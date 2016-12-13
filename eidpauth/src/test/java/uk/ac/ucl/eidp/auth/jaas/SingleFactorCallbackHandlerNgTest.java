package uk.ac.ucl.eidp.auth.jaas;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.auth.AuthLogin;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;


/**
 * Unit tests for SingleFactorCallbackHandler.
 * @author rebmdgu
 */
public class SingleFactorCallbackHandlerNgTest {
  
  public SingleFactorCallbackHandlerNgTest() {
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
   * Test of handle method, of class SingleFactorCallbackHandler.
   */
  @Test(expectedExceptions = UnsupportedCallbackException.class)
  public void testHandle() throws Exception {
    System.out.println("handle");
    Callback dummyCallback = new DummyCallback();
    Callback[] callbacks = {dummyCallback};
    AuthLogin authLogin = new AuthLogin("user","password");
    CallbackHandler instance = new SingleFactorCallbackHandler(authLogin);
    instance.handle(callbacks);
  }
  
  class DummyCallback implements Callback {
  }
  
}
