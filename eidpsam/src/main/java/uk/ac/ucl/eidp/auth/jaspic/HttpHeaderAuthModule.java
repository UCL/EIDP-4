package uk.ac.ucl.eidp.auth.jaspic;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HttpHeaderAuthModule implements ServerAuthModule {

  private static final Class<?>[] MESSAGE_TYPES = new Class<?>[]{
    HttpServletRequest.class, HttpServletResponse.class
  };

//  public static final String USERNAME_HEADER_KEY = "username_header";
  private final String requestHeader = "X-Forwarded-User";
  
  private CallbackHandler handler;

  @Override
  public void initialize(
          MessagePolicy requestPolicy,
          MessagePolicy responsePolicy,
          CallbackHandler handler,
          Map options
  ) throws AuthException {
    this.handler = handler;
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
    final String userName = req.getHeader(requestHeader);
    if (null == userName) {
      return AuthStatus.FAILURE;
    }

    try {
      // Store the user name that was in the header and also set a group.
      handler.handle(new Callback[]{
          new CallerPrincipalCallback(clientSubject, userName),
          new GroupPrincipalCallback(clientSubject, new String[]{"users"})}
      );
      return AuthStatus.SUCCESS;
    } catch (IOException | UnsupportedCallbackException ex) {
      throw new AuthException(ex.getMessage());
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
    
}