package uk.ac.ucl.eidp.auth.jaas;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;




/**
 * Unit tests for SingleFactorLoginModule.
 * @author rebmdgu
 */
public class SingleFactorLoginModuleNgTest {
  
  public SingleFactorLoginModuleNgTest() {
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  /**
   * Test of initialize method, of class SingleFactorLoginModule.
   */
  @Test
  public void testInitialize() throws Exception {
    System.out.println("initialize");
    Subject subject = null;
    CallbackHandler callbackHandler = new DummyCallbackHandler();
    CallbackHandler callbackHandlerSpy = spy(callbackHandler);
    doNothing().when(callbackHandlerSpy).handle(any());
    Map<String, ?> sharedState = null;
    Map<String, ?> options = null;
    SingleFactorLoginModule instance = new SingleFactorLoginModule();
    instance.initialize(subject, callbackHandler, sharedState, options);
  }

  /**
   * Test of commit method, of class SingleFactorLoginModule.
   */
  @Test
  public void testCommit() throws Exception {
    System.out.println("commit");
    SingleFactorLoginModule instance = new SingleFactorLoginModule();
    boolean expResult = true;
    boolean result = instance.commit();
    assertEquals(result, expResult);
  }

  /**
   * Test of abort method, of class SingleFactorLoginModule.
   */
  @Test
  public void testAbort() throws Exception {
    System.out.println("abort");
    SingleFactorLoginModule instance = new SingleFactorLoginModule();
    boolean expResult = true;
    boolean result = instance.abort();
    assertEquals(result, expResult);
  }

  /**
   * Test of logout method, of class SingleFactorLoginModule.
   */
  @Test
  public void testLogout() throws Exception {
    System.out.println("logout");
    SingleFactorLoginModule instance = new SingleFactorLoginModule();
    boolean expResult = true;
    boolean result = instance.logout();
    assertEquals(result, expResult);
  }
 
  class DummyCallbackHandler implements CallbackHandler {

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
      
    }
    
  }
}
