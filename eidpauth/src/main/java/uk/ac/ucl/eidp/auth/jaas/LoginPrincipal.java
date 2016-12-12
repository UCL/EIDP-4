package uk.ac.ucl.eidp.auth.jaas;

import java.io.Serializable;
import java.security.Principal;

/**
 *
 * @author rebmdgu
 */
public class LoginPrincipal implements Principal, Serializable {

  private static final long serialVersionUID = -6537936343260900085L;
  
  private final String name;
  
  public LoginPrincipal(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }
  
}
