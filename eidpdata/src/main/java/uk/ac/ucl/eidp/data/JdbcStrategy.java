package uk.ac.ucl.eidp.data;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.Stateless;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
@NodeQualifier(NodeType.JDBC)
public class JdbcStrategy implements DbMappingStrategy {

  @Override
  public List<Map<String, String>> processDbCall(String methodId, Map<String, String> parameters) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public void setProperties(Properties properties) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  @Override
  public List<Map<String, String>> processDbTransaction(
      String methodId, 
      Map<String, String> parameters) {
    throw new UnsupportedOperationException("Not supported yet.");
  }

}
