package uk.ac.ucl.eidp.auth;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.auth.jwt.Jwt;
import uk.ac.ucl.eidp.auth.model.RoleE;
import uk.ac.ucl.eidp.auth.model.UserE;

import java.util.HashSet;
import java.util.Set;
import javax.security.auth.login.LoginContext;



/**
 * Unit tests for AuthController.
 * @author rebmdgu
 */
public class AuthControllerNgTest {
  
  private AuthController instance;
  private final Set<RoleE> roleSet = new HashSet<>();

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  /**
   * Mocks external dependencies.
   */
  @BeforeMethod
  public void setUpMethod() {
    this.instance = new AuthController();
    this.instance.userController = mock(UserController.class);
    this.instance.jwt = mock(Jwt.class);
    this.instance.loginContext = mock(LoginContext.class);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
    roleSet.clear();
  }

  /**
   * Test of isAuthorised method, of class AuthController.
   */
  @Test
  public void testIsAuthorised() {
    System.out.println("isAuthorised");
    RoleE role1 = new RoleE();
    role1.setId(Long.valueOf(1));
    role1.setRoleName("public");
    roleSet.add(role1);
    UserE userMock = mock(UserE.class);
    userMock.setRoles(roleSet);
    String authId = "authId";
    String authToken = "token";   
    when(this.instance.userController.findByUsernameAndAuthToken(authId, authToken))
            .thenReturn(userMock);
    Set<String> rolesAllowed = new HashSet<>();
    rolesAllowed.add("public");
    boolean result = this.instance.isAuthorised(authId, authToken, rolesAllowed);
    boolean expResult = true;
    assertEquals(result, expResult);
  }
  
}
