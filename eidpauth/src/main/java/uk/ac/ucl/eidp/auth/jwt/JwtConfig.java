package uk.ac.ucl.eidp.auth.jwt;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;

/**
 * Configuration object for JWT.
 * @author rebmdgu
 */
@ApplicationScoped
public class JwtConfig {
  
  @Resource
  private String secretKeyAlias;
    
  private final KeystoreProperties keystoreProperties = new KeystoreProperties();
  private Key apiKey;
  
  /**
   * Initialise variables to read a key store.
   */
  @PostConstruct
  public void initialise() {
    try {
      KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
      try (InputStream is = new FileInputStream(keystoreProperties.getKeystorePath())) {
        keyStore.load(is, keystoreProperties.getPasswordKs().toCharArray());
        KeyStore.ProtectionParameter protParam = new KeyStore.PasswordProtection(
                keystoreProperties.getPasswordKs().toCharArray()
        );
        apiKey = keyStore.getKey(secretKeyAlias, keystoreProperties.getPasswordKs().toCharArray());
      } catch (IOException 
              | NoSuchAlgorithmException 
              | CertificateException 
              | UnrecoverableEntryException ex) {
        Logger.getLogger(JwtConfig.class.getName()).log(Level.SEVERE, null, ex);
      }
    } catch (KeyStoreException ex) {
      Logger.getLogger(JwtConfig.class.getName()).log(Level.SEVERE, null, ex);
    }

  }
  
  public Key getApiKey() {
    return apiKey;
  }
  
  
}
