/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ucl.eidp.auth.jaas;

import java.io.IOException;
import javax.inject.Inject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import uk.ac.ucl.eidp.auth.Credentials;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class EidpCallbackHandler implements CallbackHandler {
    
    @Inject
    private Credentials credentials;

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
