package uk.ac.ucl.eidp.auth.jaspic;

import uk.ac.ucl.eidp.auth.jwt.CallerClaims;
import uk.ac.ucl.eidp.auth.jwt.Jwt;

import java.io.IOException;
import java.nio.charset.Charset;
import java.security.Key; 
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public class HeaderBasicAuthModule implements ServerAuthModule {

  private MessagePolicy requestPolicy;
  private CallbackHandler handler;
  private Map<String, String> options;
  
  private static final Class<?>[] MESSAGE_TYPES = new Class<?>[]{
    HttpServletRequest.class, HttpServletResponse.class
  };

  // Not secure connections must be allowed explicitly
  private boolean allowNotSecure = false;
  // Initially set to UTF-8
  private String credentialsEncoding = "UTF-8";
  private String apiKeySecret;
  private String jwtIssuer;
  private long validFor = 0L;
  private Key jwtKey;
  private static final String BASIC = "Basic";
  private static final String BEARER = "Bearer";
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String ALLOW_NOTSECURE_NAME = "allow.notsecure";
  private static final String CREDENTIALS_ENCODING_NAME = "credentials.encoding";
  private static final String APIKEY_SECRET_NAME = "jwt.secret";
  private static final String JWT_ISSUER_NAME = "jwt.issuer";
  private static final String JWT_VALID_FOR = "jwt.validfor";
  private static final String DEF_ENC = "UTF-8";
  private static final String DEF_NOT_SEC = "false";
  private static final String DEF_ISS = "eidp";
  
  // private static final String MESSAGES = "META-INF/Messages";
  
  // private static final ResourceBundle resourceBundle = ResourceBundle.getBundle(MESSAGES);
  
  private PasswordValidationCallback passwordValidationCallback;
  
  @Override
  public void initialize(
          MessagePolicy requestPolicy,
          MessagePolicy responsePolicy,
          CallbackHandler handler,
          Map options
  ) throws AuthException {
    this.requestPolicy = requestPolicy;
    this.handler = handler;
    if (null != options) {
      this.options = options;
      if (null != this.options) {
        allowNotSecure = Boolean.parseBoolean(
                (String) options.getOrDefault(ALLOW_NOTSECURE_NAME, DEF_NOT_SEC)
        );
        credentialsEncoding = (String) options.getOrDefault(CREDENTIALS_ENCODING_NAME, DEF_ENC);
        apiKeySecret = (String) options.get(APIKEY_SECRET_NAME);
        jwtIssuer = (String) options.getOrDefault(JWT_ISSUER_NAME, DEF_ISS);
        validFor = Long.parseLong((String) options.getOrDefault(JWT_VALID_FOR, "0"));
      }
      if (null == jwtKey) {
        jwtKey = initJwtKey();
      }
      
    }
  }

  @Override
  public Class[] getSupportedMessageTypes() {
    return Arrays.copyOf(MESSAGE_TYPES, MESSAGE_TYPES.length);
  }

  @Override
  public AuthStatus validateRequest(
          MessageInfo messageInfo,
          Subject clientSubject,
          Subject serviceSubject
  ) throws AuthException {
    final HttpServletRequest req = (HttpServletRequest) messageInfo.getRequestMessage();
    
    // Evaluate whether not secure connections are allowed against policy
    if (!req.isSecure() && !allowNotSecure) {
      return AuthStatus.SEND_FAILURE;
    }
    
    // Token authorisation mandatory for all resources except for authentication
    if (!requestPolicy.isMandatory()) {

      final String userName = readAuthenticationHeader(messageInfo, clientSubject);

      if (null == userName) {
        return AuthStatus.FAILURE;
      }

      return AuthStatus.SUCCESS;
      
      // Otherwise process JWT
    } else {
      final boolean validJwt = readBearerHeader(messageInfo, clientSubject);
      if (validJwt) {
        return AuthStatus.SUCCESS;
      } else {
        return AuthStatus.FAILURE;
      }
    }

  }

  @Override
  public AuthStatus secureResponse(MessageInfo messageInfo, Subject subject) throws AuthException {
    return AuthStatus.SEND_SUCCESS;
  }

  @Override
  public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
    if (null != subject) {
      subject.getPrincipals().clear();
    }
  }
  
  protected void setPasswordValidationCallback(PasswordValidationCallback pwdValidationCallback) {
    this.passwordValidationCallback = pwdValidationCallback;
  }
  
  private String readAuthenticationHeader(MessageInfo msgInfo, Subject subj) throws AuthException {

    String header = getAuthorisationHeader(msgInfo);

    if (!isEmpty(header) && header.startsWith(BASIC + " ")) {

      header = header.substring(6).trim();

      // Decode and parse the authorization header
      byte[] headerBytes = header.getBytes(Charset.forName(credentialsEncoding));
      byte[] decBytes = Base64.getDecoder().decode(headerBytes);
      String decoded = new String(decBytes, Charset.forName(credentialsEncoding));

      int colon = decoded.indexOf(':');
      if (colon <= 0 || colon == decoded.length() - 1) {
        return (null);
      }

      String username = decoded.substring(0, colon);
      
      // use the callback to ask the container to
      // validate the password      
      if (null == passwordValidationCallback) {
        passwordValidationCallback = new PasswordValidationCallback(
              subj, username, decoded.substring(colon + 1).toCharArray());
      }
      try {
        handler.handle(new Callback[]{passwordValidationCallback});
        passwordValidationCallback.clearPassword();
      } catch (IOException | UnsupportedCallbackException ex) {
        AuthException ae = new AuthException();
        ae.initCause(ex);
        throw ae;
      }

      if (passwordValidationCallback.getResult()) {
        return username;
      }
    }
    return null;
  }
  
  private boolean readBearerHeader(MessageInfo msgInfo, Subject subj) throws AuthException {
    String header = getAuthorisationHeader(msgInfo);
    
    if (isEmpty(header) || !header.startsWith(BEARER + " ")) {
      throw new AuthException("JWT: Header missing or not valid");
    }

    header = header.substring(7).trim();
    CallerClaims callerClaims = Jwt.toCallerClaims(header, jwtKey);
    if (null == callerClaims) {
      throw new AuthException("JWT: Signature not valid");
    }

    if (validFor > 0) {
      if (null == callerClaims.getExp()) {
        throw new AuthException("JWT: Claim missing");
      }
      Date date = new Date(System.currentTimeMillis());
      if (callerClaims.getExp().before(date)) {
        return false;
      }
    }

    if (!jwtIssuer.equalsIgnoreCase(callerClaims.getIss())) {
      return false;
    }

    Callback callerCallback = new CallerPrincipalCallback(subj, callerClaims.getSub());
    Callback[] callbacks = new Callback[]{callerCallback};

    try {
      handler.handle(callbacks);
    } catch (IOException | UnsupportedCallbackException ex) {
      AuthException ae = new AuthException();
      ae.initCause(ex);
      throw ae;
    }

    return true;

  }
  
  private boolean isEmpty(String text) {
    return text == null || text.isEmpty();
  }
  
  private String getAuthorisationHeader(MessageInfo msgInfo) {
    HttpServletRequest request = (HttpServletRequest) msgInfo.getRequestMessage();
    return request.getHeader(AUTHORIZATION_HEADER);
  }

  private Key initJwtKey() throws AuthException {
    if (null == apiKeySecret) {
      throw new AuthException("Authorisation parameter missing");
    }
    return new SecretKeySpec(apiKeySecret.getBytes(Charset.forName(credentialsEncoding)),"AES");
  }

}