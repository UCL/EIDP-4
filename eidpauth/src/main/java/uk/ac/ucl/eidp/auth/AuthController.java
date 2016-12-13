package uk.ac.ucl.eidp.auth;

import uk.ac.ucl.eidp.auth.jaas.SingleFactorCallbackHandler;
import uk.ac.ucl.eidp.auth.jwt.Jwt;
import uk.ac.ucl.eidp.auth.model.RoleE;
import uk.ac.ucl.eidp.auth.model.UserE;

import java.security.Principal;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 * Controller for authentication and authorisation using JAAS.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class AuthController implements AuthControllerLocal {

  @EJB
  UserController userController;
  
  @Inject
  Jwt jwt;
  
  LoginContext loginContext;

  /**
   * Performs authentication using JAAS login method.
   * @param authLogin Containing user credentials.
   * @return {@link AuthAccess} containing the user Principals.
   */
  @Override
  public AuthAccess login(AuthLogin authLogin) {
    CallbackHandler callbackHandler = new SingleFactorCallbackHandler(authLogin);
    try {
      loginContext = new LoginContext("JaasSample", callbackHandler);
      loginContext.login();
    } catch (LoginException ex) {
      Logger.getLogger(AuthController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    Subject subject = loginContext.getSubject();
    if (null == subject) {
      return null;
    } else {
      Optional<Principal> principalOptional = subject.getPrincipals().stream().findFirst();
      if (principalOptional.isPresent()) {
        AuthAccess authAccess = new AuthAccess();
        authAccess.setAuthId(principalOptional.get().getName());
        return authAccess;
      } else {
        return null;
      }
    }
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
