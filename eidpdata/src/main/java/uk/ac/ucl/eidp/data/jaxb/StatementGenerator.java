package uk.ac.ucl.eidp.data.jaxb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class StatementGenerator {

  private final XMLInputFactory xif = XMLInputFactory.newFactory();
  private final String datasetTag = "dataset";
  private String sqlDialect = "";
  private final String dotSeparator = "\\.";
  private final int varcharLimit = 254;

  /**
   * Generates the SQL statement for a specific database method.
   * @param methodPath The path id of the database method to call
   * @return A String with the SQL statement
   */
  public String getSqlStatement(String methodPath) {

    if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("methodpath is invalid");
    }
    DatasetType datasetType = getDatasetTypeObject(methodPath);

    JaxbSqlStatement jaxbSqlStatement;
    try {
      jaxbSqlStatement = (JaxbSqlStatement) Class.forName(sqlDialect).newInstance();
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
      Logger.getLogger(StatementGenerator.class.getName()).log(
              Level.SEVERE,
              "Cannot create instance of " + sqlDialect,
              ex
      );
      jaxbSqlStatement = new JaxbSqlAnsi();
    }

    return jaxbSqlStatement.buildStatement(datasetType, methodPath.split(dotSeparator)[2]);

  }

  public void setSqlDialect(String sqlDialect) {
    this.sqlDialect = sqlDialect;
  }

  /**
   * Obtains the settings for each parameter such as field type and size.
   * @param keyset The parameter fields to retrieve the settings for.
   * @param datasetPath The identifier of the database node.
   * @return A map containing a {@link Parameter} object for each parameter field.
   */
  public Map<String, Parameter> getParameterSettings(Set<String> keyset, String datasetPath) {

    if (!datasetPath.matches("[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("datasetPath is invalid");
    }
    Map<String, Parameter> parameterMap = new HashMap<>();
    DatasetType datasetType = getDatasetTypeObject(datasetPath);

    keyset.forEach((String key) -> {
      TableFieldType field = datasetType.getTable().getField().stream()
              .filter(t -> t.getId().equals(key))
              .findFirst()
              .get();
      Parameter parameter = new Parameter();
      parameter.setType(field.getType().value());
      parameter.setSize(field.getSize());
      parameterMap.put(key, parameter);
    });

    return parameterMap;
  }

  /**
   * Retrieves a field from a table.
   * @param fieldId The field identifier.
   * @param datasetPath The location identifier for the database node.
   * @return a JAXB {@link TableFieldType} object.
   */
  public TableFieldType getTableField(String fieldId, String datasetPath) {
    if (!datasetPath.matches("[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("datasetPath is invalid");
    }
    DatasetType datasetType = getDatasetTypeObject(datasetPath);
    return datasetType.getTable().getField().stream()
            .filter(t -> t.getId().equals(fieldId))
            .findFirst()
            .get();
  }

  /**
   * Obtains the authorised roles for a method.
   * @param methodPath The identifier of a method.
   * @return A list with the roles allowed.
   */
  public List<String> getMethodRoles(String methodPath) {
    if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("methodPath is invalid");
    }
    DatasetType datasetType = getDatasetTypeObject(methodPath);
    String method = methodPath.split(dotSeparator)[2];
    MethodType methodType = getMethodType(datasetType, method);
    return methodType.getRoleName();
  }

  /**
   * Retrieves the fields to be used in a database method call.
   * @param methodPath The identifier of a method.
   * @return The fields to use in a database call.
   */
  public List<String> getMethodFields(String methodPath) {
    if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) {
      throw new IllegalArgumentException("methodPath is invalid");
    }
    DatasetType datasetType = getDatasetTypeObject(methodPath);
    String method = methodPath.split(dotSeparator)[2];
    MethodType methodType = getMethodType(datasetType, method);
    return methodType.getFields().getField();
  }

  private DatasetType getDatasetTypeObject(String path) {

    DatasetType datasetType = null;
    XMLStreamReader xsr = null;

    try (InputStream is = getClass().getClassLoader().getResourceAsStream(
            "META-INF/eidp/" + path.split(dotSeparator)[0] + "/resources/db.xml"
    )) {

      xsr = xif.createXMLStreamReader(is, "UTF-8");
      while (xsr.hasNext()) {
        int event = xsr.next();
        if (XMLStreamConstants.START_ELEMENT == event
                && datasetTag.equals(xsr.getLocalName())
                && path.split(dotSeparator)[1].equals(xsr.getAttributeValue(0))) {
          break;
        }
      }

      JAXBContext jc = JAXBContext.newInstance(
              "uk.ac.ucl.eidp.data.jaxb",
              ObjectFactory.class.getClassLoader()
      );
      Unmarshaller unmarshaller = jc.createUnmarshaller();
      JAXBElement<DatasetType> jb = unmarshaller.unmarshal(xsr, DatasetType.class);
      datasetType = jb.getValue();

    } catch (XMLStreamException | IOException ex) {
      throw new UnsupportedOperationException(
              "Could not generate XMLStreamReader for given context or dataset", ex
      );
    } catch (JAXBException ex) {
      throw new UnsupportedOperationException(
              "JAXB could not unmarshall XMLStreamReader for given context", ex
      );
    }

    try {
      xsr.close();
    } catch (XMLStreamException ex) {
      Logger.getLogger(StatementGenerator.class.getName()).log(
          Level.SEVERE, "Cannot close XMLStreamReader", ex
      );
    }

    return datasetType;
  }

  private MethodType getMethodType(DatasetType datasetType, String method) {
    return datasetType.getMethod().stream().filter(
        m -> (m.getId() == null ? method == null : m.getId().equals(method))
    ).findFirst().get();
  }

  public class Parameter {

    private String type;
    private Integer size;
    //private String format;

    public String getType() {
      return type;
    }

    public void setType(String type) {
      this.type = type;
    }

    public Integer getSize() {
      return size;
    }

    public void setSize(Integer size) {
      this.size = size;
    }

    //public String getFormat() {
    //  return format;
    //}
    //
    //public void setFormat(String format) {
    //  this.format = format;
    //}
    
    /**
     * Maps a field type to a {@link Types} value.
     * @return the {@link Types} mapped value.
     */
    public Integer getSqlType() {
      TableFieldTypeType fieldType = TableFieldTypeType.fromValue(type);
      switch (fieldType) {
        case STRING:
          if (getSize() > varcharLimit) {
            return Types.LONGVARCHAR;
          }
          return Types.VARCHAR;
        case INTEGER:
          return Types.INTEGER;
        case FLOAT:
          return Types.FLOAT;
        case DATE:
          return Types.DATE;
        default:
          return null;
      }
    }

  }
}
