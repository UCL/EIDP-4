package uk.ac.ucl.eidp.data.jaxb;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public abstract class JaxbSqlStatement {

  protected TableType tableType;
  protected MethodType methodType;

  /**
   * Builds an SQL statement from the dataset and method JAXB objects.
   * @param datasetType The JAXB object containing the dataset definitions.
   * @param method The name of the method to convert into SQL.
   * @return The SQL statement.
   */
  public String buildStatement(DatasetType datasetType, String method) {
    this.tableType = datasetType.getTable();
    methodType = datasetType.getMethod().stream().filter(
        m -> (m.getId() == null ? method == null : m.getId().equals(method))
    ).findFirst().get();

    switch (methodType.getType()) {
      case GET:
        return buildGetStatement();
      case SET:
        return buildSetStatement();
      case REMOVE:
        return buildDelStatement();
      default:
        break;
    }
    throw new UnsupportedOperationException("Method not found." + methodType.getId());
  }

  protected abstract String buildGetStatement();

  protected abstract String buildSetStatement();

  protected abstract String buildDelStatement();

  protected String translateId(String id) {
    String tid;
    tid = tableType.getField().stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .get()
            .getName();
    return tid;
  }

  protected boolean isQuotation(String id) {
    return tableType.getField().stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .get()
            .getType()
            .value().matches("String|Date|Timestamp");
  }

}
