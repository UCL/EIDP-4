package uk.ac.ucl.eidp.web;

import uk.ac.ucl.eidp.auth.AuthAccess;
import uk.ac.ucl.eidp.auth.AuthService;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

/**
 * An interceptor to carry out the authorisation filter.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Provider
public class AuthSecurityInterceptor implements ContainerRequestFilter {

  // 401 - Access denied
  private static final Response accessUnauthorised = 
          Response.status(Response.Status.UNAUTHORIZED).entity("Not authorised.").build();

  @EJB
  private AuthService authService;

  @Context
  private HttpServletRequest request;

  @Context
  private ResourceInfo resourceInfo;

  @Override
  public void filter(ContainerRequestContext crc) throws IOException {
    // Get AuthId and AuthToken from HTTP-Header.
    String authId = crc.getHeaderString(AuthAccess.PARAM_AUTH_ID);
    String authToken = crc.getHeaderString(AuthAccess.PARAM_AUTH_TOKEN);

    // Get method invoked.
    Method resourceMethod = resourceInfo.getResourceMethod();

    if (resourceMethod.isAnnotationPresent(RolesAllowed.class)) {
      RolesAllowed rolesAllowedAnnotation = resourceMethod.getAnnotation(RolesAllowed.class);
      Set<String> rolesAllowed = new HashSet<>(Arrays.asList(rolesAllowedAnnotation.value()));

      if (!authService.isAuthorised(authId, authToken, rolesAllowed)) {
        crc.abortWith(accessUnauthorised);
      }
    }
  }

}
