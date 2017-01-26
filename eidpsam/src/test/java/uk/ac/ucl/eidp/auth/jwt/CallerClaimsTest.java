package uk.ac.ucl.eidp.auth.jwt;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit tests for CallerClaims.
 * @author David Guzman
 */
public class CallerClaimsTest {
  
  private final CallerClaims callerClaims = new CallerClaims("subject","issuer");
  
  public CallerClaimsTest() {
  }

  @Test
  public void testGetSub() {
    assertEquals(callerClaims.getSub(),"subject");
  }
  
  @Test
  public void testGetIss() {
    assertEquals(callerClaims.getIss(),"issuer");
  }
  
  @Test
  public void testExp() {
    assertNull(callerClaims.getExp());
  }
  
  @Test
  public void testJti() {
    String uuidRegex = "[0-9a-f]{8}(?:-[0-9a-f]{4}){4}[0-9a-f]{8}";
    Pattern pattern = Pattern.compile(uuidRegex, Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(callerClaims.getJti());
    assertTrue(matcher.matches());
  }

}
