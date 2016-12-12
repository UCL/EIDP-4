package uk.ac.ucl.eidp.auth;

import java.util.Set;
import javax.ejb.Local;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Local
public interface AuthControllerLocal {

  AuthAccess login(AuthLogin credentials);

  boolean isAuthorised(String authId, String authToken, Set<String> rolesAllowed);

}
