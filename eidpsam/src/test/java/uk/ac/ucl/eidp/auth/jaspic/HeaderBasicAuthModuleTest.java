package uk.ac.ucl.eidp.auth.jaspic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.PasswordValidationCallback;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * Unit tests for HeaderBasicAuthModule.
 * @author rebmdgu
 */
public class HeaderBasicAuthModuleTest {
  
  private final Map<String, String> options;
  private final HeaderBasicAuthModule module = new HeaderBasicAuthModule();
  private final CallbackHandler handler = mock(CallbackHandler.class);
  private final MessagePolicy mockRequestPolicy = mock(MessagePolicy.class);
  
  /**
   * Initialisation of variables in constructor.
   */
  public HeaderBasicAuthModuleTest() {
    Map<String, String> map = new HashMap<>();
    map.put("username_header", "X-Forwarded-User");
    options = Collections.unmodifiableMap(map);
    
    when(mockRequestPolicy.isMandatory()).thenReturn(false);
  }
  
  @Test
  public void testSecureResponse() throws AuthException {
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final Subject subject = new Subject();
    assertEquals(module.secureResponse(messageInfo, subject), AuthStatus.SEND_SUCCESS);
  }
  
  @Test
  public void testValidateRequest() throws AuthException {
    final MessageInfo messageInfo = mock(MessageInfo.class);
    final HttpServletRequest servletRequest = mock(HttpServletRequest.class);
    when(messageInfo.getRequestMessage()).thenReturn(servletRequest);
    final Subject client = new Subject();
    
    // 1a. Not secure connections must return SEND_FAILURE by default
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.SEND_FAILURE);
    
    // 1b. Return TRUE for isSecure()
    when(servletRequest.isSecure()).thenReturn(Boolean.TRUE);
    
    // 2a. HttpServletRequest header returns null;
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.FAILURE);
    
    // 2b. Set value of Authorization header to a valid string.
    when(servletRequest.getHeader("Authorization")).thenReturn("Basic dXNlcjpwYXNzd29yZA==");
    PasswordValidationCallback pwc = mock(PasswordValidationCallback.class);
    when(pwc.getResult()).thenReturn(Boolean.TRUE);
    module.setPasswordValidationCallback(pwc);
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.SUCCESS);
  }
  
  @Test
  public void testSupportedMessageTypes() {
    Class<?>[] messageTypes = new Class<?>[]{HttpServletRequest.class, HttpServletResponse.class};
    assertEquals(module.getSupportedMessageTypes(), messageTypes);
  }
  
  @Test
  public void testCleanSubject() throws AuthException {
    Subject subject = new Subject();
    Principal principal = () -> "testPrincipal";
    subject.getPrincipals().add(principal);
    module.cleanSubject(null, subject);
    assertEquals(subject.getPrincipals().size(), 0);
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
    module.initialize(mockRequestPolicy, null, handler, options);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }
  
}
