package uk.ac.ucl.eidp.service.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Objects;

/**
 * Model class for /login resource.
 * @author rebmdgu
 */
public class AuthToken {
  
  private String authToken = null;
  
  public AuthToken authToken(String authToken) {
    this.authToken = authToken;
    return this;
  }
  
  @ApiModelProperty(example = "null", required = true, value = "")
  public String getAuthToken() {
    return authToken;
  }
  
  public void setAuthToken(String authToken) {
    this.authToken = authToken;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    AuthToken objToken = (AuthToken) obj;
    return Objects.equals(authToken, objToken.authToken);
  }
  
  @Override
  public int hashCode() {
    return Objects.hash(authToken);
  }
  
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AuthToken {\n");    
    sb.append("    authToken: ").append(toIndentedString(authToken)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object obj) {
    if (obj == null) {
      return "null";
    }
    return obj.toString().replace("\n", "\n    ");
  }
}
