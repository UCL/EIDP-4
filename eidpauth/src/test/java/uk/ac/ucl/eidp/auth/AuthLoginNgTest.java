package uk.ac.ucl.eidp.auth;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit test for AuthLogin.
 * @author rebmdgu
 */
public class AuthLoginNgTest {
  
  private AuthLogin instance = null;
  
  public AuthLoginNgTest() {
    instance = new AuthLogin("login", "password");
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
   * Test of getLogin method, of class AuthLogin.
   */
  @Test
  public void testGetLogin() {
    System.out.println("getLogin");
    String expResult = "login";
    String result = instance.getLogin();
    assertEquals(result, expResult);
  }

  /**
   * Test of setLogin method, of class AuthLogin.
   */
  @Test
  public void testSetLogin() {
    System.out.println("setLogin");
    String origLogin = instance.getLogin();
    String login = "newlogin";
    instance.setLogin(login);
    assertEquals(instance.getLogin(), login);
    instance.setLogin(origLogin);
  }

  /**
   * Test of getPassword method, of class AuthLogin.
   */
  @Test
  public void testGetPassword() {
    System.out.println("getPassword");
    String expResult = "password";
    String result = instance.getPassword();
    assertEquals(result, expResult);
  }

  /**
   * Test of setPassword method, of class AuthLogin.
   */
  @Test
  public void testSetPassword() {
    System.out.println("setPassword");
    String origPassword = instance.getPassword();
    String password = "newpassword";
    instance.setPassword(password);
    assertEquals(instance.getPassword(), password);
    instance.setPassword(origPassword);
  }
  
}
