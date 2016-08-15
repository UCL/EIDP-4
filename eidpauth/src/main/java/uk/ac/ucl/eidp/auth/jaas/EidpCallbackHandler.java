package uk.ac.ucl.eidp.auth.jaas;

import uk.ac.ucl.eidp.auth.AuthLogin;

import java.io.IOException;
import javax.inject.Inject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class EidpCallbackHandler implements CallbackHandler {

  @Inject
  private AuthLogin credentials;

  @Override
  public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
    for (Callback callback : callbacks) {
      if (callback instanceof NameCallback) {
        NameCallback nameCallback = (NameCallback) callback;
        nameCallback.setName(credentials.getLogin());
      } else if (callback instanceof PasswordCallback) {
        PasswordCallback passwordCallback = (PasswordCallback) callback;
        passwordCallback.setPassword(credentials.getPassword().toCharArray());
      } else {
        throw new UnsupportedCallbackException(callback);
      }
    }
  }

}
