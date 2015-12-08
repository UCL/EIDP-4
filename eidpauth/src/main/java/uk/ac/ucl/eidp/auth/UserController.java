/*
 * Copyright 2015 David Guzman <d.guzman at ucl.ac.uk>.
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

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import uk.ac.ucl.eidp.auth.model.UserE;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Stateless
@LocalBean
public class UserController {
    
    @PersistenceContext(unitName = "eidpauthPU")
    private EntityManager em;

    public UserE findUser(final String login, final String password) {
        TypedQuery<UserE> typedQuery = em.createNamedQuery(UserE.FIND_BY_LOGIN_PASSWORD, UserE.class);
        typedQuery.setParameter("login", login);
        typedQuery.setParameter("password", password);
        return typedQuery.getSingleResult();
    }
    
}
