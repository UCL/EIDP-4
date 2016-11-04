package uk.ac.ucl.eidp.auth.jwt;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.Key;
import java.security.KeyStoreException;

/**
 * Unit test for JwtConfig.
 * @author David Guzman
 */
public class JwtConfigNgTest {
  
  public JwtConfigNgTest() {
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
   * Test of initialise method, of class JwtConfig.
   * @throws java.security.KeyStoreException
   */
  @Test
  public void testInitialise() throws KeyStoreException {
    System.out.println("initialise");
    JwtConfig instance = new JwtConfig();
    instance.initialise();
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
  }

  /**
   * Test of getApiKey method, of class JwtConfig.
   * @throws java.security.KeyStoreException
   */
  @Test
  public void testGetApiKey() throws KeyStoreException {
    System.out.println("getApiKey");
    JwtConfig instance = new JwtConfig();
    Key result = instance.getApiKey();
    assertNull(result);
  }
  
}
