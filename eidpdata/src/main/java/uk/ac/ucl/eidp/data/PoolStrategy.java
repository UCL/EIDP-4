package uk.ac.ucl.eidp.data;

import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator.Parameter;
import uk.ac.ucl.eidp.data.jaxb.StatementProducer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Stateless
@NodeQualifier(NodeType.POOL)
public class PoolStrategy implements DBMappingStrategy {

  private Properties properties;
  private final String dsJndiName = "datasource-jndi-name";
  private final String rsScrollType = "resultset-scroll-type";
  private final String rsConcurrencyMode = "resultset-concurrency-mode";
  private final String sqlDialectProperty = "uk.ac.ucl.eidp.data.jaxb.JaxbSqlStatement";
  private String sqlDialect = "uk.ac.ucl.eidp.data.jaxb.JaxbSqlAnsi";
  private Connection connection;
  private final Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')");

  @Inject
  @StatementProducer
  private StatementGenerator statementGenerator;

  private void initialiseConnection() {
    if (properties.containsKey(sqlDialectProperty)) {
      sqlDialect = properties.getProperty(sqlDialectProperty);
    }

    statementGenerator.setSqlDialect(sqlDialect);
    if (!properties.containsKey(dsJndiName)) {
      throw new IllegalStateException("Property " + dsJndiName + " not found");
    }
    String datasourceJndiName = properties.getProperty(dsJndiName);
    DataSource source;
    try {
      Context context = new InitialContext();
      source = (DataSource) context.lookup(datasourceJndiName);
      connection = source.getConnection();
    } catch (SQLException | NamingException ex) {
      throw new IllegalStateException("Could not get Connection from the specified DataSource", ex);
    }
  }

  @Override
  public List<Map<String, String>> processDbCall(String methodId, Map<String, String> parameters) {
    if (null == connection) {
      initialiseConnection();
    }

    String sqlStatement = statementGenerator.getSqlStatement(methodId);

    List<String> fields = getJdbcFields(sqlStatement.split(";")[0]);
    String jdbcStatement = sqlStatement.replaceAll(findParametersPattern.patter‌n(), "?");

    String datasetPath = methodId.substring(0, methodId.lastIndexOf('.'));
    Map<String, Parameter> p = statementGenerator.getParameterSettings(parameters.keySet(), datasetPath);
    List<Map<String, String>> l = new ArrayList<>();
    try {
      int scroll_type = 1004; // defaults to ResultSet.TYPE_SCROLL_INSENSITIVE
      int concurrency_mode = 1007; // defaults to ResultSet.CONCUR_READ_ONLY
      if (properties.containsKey(rsScrollType)) {
        scroll_type = Integer.getInteger(properties.getProperty(rsScrollType));
      }
      if (properties.containsKey(rsScrollType)) {
        concurrency_mode = Integer.getInteger(properties.getProperty(rsConcurrencyMode));
      }
      PreparedStatement ps = connection.prepareStatement(jdbcStatement.split(";")[0], scroll_type, concurrency_mode);
      setValues(ps, fields, parameters, p);
      boolean rsbool = ps.execute();

      if (!rsbool) {
        int updateCount = ps.getUpdateCount();
        if (updateCount == 0) {
          ps = connection.prepareStatement(jdbcStatement.split(";")[1], scroll_type, concurrency_mode);
          fields = getJdbcFields(sqlStatement.split(";")[1]);
          setValues(ps, fields, parameters, p);
          updateCount = ps.executeUpdate();
        }
        Map<String, String> m = new HashMap<>();
        m.put("updateCount", String.valueOf(updateCount));
        l.add(m);
        return l;
      }

      List<String> methodFields = statementGenerator.getMethodFields(methodId);
      ResultSet resultSet = ps.getResultSet();

      while (resultSet.next()) {
        Map<String, String> m = new HashMap<>();
        for (String k : methodFields) {
          String v = resultSet.getString(k);
          m.put(k, v);
        }
        l.add(m);
      }

    } catch (SQLException ex) {
      throw new IllegalStateException("Could not execute PreparedStatement " + jdbcStatement, ex);
    }
    return l;

  }

  private List<String> getJdbcFields(String sqlStatement) {
    Matcher matcher = findParametersPattern.matcher(sqlStatement);
    List<String> fields = new ArrayList<>();
    while (matcher.find()) {
      fields.add(matcher.group().substring(1));
    }
    return fields;
  }

  private void setValues(
    PreparedStatement ps, 
    List<String> fields, 
    Map<String, String> parameters, 
    Map<String, Parameter> p) throws SQLException {

    int idx = 1;
    for (String f : fields) {
      String value = parameters.get(f);
      Integer size = p.get(f).getSize();
      if (value.length() > size) {
        throw new StringIndexOutOfBoundsException("Variable length exceeds column size");
      }
      try {
        ps.setObject(idx, value, p.get(f).getSqlType());
      } catch (SQLException ex) {
        ps.clearParameters();
        ps.close();
        throw new IllegalArgumentException("Could not set parameter object in PreparedStatement", ex);
      }
      idx++;
    }
  }

  @Override
  public void setProperties(Properties p) {
    properties = p;
  }

  @Override
  public List<Map<String, String>> processDbTransaction(
    String methodId, 
    Map<String, String> parameters) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

}
