/*
 * Copyright 2015 David Guzman <d.guzman at ucl.ac.uk>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ucl.eidp.auth.jaas;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Map;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import uk.ac.ucl.eidp.auth.UserController;
import uk.ac.ucl.eidp.auth.model.UserE;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class EidpLoginModule implements LoginModule {

//  private Subject subject;
  private CallbackHandler callbackHandler;
//  private Map<String, ?> sharedState;
//  private Map<String, ?> options;
//  private boolean debug = false;

  private UserController userService;

  private BeanManager beanManager;

  private static final Logger logger = Logger.getLogger(EidpLoginModule.class.getName());

  private void setUserService() {
    if (userService == null) {
      try {
        Context context = new InitialContext();
        beanManager = (BeanManager) context.lookup("java:comp/BeanManager");
        Bean<?> bean = beanManager.getBeans(UserController.class).iterator().next();
        CreationalContext cc = beanManager.createCreationalContext(bean);
        userService = (UserController) beanManager.getReference(bean, UserController.class, cc);
      } catch (NamingException e) {
        logger.log(Level.SEVERE, "Cannot call UserController bean", e);
      }
    }
  }

  @Override
  public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
//    this.subject = subject;
    this.callbackHandler = callbackHandler;
//    this.sharedState = sharedState;
//    this.options = options;
//    this.debug = "true".equalsIgnoreCase((String)options.get("debug"));
    setUserService();
  }

  @Override
  public boolean login() throws LoginException {
    NameCallback nameCallback = new NameCallback("Name : ");
    PasswordCallback passwordCallback = new PasswordCallback("Password : ", false);
    try {
      callbackHandler.handle(new Callback[] {nameCallback, passwordCallback});
      String username = nameCallback.getName();
      String password = new String(passwordCallback.getPassword());
      nameCallback.setName("");
      passwordCallback.clearPassword();
      UserE customer = userService.findUser(username, password);

      if (customer == null) {
        throw new LoginException("Authentication failed");
      }

      return true;
    } catch (IOException | UnsupportedCallbackException | LoginException e) {
      logger.log(Level.SEVERE, "login() method failed", e);
      return false;
    }
  }

  @Override
  public boolean commit() throws LoginException {
    return true;
  }

  @Override
  public boolean abort() throws LoginException {
    return true;
  }

  @Override
  public boolean logout() throws LoginException {
    return true;
  }

}
