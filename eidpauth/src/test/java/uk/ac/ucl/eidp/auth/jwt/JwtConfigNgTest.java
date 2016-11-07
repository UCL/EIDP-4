package uk.ac.ucl.eidp.auth.jwt;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.util.Base64;
import javax.crypto.KeyGenerator;


/**
 * Unit test for JwtConfig.
 * @author David Guzman
 */
public class JwtConfigNgTest {
  
  private final static KeystoreProperties keystoreProperties = new KeystoreProperties();
  private static String apiKey;
  
  public JwtConfigNgTest() {
  }

  /**
   * Creates a KeyStore programmatically.
   * @throws Exception If fails to create KeyStore.
   */
  @BeforeClass
  public static void setUpClass() throws Exception {
    KeyStore ks = KeyStore.getInstance("JCEKS");
    ks.load(null, null);
    KeyGenerator keyGen = KeyGenerator.getInstance("AES");
    keyGen.init(256);
    Key key = keyGen.generateKey();
    apiKey = Base64.getEncoder().encodeToString(key.getEncoded());
    char[] password = keystoreProperties.getPasswordKs().toCharArray();
    ks.setKeyEntry(keystoreProperties.getSecretKeyAlias(), key, password, null);
    ks.store(new FileOutputStream(keystoreProperties.getKeystorePath()), password);
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    File file = new File(keystoreProperties.getKeystorePath());
    file.delete();
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  /**
   * Test of initialise method, of class JwtConfig.
   */
  @Test
  public void testInitialise() {
    System.out.println("initialise");
    JwtConfig instance = new JwtConfig();
    instance.initialise();
    // TODO review the generated test code and remove the default call to fail.
    //fail("The test case is a prototype.");
  }

  /**
   * Test of getApiKey method, of class JwtConfig.
   */
  @Test
  public void testGetApiKey() {
    System.out.println("getApiKey should be " + apiKey);
    JwtConfig instance = new JwtConfig();
    instance.initialise();
    Key key = instance.getApiKey();
    String result = Base64.getEncoder().encodeToString(key.getEncoded());
    assertEquals(result, apiKey);
  }
  
}
