package uk.ac.ucl.eidp.auth.jaspic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.servlet.http.HttpServletRequest;

/**
 * Unit tests for HttpHeaderAuthModule.
 * @author rebmdgu
 */
public class HttpHeaderAuthModuleTest {
  
  private final Map<String, String> options;
  private final HttpHeaderAuthModule module = new HttpHeaderAuthModule();
  private final CallbackHandler handler = mock(CallbackHandler.class);
  private final MessagePolicy mockRequestPolicy = mock(MessagePolicy.class);
  
  /**
   * Initialisation of variables in constructor.
   */
  public HttpHeaderAuthModuleTest() {
    Map<String, String> map = new HashMap<>();
    map.put(HttpHeaderAuthModule.USERNAME_HEADER_KEY, "X-Forwarded-User");
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
    // when HttpServletRequest header "X-Forwarded-User" returns null;
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.FAILURE);
    when(servletRequest.getHeader("X-Forwarded-User")).thenReturn("username");
    // when HttpServletRequest header "X-Forwarded-User" returns a String;
    assertEquals(module.validateRequest(messageInfo, client, null), AuthStatus.SUCCESS);
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
    module.initialize(mockRequestPolicy, null, handler, options);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }
}
