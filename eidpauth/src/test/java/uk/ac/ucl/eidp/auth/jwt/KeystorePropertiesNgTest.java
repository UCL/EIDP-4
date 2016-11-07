package uk.ac.ucl.eidp.auth.jwt;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * Test for KeystoreProperties class.
 * @author rebmdgu
 */
public class KeystorePropertiesNgTest {
  
  public KeystorePropertiesNgTest() {
  }

  /**
   * Test of getKeystorePath method, of class KeystoreProperties.
   */
  @Test
  public void testGetKeystorePath() {
    System.out.println("getKeystorePath");
    KeystoreProperties instance = new KeystoreProperties();
    String expResult = "src/test/resources/META-INF/keystore.jks";
    String result = instance.getKeystorePath();
    assertEquals(result, expResult);
  }

  /**
   * Test of getPasswordKs method, of class KeystoreProperties.
   */
  @Test
  public void testGetPasswordKs() {
    System.out.println("getPasswordKs");
    KeystoreProperties instance = new KeystoreProperties();
    String expResult = "changeme";
    String result = instance.getPasswordKs();
    assertEquals(result, expResult);
  }
  
}
