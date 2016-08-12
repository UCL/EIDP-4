package uk.ac.ucl.eidp.service;

import uk.ac.ucl.eidp.auth.AuthAccess;
import uk.ac.ucl.eidp.auth.AuthLogin;
import uk.ac.ucl.eidp.auth.AuthServiceLocal;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author David Guzman
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @EJB
  private AuthServiceLocal authService;

  @POST
  @Path("login")
  @PermitAll
  public AuthAccess login(@Context HttpServletRequest request, AuthLogin loginElement) {
    AuthAccess accessElement = authService.login(loginElement);
    if (accessElement != null) {
      request.getSession().setAttribute(AuthAccess.PARAM_AUTH_ID, accessElement.getAuthId());
      request.getSession().setAttribute(AuthAccess.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
    }
    return accessElement;
  }
}
