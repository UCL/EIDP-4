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
package uk.ac.ucl.eidp.service;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import uk.ac.ucl.eidp.auth.AuthAccess;
import uk.ac.ucl.eidp.auth.AuthLogin;
import uk.ac.ucl.eidp.auth.AuthServiceLocal;

/**
 *
 * @author david
 */
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {
    
    @EJB
    AuthServiceLocal authService;
    
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
