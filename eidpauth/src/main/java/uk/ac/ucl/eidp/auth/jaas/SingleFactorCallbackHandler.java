package uk.ac.ucl.eidp.auth.jaas;

import uk.ac.ucl.eidp.auth.AuthLogin;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class SingleFactorCallbackHandler implements CallbackHandler {
  
  private final String login;
  private final String password;
  
  public SingleFactorCallbackHandler(AuthLogin authLogin) {
    this.login = authLogin.getLogin();
    this.password = authLogin.getPassword();
  }

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (Callback callback : callbacks) {
      if (callback instanceof NameCallback) {
        NameCallback nameCallback = (NameCallback) callback;
        nameCallback.setName(this.login);
      } else if (callback instanceof PasswordCallback) {
        PasswordCallback passwordCallback = (PasswordCallback) callback;
        passwordCallback.setPassword(this.password.toCharArray());
      } else {
        throw new UnsupportedCallbackException(callback);
      }
    }
  }

}
