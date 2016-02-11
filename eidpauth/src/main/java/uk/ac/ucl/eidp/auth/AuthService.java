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
package uk.ac.ucl.eidp.auth;

import java.util.Set;
import java.util.UUID;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import uk.ac.ucl.eidp.auth.model.RoleE;
import uk.ac.ucl.eidp.auth.model.UserE;

/**
 *
 * @author david
 */
@Stateless
public class AuthService implements AuthServiceLocal {
    
    @EJB
    UserController userController;

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
            // TODO : Check it might not work
            return rolesAllowed.containsAll(user.getRoles());
        } else {
            return false;
        }
    }

    
}
