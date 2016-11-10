package uk.ac.ucl.eidp.auth.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for UserE.
 * @author rebmdgu
 */
public class UsereNgTest {
  
  private UserE instance = new UserE();
  private final Set<RoleE> roles = new HashSet<>();
  private final String[] rolenames = { "user", "admin", "root" };
  
  /**
   * Empty constructor.
   */
  public UsereNgTest() {
    for (String role : rolenames) {
      roles.add(createRole(role));
    }
    
    instance = resetUserE(instance);
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @BeforeMethod
  public void setUpMethod() throws Exception {
  }

  @AfterMethod
  public void tearDownMethod() throws Exception {
    instance = resetUserE(instance);
  }

  /**
   * Test of getId method, of class UserE.
   */
  @Test
  public void testGetId() {
    System.out.println("getId");
    Long expResult = Long.MIN_VALUE;
    Long result = instance.getId();
    assertEquals(result, expResult);
  }

  /**
   * Test of setId method, of class UserE.
   */
  @Test
  public void testSetId() {
    System.out.println("setId");
    Long id = Long.MAX_VALUE;
    instance.setId(id);
    assertEquals(instance.getId(), id);
  }

  /**
   * Test of getRoles method, of class UserE.
   */
  @Test
  public void testGetRoles() {
    System.out.println("getRoles");
    Set expResult = roles;
    Set result = instance.getRoles();
    assertEquals(result, expResult);
  }

  /**
   * Test of setRoles method, of class UserE.
   */
  @Test
  public void testSetRoles() {
    System.out.println("setRoles");
    instance.setRoles(null);
    assertNull(instance.getRoles());
  }

  /**
   * Test of getToken method, of class UserE.
   */
  @Test
  public void testGetToken() {
    System.out.println("getToken");
    String expResult = "thetoken";
    String result = instance.getToken();
    assertEquals(result, expResult);
  }

  /**
   * Test of setToken method, of class UserE.
   */
  @Test
  public void testSetToken() {
    System.out.println("setToken");
    String token = "newToken";
    instance.setToken(token);
    assertEquals(instance.getToken(), token);
  }

  /**
   * Test of hashCode method, of class UserE.
   */
  @Test
  public void testHashCode() {
    System.out.println("hashCode");
    Long id = Long.MIN_VALUE;
    int expResult = id.hashCode();
    int result = instance.hashCode();
    assertEquals(result, expResult);
  }

  /**
   * Test of equals method, of class UserE.
   * Must have same id.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    UserE object = new UserE();
    object.setId(Long.MIN_VALUE);
    boolean expResult = true;
    boolean result = instance.equals(object);
    assertEquals(result, expResult);
  }

  /**
   * Test of toString method, of class UserE.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    String expResult = "uk.ac.ucl.eidp.auth.model.UserE[ id=-9223372036854775808 ]";
    String result = instance.toString();
    assertEquals(result, expResult);
  }
 
  private UserE resetUserE(UserE userE) {
    if (null != userE) {
      userE.setId(Long.MIN_VALUE);
      userE.setToken("thetoken");
      userE.setRoles(roles);
    }
    return userE;
  }
  
  private RoleE createRole(String rolename) {
    RoleE role = mock(RoleE.class);
    when(role.getRoleName()).thenReturn(rolename);
    return role;
  }
}
