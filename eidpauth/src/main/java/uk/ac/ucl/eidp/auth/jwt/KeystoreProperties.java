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
  private static final String propertiesPath = "META-INF/eidpauth.properties";
  private static final String keystoreProp = "javax.net.ssl.keyStore";
  private static final String keystoreDef = "${com.sun.aas.instanceRoot}/config/keystore.jks";
  private static final String passwordProp = "javax.net.ssl.keyStorePassword";
  private static final String passwordDef = "changeme";
  private static final String secretKeyAliasProp = "eidp.auth.secretKeyAlias";
  private static final String secretKeyAliasDef = "secret";

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
  
  /**
   * Alias of the secret key.
   * @return The alias
   */
  public String getSecretKeyAlias() {
    return properties.getProperty(secretKeyAliasProp, secretKeyAliasDef);
  }
}
