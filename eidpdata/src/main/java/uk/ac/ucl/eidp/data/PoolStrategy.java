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
public class PoolStrategy implements DbMappingStrategy {

  private Properties properties;
  private static final String dsJndiName = "datasource-jndi-name";
  private static final String rsScrollType = "resultset-scroll-type";
  private static final String rsConcurrencyMode = "resultset-concurrency-mode";
  private static final String sqlDialectProperty = "uk.ac.ucl.eidp.data.jaxb.JaxbSqlStatement";
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
    Map<String, Parameter> parameterMap = statementGenerator.getParameterSettings(
         parameters.keySet(), 
         datasetPath
    );
    List<Map<String, String>> resultList = new ArrayList<>();
    try {
      int scrollType = 1004; // defaults to ResultSet.TYPE_SCROLL_INSENSITIVE
      int concurrencyMode = 1007; // defaults to ResultSet.CONCUR_READ_ONLY
      if (properties.containsKey(rsScrollType)) {
        scrollType = Integer.getInteger(properties.getProperty(rsScrollType));
      }
      if (properties.containsKey(rsScrollType)) {
        concurrencyMode = Integer.getInteger(properties.getProperty(rsConcurrencyMode));
      }
      PreparedStatement ps = connection.prepareStatement(
            jdbcStatement.split(";")[0], scrollType, concurrencyMode
      );
      setValues(ps, fields, parameters, parameterMap);
      boolean rsbool = ps.execute();

      if (!rsbool) {
        int updateCount = ps.getUpdateCount();
        if (updateCount == 0) {
          ps = connection.prepareStatement(
               jdbcStatement.split(";")[1], scrollType, concurrencyMode
          );
          fields = getJdbcFields(sqlStatement.split(";")[1]);
          setValues(ps, fields, parameters, parameterMap);
          updateCount = ps.executeUpdate();
        }
        Map<String, String> updateCountMap = new HashMap<>();
        updateCountMap.put("updateCount", String.valueOf(updateCount));
        resultList.add(updateCountMap);
        return resultList;
      }

      List<String> methodFields = statementGenerator.getMethodFields(methodId);
      ResultSet resultSet = ps.getResultSet();

      while (resultSet.next()) {
        Map<String, String> rowMap = new HashMap<>();
        for (String k : methodFields) {
          String value = resultSet.getString(k);
          rowMap.put(k, value);
        }
        resultList.add(rowMap);
      }

    } catch (SQLException ex) {
      throw new IllegalStateException("Could not execute PreparedStatement " + jdbcStatement, ex);
    }
    return resultList;

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
      Map<String, Parameter> parameterMap) throws SQLException {

    int idx = 1;
    for (String f : fields) {
      String value = parameters.get(f);
      Integer size = parameterMap.get(f).getSize();
      if (value.length() > size) {
        throw new StringIndexOutOfBoundsException("Variable length exceeds column size");
      }
      try {
        ps.setObject(idx, value, parameterMap.get(f).getSqlType());
      } catch (SQLException ex) {
        ps.clearParameters();
        ps.close();
        throw new IllegalArgumentException("Could not set parameter map in PreparedStatement", ex);
      }
      idx++;
    }
  }

  @Override
  public void setProperties(Properties properties) {
    this.properties = properties;
  }

  @Override
  public List<Map<String, String>> processDbTransaction(
      String methodId, 
      Map<String, String> parameters) {
    throw new UnsupportedOperationException("Not supported yet."); 
  }

}
