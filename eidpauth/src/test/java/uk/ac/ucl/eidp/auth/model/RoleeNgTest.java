package uk.ac.ucl.eidp.auth.model;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests for RoleE.
 * @author rebmdgu
 */
public class RoleeNgTest {
  
  RoleE instance = new RoleE();
  
  public RoleeNgTest() {
    instance = resetRoleE(instance);
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
    instance = resetRoleE(instance);
  }

  /**
   * Test of getId method, of class RoleE.
   */
  @Test
  public void testGetId() {
    System.out.println("getId");
    Long expResult = Long.MIN_VALUE;
    Long result = instance.getId();
    assertEquals(result, expResult);
  }

  /**
   * Test of setId method, of class RoleE.
   */
  @Test
  public void testSetId() {
    System.out.println("setId");
    Long id = Long.MAX_VALUE;
    instance.setId(id);
    assertEquals(instance.getId(), id);
  }

  /**
   * Test of getRoleName method, of class RoleE.
   */
  @Test
  public void testGetRoleName() {
    System.out.println("getRoleName");
    String expResult = "role";
    String result = instance.getRoleName();
    assertEquals(result, expResult);
  }

  /**
   * Test of setRoleName method, of class RoleE.
   */
  @Test
  public void testSetRoleName() {
    System.out.println("setRoleName");
    String roleName = "newrole";
    instance.setRoleName(roleName);
    assertEquals(instance.getRoleName(), roleName);
  }

  /**
   * Test of hashCode method, of class RoleE.
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
   * Test of equals method, of class RoleE.
   */
  @Test
  public void testEquals() {
    System.out.println("equals");
    RoleE object = new RoleE();
    object.setId(Long.MIN_VALUE);
    boolean expResult = true;
    boolean result = instance.equals(object);
    assertEquals(result, expResult);
  }

  /**
   * Test of toString method, of class RoleE.
   */
  @Test
  public void testToString() {
    System.out.println("toString");
    String expResult = "uk.ac.ucl.eidp.auth.model.RoleE[ id=-9223372036854775808 ]";
    String result = instance.toString();
    assertEquals(result, expResult);
  }
  
  private RoleE resetRoleE(RoleE roleE) {
    if (null != roleE) {
      roleE.setId(Long.MIN_VALUE);
      roleE.setRoleName("role");
    }
    return roleE;
  }
}
