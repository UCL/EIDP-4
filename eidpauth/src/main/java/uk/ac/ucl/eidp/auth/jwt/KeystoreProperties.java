package uk.ac.ucl.eidp.auth.jwt;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Contains properties required to access the key store.
 * @author rebmdgu
 */
public class KeystoreProperties {
  
  private final Properties properties = new Properties();
  private final String propertiesPath = "META-INF/eidpauth.properties";
  private final String keystoreProp = "javax.net.ssl.keyStore";
  private final String keystoreDef = "${com.sun.aas.instanceRoot}/config/keystore.jks";
  private final String passwordProp = "javax.net.ssl.keyStorePassword";
  private final String passwordDef = "changeme";

  /**
   * Constructor.
   */
  public KeystoreProperties() {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesPath)) {
      properties.load(is);
    } catch (IOException ex) {
      throw new UnsupportedOperationException("Cannot load eidpauth.properties " , ex);
    }
  }
  
  /**
   * Path to the Java key store.
   * @return the keystorePath
   */
  public String getKeystorePath() {
    return properties.getProperty(keystoreProp, keystoreDef);
  }

  /**
   * Password of the key store. Must be encrypted.
   * @return the passwordKs
   */
  public String getPasswordKs() {
    return properties.getProperty(passwordProp, passwordDef);
  }
  
}
