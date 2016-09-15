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
 * REST resource for authentication and authorisation.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

  @EJB
  private AuthServiceLocal authService;

  /**
   * Authenticates user obtaining credentials from the http request.
   * @param request {@link HttpServletRequest} call
   * @param loginElement {@link AuthLogin} object containing the credentials.
   * @return A {@link AuthAccess} containing the authenticated credentials and token.
   */
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
