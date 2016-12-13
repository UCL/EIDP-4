package uk.ac.ucl.eidp.auth.jaas;

import java.io.Serializable;
import java.security.Principal;

/**
 * Implementation of {@link Principal} that stores the user's login name.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
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
