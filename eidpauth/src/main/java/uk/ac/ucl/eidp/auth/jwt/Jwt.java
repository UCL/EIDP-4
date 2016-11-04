package uk.ac.ucl.eidp.auth.jwt;

//import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import uk.ac.ucl.eidp.auth.AuthLogin;

import java.util.Date;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

/** 
 * Utility class for the generation and validation of JWT tokens.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@RequestScoped
public class Jwt {
  
  @Inject
  private JwtConfig jwtConfig;
  
  /**
   * Creates a JWT Token.
   * @param loginElement A {@link AuthLogin} object containing the user credentials
   * @param ttl Timestamp
   * @return The JWT token
   */
  public String createJwtToken(AuthLogin loginElement, long ttl) {
    String id = "theID";
    String subject = loginElement.getLogin();
    String issuer = "theIssuer";
    
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);
    
    //We will sign our JWT with our ApiKey secret
    //Let's set the JWT Claims
    JwtBuilder builder = Jwts.builder().setId(id)
                                .setIssuedAt(now)
                                .setSubject(subject)
                                .setIssuer(issuer)
                                .signWith(signatureAlgorithm, jwtConfig.getApiKey());
    
    //if it has been specified, let's add the expiration
    if (ttl >= 0) {
      long expMillis = nowMillis + ttl;
      Date exp = new Date(expMillis);
      builder.setExpiration(exp);
    }
 
    //Builds the JWT and serializes it to a compact, URL-safe string
    return builder.compact();
  }
  
//  private void parseJwt(String jwt) {
// 
//    //This line will throw an exception if it is not a signed JWS (as expected)
//    Claims claim = Jwts.parser().setSigningKey(jwtConfig.getApiKey()).parseClaimsJws(jwt).getBody();
//    System.out.println("ID: " + claim.getId());
//    System.out.println("Subject: " + claim.getSubject());
//    System.out.println("Issuer: " + claim.getIssuer());
//    System.out.println("Expiration: " + claim.getExpiration());
//  }


}
