package uk.ac.ucl.eidp.auth.jaas;

import uk.ac.ucl.eidp.auth.UserController;
import uk.ac.ucl.eidp.auth.model.UserE;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class SingleFactorLoginModule implements LoginModule {

  private Subject subject;
  private CallbackHandler callbackHandler;
  //private Map<String, ?> sharedState;
  //private Map<String, ?> options;
  //private boolean debug = false;

  private UserController userController;

  private BeanManager beanManager;

  private static final Logger logger = Logger.getLogger(SingleFactorLoginModule.class.getName());

  private void setUserService() {
    if (userController == null) {
      try {
        Context context = new InitialContext();
        beanManager = (BeanManager) context.lookup("java:comp/BeanManager");
        Bean<?> bean = beanManager.getBeans(UserController.class).iterator().next();
        CreationalContext creationalCtx = beanManager.createCreationalContext(bean);
        userController = (UserController) beanManager.getReference(
          bean, UserController.class, creationalCtx
        );
      } catch (NamingException namingX) {
        logger.log(Level.SEVERE, "Cannot call UserController bean", namingX);
      }
    }
  }

  @Override
  public void initialize(
      Subject subject, 
      CallbackHandler callbackHandler, 
      Map<String, ?> sharedState, 
      Map<String, ?> options
  ) {
    this.subject = subject;
    this.callbackHandler = callbackHandler;
    //this.sharedState = sharedState;
    //this.options = options;
    //this.debug = "true".equalsIgnoreCase((String)options.get("debug"));
    setUserService();
  }
  
  public void setCallbackHandler(CallbackHandler callbackHandler) {
    this.callbackHandler = callbackHandler;
  }

  @Override
  public boolean login() throws LoginException {
    NameCallback nameCallback = new NameCallback("Name : ");
    PasswordCallback passwordCallback = new PasswordCallback("Password : ", false);
    try {
      callbackHandler.handle(new Callback[] {nameCallback, passwordCallback});

      char[] passwordChar = passwordCallback.getPassword();
      String password = null;
      if (null != passwordChar) {
        password = new String(passwordChar);
      }
      String username = nameCallback.getName();
      nameCallback.setName("");
      passwordCallback.clearPassword();

      UserE userE = null;
      
      if (null == userController) {
        userE = userController.findUser(username, password);
      }

      if (userE == null) {
        throw new FailedLoginException("Authentication failed: User not found.");
      }
      
      Principal loginPrincipal = new LoginPrincipal(username);
      subject.getPrincipals().add(loginPrincipal);

      return true;
    } catch (IOException | UnsupportedCallbackException | LoginException exception) {
      logger.log(Level.SEVERE, "login() method failed", exception);
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
