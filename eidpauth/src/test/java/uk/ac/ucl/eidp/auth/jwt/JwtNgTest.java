package uk.ac.ucl.eidp.auth.jwt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.auth.AuthLogin;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * Unit tests for Jwt.
 * @author rebmdgu
 */
public class JwtNgTest {

  private Jwt instance;
  
  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
    instance = new Jwt();
    instance.jwtConfig = mock(JwtConfig.class);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  /**
   * Test of createJwtToken method, of class Jwt. It only tests creation of token, not whether it
   * is a valid one.
   * @throws Exception if AES algorithm not found.
   */
  @Test
  public void testCreateJwtToken() throws Exception {
    System.out.println("createJwtToken");
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(128);
    SecretKey secretKey = keyGen.generateKey();
    
    when(this.instance.jwtConfig.getApiKey()).thenReturn(secretKey);
    
    AuthLogin loginElement = new AuthLogin("login","password");
    long ttl = 0L;

    String expResult = "eyJhbGciOiJIUzI1NiJ9";
    String result = instance.createJwtToken(loginElement, ttl).split("\\.")[0];
    assertEquals(result, expResult);
  }
  
}
