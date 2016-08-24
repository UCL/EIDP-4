package uk.ac.ucl.eidp.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.Local;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Local
public interface DBMappingStrategy extends Serializable {

  void setProperties(Properties properties);

  List<Map<String, String>> processDbCall(String methodId, Map<String, String> parameters);

  List<Map<String, String>> processDbTransaction(String methodId, Map<String, String> parameters);

}
