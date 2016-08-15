package uk.ac.ucl.eidp.auth;

import uk.ac.ucl.eidp.auth.model.UserE;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class UserController {

  @PersistenceContext(unitName = "eidpauthPU")
  private EntityManager em;

  public UserE findUser(final String login, final String password) {
    TypedQuery<UserE> typedQuery = em.createNamedQuery(UserE.FIND_BY_LOGIN_PASSWORD, UserE.class);
    typedQuery.setParameter("login", login);
    typedQuery.setParameter("password", password);
    return typedQuery.getSingleResult();
  }

  public void save(UserE user) {

  }

  public UserE findByUsernameAndAuthToken(String authId, String authToken) {
    //TODO
    return null;
  }
}
