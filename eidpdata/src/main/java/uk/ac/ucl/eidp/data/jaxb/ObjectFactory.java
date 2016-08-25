package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the uk.ac.ucl.eidp.data.jaxb package.
 * 
 * <p>An ObjectFactory allows you to programmatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 *
 */
@XmlRegistry
public class ObjectFactory {

  /**
   * Create an instance of {@link Db }.
   *
   */
  public Db createDb() {
    return new Db();
  }

  /**
   * Create an instance of {@link DatasetType }.
   *
   */
  public DatasetType createDatasetType() {
    return new DatasetType();
  }

  /**
   * Create an instance of {@link MethodType }.
   *
   */
  public MethodType createMethodType() {
    return new MethodType();
  }

  /**
   * Create an instance of {@link MethodOrderType }.
   *
   */
  public MethodOrderType createMethodOrderType() {
    return new MethodOrderType();
  }

  /**
   * Create an instance of {@link TableFieldType }.
   *
   */
  public TableFieldType createTableFieldType() {
    return new TableFieldType();
  }

  /**
   * Create an instance of {@link MethodForType }.
   *
   */
  public MethodForType createMethodForType() {
    return new MethodForType();
  }

  /**
   * Create an instance of {@link TableType }.
   *
   */
  public TableType createTableType() {
    return new TableType();
  }

  /**
   * Create an instance of {@link MethodFieldsType }.
   *
   */
  public MethodFieldsType createMethodFieldsType() {
    return new MethodFieldsType();
  }

}
