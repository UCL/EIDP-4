package uk.ac.ucl.eidp.auth.jaas;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for LoginPrincipal.
 * @author rebmdgu
 */
public class LoginPrincipalNgTest {
  
  public LoginPrincipalNgTest() {
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
   * Test of getName method, of class LoginPrincipal.
   */
  @Test
  public void testGetName() {
    System.out.println("getName");
    LoginPrincipal instance = new LoginPrincipal("name");
    String expResult = "name";
    String result = instance.getName();
    assertEquals(result, expResult);
  }
  
}
