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

/**
 * Unit tests for AuthService.
 * @author rebmdgu
 */
public class AuthServiceNgTest {
  
  private AuthService instance;
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
    this.instance = new AuthService();
    this.instance.userController = mock(UserController.class);
    this.instance.jwt = mock(Jwt.class);
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
    roleSet.clear();
  }

  /**
   * Test of login method, of class AuthService.
   */
  @Test
  public void testLogin() {
    System.out.println("login");
    RoleE role1 = new RoleE();
    role1.setId(Long.valueOf(0));
    role1.setRoleName("admin");
    roleSet.add(role1);
    RoleE role2 = new RoleE();
    role2.setId(Long.valueOf(1));
    role2.setRoleName("public");
    roleSet.add(role2);    
    UserE userMock = mock(UserE.class);
    userMock.setRoles(roleSet);
    when(this.instance.userController.findUser("user", "password")).thenReturn(userMock);    
    AuthLogin authLogin = new AuthLogin("user","password");
    when(this.instance.jwt.createJwtToken(authLogin, 0)).thenReturn("token");
    AuthAccess expResult = new AuthAccess("user",
            "token",
            "[uk.ac.ucl.eidp.auth.model.RoleE[ id=0 ], uk.ac.ucl.eidp.auth.model.RoleE[ id=1 ]]");
    AuthAccess result = instance.login(authLogin);
    assertEquals(result.getAuthId(), expResult.getAuthId());
    //assertEquals(result.getAuthToken(), expResult.getAuthToken());
  }

  /**
   * Test of isAuthorised method, of class AuthService.
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
