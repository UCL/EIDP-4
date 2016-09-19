package uk.ac.ucl.eidp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
public class StrategyResolver {

  private final Map<String, Properties> databaseNodes = new HashMap<>();
  private static final String nodetypeProperty = "uk.ac.ucl.eidp.data.NodeType";
  private static final String customStrategyJndi = "uk.ac.ucl.eidp.data.DBMappingStrategy";

  @Inject
  @NodeQualifier(NodeType.POOL)
  private DbMappingStrategy poolStrategy;

  @Inject
  @NodeQualifier(NodeType.JDBC)
  private DbMappingStrategy jdbcStrategy;

  @Inject
  @NodeQualifier(NodeType.EIDP)
  private DbMappingStrategy eidpStrategy;

  /**
   * Looks for a database node that matches the identifier provided and creates an instance of the
   * strategy to access the database. If it is not found then it will search in the local JNDI
   * context for an instance of the class specified under the property
   * uk.ac.ucl.eidp.data.DBMappingStrategy
   *
   * @param databaseNodeId The identifier of the database node.
   * @return A {@link DbMappingStrategy} which could be a pool connection, a remote EIDP or JDBC.
   */
  public DbMappingStrategy getDbMappingStrategyForId(String databaseNodeId) {

    Properties properties = getPropertiesForDatabaseNodeId(databaseNodeId);
    
    DbMappingStrategy dbMappingStrategy = null;
    switch (NodeType.valueOf(properties.getProperty(nodetypeProperty))) {
      case JDBC:
        dbMappingStrategy = jdbcStrategy;
        break;
      case POOL:
        dbMappingStrategy = poolStrategy;
        break;
      case EIDP:
        dbMappingStrategy = eidpStrategy;
        break;
      case CUSTOM: {
        try {
          Context ctx = new InitialContext();
          dbMappingStrategy = (DbMappingStrategy) ctx.lookup(
                  properties.getProperty(customStrategyJndi)
          );
        } catch (NamingException ex) {
          throw new UnsupportedOperationException(customStrategyJndi + " could not be found. ", ex);
        }
      }
      break;
      default:
        throw new AssertionError(NodeType.valueOf(properties.getProperty(nodetypeProperty)).name());
    }
    if (null == dbMappingStrategy) {
      throw new UnsupportedOperationException("Instance DBMappingStrategy could not be created");
    }
    dbMappingStrategy.setProperties(properties);
    return dbMappingStrategy;

  }
  
  private Properties getPropertiesForDatabaseNodeId(String databaseNodeId) {
    Properties properties;
    if (!databaseNodes.containsKey(databaseNodeId)) {
      properties = new Properties();
      try (InputStream is = getClass().getClassLoader().getResourceAsStream(
              "META-INF/eidp/" + databaseNodeId + ".properties"
          )) {
        properties.load(is);
      } catch (IOException ex) {
        Logger.getLogger(DbMapping.class.getName()).log(Level.SEVERE, null, ex);
      }
      databaseNodes.put(databaseNodeId, properties);
    } else {
      properties = databaseNodes.get(databaseNodeId);
    }
    return properties;
  }

}
