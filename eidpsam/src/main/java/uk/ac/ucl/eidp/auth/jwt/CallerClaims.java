package uk.ac.ucl.eidp.auth.jwt;

import java.util.Date;
import java.util.UUID;

/**
 * JWT Claims for processing in JASPIC modules.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class CallerClaims {
  
  private final UUID uid = UUID.randomUUID();
  private final String sub;
  private final String iss;
  private Date exp;
  
  public CallerClaims(String sub, String iss) {
    this.sub = sub;
    this.iss = iss;
  }
  
  public String getSub() {
    return sub;
  }
  
  public String getIss() {
    return iss;
  }
  
  public String getJti() {
    return uid.toString();
  }
  
  /**
   * Returns the expiration date.
   * @return returns a clone copy of the data of expiration.
   */
  public Date getExp() {
    if (null != exp) {
      return (Date) exp.clone();
    }
    return exp;
  }
  
  public void setExp(Date exp) {
    this.exp = new Date(exp.getTime());
  }
}
