package uk.ac.ucl.eidp.auth;

import uk.ac.ucl.eidp.auth.model.RoleE;
import uk.ac.ucl.eidp.auth.model.UserE;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class AuthService implements AuthServiceLocal {

  @EJB
  private UserController userController;

  @Override
  public AuthAccess login(AuthLogin authLogin) {
    UserE user = userController.findUser(authLogin.getLogin(), authLogin.getPassword());
    if (null != user) {
      user.setToken(UUID.randomUUID().toString());
      userController.save(user);
      return new AuthAccess(authLogin.getLogin(), user.getToken(), user.getRoles().toString());
    }
    return null;
  }

  @Override
  public boolean isAuthorised(String authId, String authToken, Set<String> rolesAllowed) {
    UserE user = userController.findByUsernameAndAuthToken(authId, authToken);
    if (user != null) {
      Set<String> userRoles = user.getRoles().stream().map(
          (RoleE role) -> role.getRoleName()
      ).collect(Collectors.toSet());
      return rolesAllowed.containsAll(userRoles);
    } else {
      return false;
    }
  }


}
