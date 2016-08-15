package uk.ac.ucl.eidp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
@LocalBean
public class DBMapping {

  private final String propertiesPath = "META-INF/eidp/mapping.properties";
  private final Properties mappingProperties = new Properties();

  @Inject
  private StrategyResolver strategyResolver;

  /**
   * Loads into {@link DBMapping} the paths associated with each database method.
   */
  @PostConstruct
  public void loadMapping() {
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesPath)) {
      mappingProperties.load(is);
    } catch (IOException ex) {
      throw new UnsupportedOperationException("Cannot load mapping.properties " , ex);
    }
  }

  /**
   * Returns a {@link java.util.List} containing the results of a database call
   * @param methodPath the address of the database method to call
   * @param parameters a {@link java.util.Map} containing the parameters for the database call
   * @return a list containing the results of the database call, each item corresponds to one row
   */
  public List<Map<String, String>> dbAction(String methodPath, Map<String, String> parameters) {

    if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("methodPath is invalid");
    }
    String databaseNodeId = mappingProperties.getProperty(methodPath);

    DBMappingStrategy mappingStrategy = strategyResolver.getDbMappingStrategyForId(databaseNodeId);

    return mappingStrategy.processDbCall(methodPath, parameters);
  }

}