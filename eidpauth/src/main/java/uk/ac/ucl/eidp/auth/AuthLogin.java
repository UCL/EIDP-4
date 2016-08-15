package uk.ac.ucl.eidp.auth;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@XmlRootElement
public class AuthLogin implements Serializable {

  private String login;
  private String password;

  public AuthLogin(String login, String password) {
    this.login = login;
    this.password = password;
  }

  public String getLogin() {
    return login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
