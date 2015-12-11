/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ucl.eidp.auth;

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
public class UserController implements UserControllerLocal {

    @PersistenceContext(unitName = "eidpauthPU")
    private EntityManager em;
    
    @Override
    public UserE findUser(final String login, final String password) {
        TypedQuery<UserE> typedQuery = em.createNamedQuery(UserE.FIND_BY_LOGIN_PASSWORD, UserE.class);
        typedQuery.setParameter("login", login);
        typedQuery.setParameter("password", password);
        return typedQuery.getSingleResult();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
