package uk.ac.ucl.eidp.data;

import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;
import uk.ac.ucl.eidp.data.jaxb.StatementProducer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class StatementGeneratorProducer {

  private final String sqlGeneratorProperty = "uk.ac.ucl.eidp.data.StatementGenerator";
  private String sqlGeneratorClass = "uk.ac.ucl.eidp.data.jaxb.JaxbStatementGenerator";
  private final String propertiesFile = "META-INF/eidp.properties";

  /**
   * Injects a producer of a {@link StatementGenerator}. Defaults to {@link JaxbStatementGenerator}.
   */
  @Inject
  public StatementGeneratorProducer() {

    Properties properties = new Properties();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
      properties.load(is);
    } catch (IOException ex) {
      Logger.getLogger(StatementGeneratorProducer.class.getName()).log(Level.SEVERE, null, ex);
    }
    if (properties.containsKey(sqlGeneratorProperty) 
            && !sqlGeneratorClass.equals(properties.getProperty(sqlGeneratorProperty))
    ) {
      sqlGeneratorClass = properties.getProperty(sqlGeneratorProperty);
    }

  }

  @Produces
  @StatementProducer
  public StatementGenerator newSqlGenerator() {
    StatementGenerator sqlGenerator = new StatementGenerator();
    return sqlGenerator;
  }

}
