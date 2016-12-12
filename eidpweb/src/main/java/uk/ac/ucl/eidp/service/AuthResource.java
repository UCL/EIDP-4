package uk.ac.ucl.eidp.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import uk.ac.ucl.eidp.auth.AuthAccess;
import uk.ac.ucl.eidp.auth.AuthControllerLocal;
import uk.ac.ucl.eidp.auth.AuthLogin;
import uk.ac.ucl.eidp.service.model.AuthToken;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST resource for authentication and authorisation.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Api(protocols = "https")
public class AuthResource {

  @EJB
  private AuthControllerLocal authController;

  /**
   * Authenticates user obtaining credentials from the http request.
   * @param request {@link HttpServletRequest} call
   * @param loginElement {@link AuthLogin} object containing the credentials.
   * @return A {@link AuthAccess} containing the authenticated credentials and token.
   */
  @POST
  @Path("login")
  @PermitAll
  @ApiOperation(
          value = "Authenticates client on an EIDP instance.", 
          notes = "Authentication based on userid and password. It returns a JWT token.", 
          response = AuthToken.class, 
          tags = {  }
      )
  @ApiResponses(value = { 
        @ApiResponse(
                code = 201, 
                message = "Authentication successful.", 
                response = AuthToken.class),
        @ApiResponse(
                code = 401, 
                message = "Unauthorised or authentication failed.", 
                response = AuthToken.class) 
      }
  )
  public Response login(@Context HttpServletRequest request, AuthLogin loginElement) {
    AuthAccess accessElement = authController.login(loginElement);
    if (accessElement != null) {
      request.getSession().setAttribute(AuthAccess.PARAM_AUTH_ID, accessElement.getAuthId());
      request.getSession().setAttribute(AuthAccess.PARAM_AUTH_TOKEN, accessElement.getAuthToken());
    }
    return Response.ok().entity("magic!").build();
  }
}
