package uk.ac.ucl.eidp.auth.jwt;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.security.Key;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.spec.SecretKeySpec;

/**
 * Unit tests for Jwt.
 * @author David Guzman d.guzman at ucl.ac.uk
 */
public class JwtTest {
  
  private static final String apiKeySecret = "lIySuazq3y2dCKaJhmJyuA==";
  private static final String credentialsEncoding = "UTF-8";
  private final Key apiKey;
  private String token;
  private CallerClaims expected;
  
  public JwtTest() {
    apiKey = new SecretKeySpec(apiKeySecret.getBytes(Charset.forName(credentialsEncoding)),"AES");
  }

  @Test
  public void testCreateJwtToken() throws ParseException {
    expected = new CallerClaims("usertest@ucl.ac.uk","eidp");
    long millisInYear = 31536000000L;
    token = Jwt.createJwtToken(expected, apiKey, millisInYear);
    String jwtRegex = "^[a-zA-Z0-9\\-_]+?\\.[a-zA-Z0-9\\-_]+?\\.([a-zA-Z0-9\\-_]+)?$";
    Pattern pattern = Pattern.compile(jwtRegex);
    Matcher matcher = pattern.matcher(token);
    assertTrue(matcher.matches());
  }
  
  @Test(dependsOnMethods = { "testCreateJwtToken" })
  public void testToCallerClaims() throws ParseException {
    CallerClaims actual = Jwt.toCallerClaims(token, apiKey);
    // Sub, Iss and Exp should be the same
    assertEquals(actual.getSub(),"usertest@ucl.ac.uk");
    assertEquals(actual.getIss(),"eidp");
    // Jtis should make instances unequal
    assertNotEquals(actual,expected);
  }

}
