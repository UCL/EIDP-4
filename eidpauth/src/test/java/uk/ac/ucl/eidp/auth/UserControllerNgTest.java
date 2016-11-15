package uk.ac.ucl.eidp.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.mockito.Matchers;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.auth.model.UserE;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


/**
 * Unit tests for UserController.
 * @author rebmdgu
 */
public class UserControllerNgTest {
  
  private UserController instance;

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
    this.instance = new UserController();
    this.instance.em = mock(EntityManager.class);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
  }

  private void mockTypedQuery(String name, UserE user) {
    TypedQuery<UserE> typedQuery = mock(TypedQuery.class);
    when(typedQuery.getSingleResult()).thenReturn(user);
    when(typedQuery.setParameter(Matchers.anyString(), 
            Matchers.anyObject())).thenReturn(typedQuery);
    when(this.instance.em.createNamedQuery(name, UserE.class)).thenReturn(typedQuery);
  }

  /**
   * Test of findUser method, of class UserController.
   */
  @Test
  public void testFindUser() {
    System.out.println("findUser");
    UserE expResult = new UserE();
    expResult.setId(Long.MIN_VALUE);
    expResult.setToken("token");
    mockTypedQuery(UserE.FIND_BY_LOGIN_PASSWORD, expResult);
    String login = "login";
    String password = "password";  
    UserE result = instance.findUser(login, password);
    assertEquals(result, expResult);
  }

}
