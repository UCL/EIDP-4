package uk.ac.ucl.eidp.auth;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for AuthAccess.
 * @author rebmdgu
 */
public class AuthAccessNgTest {
  
  private AuthAccess instance = null;
  
  public AuthAccessNgTest() {
    instance = new AuthAccess("authId","authToken","authPermission");
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
   * Test of getAuthId method, of class AuthAccess.
   */
  @Test
  public void testGetAuthId() {
    System.out.println("getAuthId");
    String expResult = "authId";
    String result = instance.getAuthId();
    assertEquals(result, expResult);
  }

  /**
   * Test of setAuthId method, of class AuthAccess.
   */
  @Test
  public void testSetAuthId() {
    System.out.println("setAuthId");
    String origAuthId = instance.getAuthId();
    String authId = "newAuthId";
    instance.setAuthId(authId);
    assertEquals(instance.getAuthId(), authId);
    instance.setAuthId(origAuthId);
  }

  /**
   * Test of getAuthToken method, of class AuthAccess.
   */
  @Test
  public void testGetAuthToken() {
    System.out.println("getAuthToken");
    String expResult = "authToken";
    String result = instance.getAuthToken();
    assertEquals(result, expResult);
  }

  /**
   * Test of setAuthToken method, of class AuthAccess.
   */
  @Test
  public void testSetAuthToken() {
    System.out.println("setAuthToken");
    String origAuthToken = instance.getAuthToken();
    String authToken = "newAuthToken";
    instance.setAuthToken(authToken);
    assertEquals(instance.getAuthToken(), authToken);
    instance.setAuthToken(origAuthToken);
  }

  /**
   * Test of getAuthPermission method, of class AuthAccess.
   */
  @Test
  public void testGetAuthPermission() {
    System.out.println("getAuthPermission");
    String expResult = "authPermission";
    String result = instance.getAuthPermission();
    assertEquals(result, expResult);
  }

  /**
   * Test of setAuthPermission method, of class AuthAccess.
   */
  @Test
  public void testSetAuthPermission() {
    System.out.println("setAuthPermission");
    String origAuthPermission = instance.getAuthPermission();
    String authPermission = "newAuthPermission";
    instance.setAuthPermission(authPermission);
    assertEquals(instance.getAuthPermission(), authPermission);
    instance.setAuthPermission(origAuthPermission);
  }
  
}
