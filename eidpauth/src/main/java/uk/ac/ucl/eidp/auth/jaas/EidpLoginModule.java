package uk.ac.ucl.eidp.auth.jaas;

import uk.ac.ucl.eidp.auth.UserController;
import uk.ac.ucl.eidp.auth.model.UserE;

import java.io.IOException;
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
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class EidpLoginModule implements LoginModule {

  //private Subject subject;
  private CallbackHandler callbackHandler;
  //private Map<String, ?> sharedState;
  //private Map<String, ?> options;
  //private boolean debug = false;

  private UserController userService;

  private BeanManager beanManager;

  private static final Logger logger = Logger.getLogger(EidpLoginModule.class.getName());

  private void setUserService() {
    if (userService == null) {
      try {
        Context context = new InitialContext();
        beanManager = (BeanManager) context.lookup("java:comp/BeanManager");
        Bean<?> bean = beanManager.getBeans(UserController.class).iterator().next();
        CreationalContext creationalCtx = beanManager.createCreationalContext(bean);
        userService = (UserController) beanManager.getReference(
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
    //this.subject = subject;
    this.callbackHandler = callbackHandler;
    //this.sharedState = sharedState;
    //this.options = options;
    //this.debug = "true".equalsIgnoreCase((String)options.get("debug"));
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
