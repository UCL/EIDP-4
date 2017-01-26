package uk.ac.ucl.eidp.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import java.security.Key;
import java.util.Date;


/** 
 * Utility class for the generation and validation of JWT tokens.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class Jwt {
  
  /**
   * Generates a JSON Web Token using HS256 signature algorithm.
   * @param callerClaims The JWT claims.
   * @param apiKey The API key.
   * @param validForMs Validity in milliseconds of the token to be generated.
   * @return The JSON Web Token.
   */
  public static String createJwtToken(CallerClaims callerClaims, Key apiKey, long validForMs) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    
    long nowMs = System.currentTimeMillis();
    Date now = new Date(nowMs);
    
    JwtBuilder builder = Jwts.builder().setId(callerClaims.getJti())
                                .setIssuedAt(now)
                                .setSubject(callerClaims.getSub())
                                .setIssuer(callerClaims.getIss())
                                .signWith(signatureAlgorithm, apiKey);

    if (validForMs > 0) {
      builder.setExpiration(new Date(nowMs + validForMs));
    }
 
    return builder.compact();
  }
  
  /**
   * Extract the subject information from the JWT.
   * @param jwt the JSON web token
   * @param apiKey the API key
   * @return a Subject with JWT claims added as Principals
   */
  public static CallerClaims toCallerClaims(String jwt, Key apiKey) {
    try {
      Claims claim = Jwts.parser().setSigningKey(apiKey).parseClaimsJws(jwt).getBody();
      CallerClaims callerClaims = new CallerClaims(claim.getSubject(), claim.getIssuer());
      callerClaims.setExp(claim.getExpiration());
      return callerClaims;
    } catch (SignatureException ex) {
      return (null);
    }
  }
}
