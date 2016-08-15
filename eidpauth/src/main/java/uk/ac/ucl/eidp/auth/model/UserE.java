package uk.ac.ucl.eidp.auth.model;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Entity
@NamedQueries({
  @NamedQuery(
    name = UserE.FIND_BY_LOGIN_PASSWORD,
    query = "SELECT u FROM UserE u WHERE u.login = :login AND u.password = :password"
  )
})
public class UserE implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String login;
  private String password;
  private String token;

  @OneToMany
  private Set<RoleE> roles;

  public static final String FIND_BY_LOGIN_PASSWORD = "UserE.findByLoginAndPassword";

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Set<RoleE> getRoles() {
    return roles;
  }

  public void setRoles(Set<RoleE> roles) {
    this.roles = roles;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof UserE)) {
      return false;
    }
    UserE other = (UserE) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "uk.ac.ucl.eidp.auth.model.UserE[ id=" + id + " ]";
  }

}
