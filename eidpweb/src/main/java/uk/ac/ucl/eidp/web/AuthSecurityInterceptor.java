/*
 * Copyright 2016 david.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ucl.eidp.web;

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
import uk.ac.ucl.eidp.auth.AuthAccess;
import uk.ac.ucl.eidp.auth.AuthService;

/**
 *
 * @author david
 */
@Provider
public class AuthSecurityInterceptor implements ContainerRequestFilter {
    
    // 401 - Access denied
    private static final Response ACCESS_UNAUTHORIZED = Response.status(Response.Status.UNAUTHORIZED).entity("Not authorized.").build();

    @EJB
    AuthService authService;

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
                crc.abortWith(ACCESS_UNAUTHORIZED);
            }
        }
    }
    
}
