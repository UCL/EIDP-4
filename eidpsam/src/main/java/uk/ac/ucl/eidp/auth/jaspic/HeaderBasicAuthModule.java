package uk.ac.ucl.eidp.auth.jaspic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HeaderBasicAuthModule implements ServerAuthModule {

  private CallbackHandler handler;
  private Map<String, String> options;
  
  private static final Class<?>[] MESSAGE_TYPES = new Class<?>[]{
    HttpServletRequest.class, HttpServletResponse.class
  };

  // Not secure connections must be allowed explicitly
  private boolean allowNotSecure = false;
  private static final String BASIC = "Basic";
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String ALLOW_NOTSECURE_NAME = "allow.notsecure";
  
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
    this.handler = handler;
    if (null != options) {
      this.options = options;
      if (null != this.options) {
        allowNotSecure = Boolean.parseBoolean((String) options.get(ALLOW_NOTSECURE_NAME));
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
    
    // Evaluate whether not secure connections are allowed
    if (!req.isSecure() && !allowNotSecure) {
      return AuthStatus.SEND_FAILURE;
    }
    
    final String userName = readAuthenticationHeader(messageInfo, clientSubject);

    if (null == userName) {
      return AuthStatus.FAILURE;
    }

    return AuthStatus.SUCCESS;

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

    HttpServletRequest request = (HttpServletRequest) msgInfo.getRequestMessage();

    String header = request.getHeader(AUTHORIZATION_HEADER);

    if (!isEmpty(header) && header.startsWith(BASIC + " ")) {

      header = header.substring(6).trim();

      // Decode and parse the authorization header
      String decoded = new String(Base64.getDecoder().decode(header.getBytes()));

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
  
  private boolean isEmpty(String text) {
    return text == null || text.isEmpty();
  }
}