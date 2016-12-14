package uk.ac.ucl.eidp.auth.jaas;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for SingleFactorLoginModule.
 * @author rebmdgu
 */
public class SingleFactorLoginModuleNgTest {

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
 
}
