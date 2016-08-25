package uk.ac.ucl.eidp.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for table-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="table-type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="field" type="{}table-field-type" maxOccurs="unbounded"/&gt;
 *         &lt;element name="primary-key" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="primary-key-generation" 
 *                     type="{http://www.w3.org/2001/XMLSchema}anyType"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "table-type", propOrder = {
    "name",
    "field",
    "primaryKey",
    "primaryKeyGeneration"
    })
public class TableType {

  @XmlElement(required = true)
  protected String name;
  @XmlElement(required = true)
  protected List<TableFieldType> field;
  @XmlElement(name = "primary-key", required = true)
  protected String primaryKey;
  @XmlElement(name = "primary-key-generation", required = true)
  protected Object primaryKeyGeneration;

  /**
   * Gets the value of the name property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getName() {
    return name;
  }

  /**
   * Sets the value of the name property.
   *
   * @param value allowed object is {@link String }
   *
   */
  public void setName(String value) {
    this.name = value;
  }

  /**
   * Gets the value of the field property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the field property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getField().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link TableFieldType }
   *
   *
   */
  public List<TableFieldType> getField() {
    if (field == null) {
      field = new ArrayList<TableFieldType>();
    }
    return this.field;
  }

  /**
   * Gets the value of the primaryKey property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getPrimaryKey() {
    return primaryKey;
  }

  /**
   * Sets the value of the primaryKey property.
   *
   * @param value allowed object is {@link String }
   *
   */
  public void setPrimaryKey(String value) {
    this.primaryKey = value;
  }

  /**
   * Gets the value of the primaryKeyGeneration property.
   *
   * @return possible object is {@link Object }
   *
   */
  public Object getPrimaryKeyGeneration() {
    return primaryKeyGeneration;
  }

  /**
   * Sets the value of the primaryKeyGeneration property.
   *
   * @param value allowed object is {@link Object }
   *
   */
  public void setPrimaryKeyGeneration(Object value) {
    this.primaryKeyGeneration = value;
  }

}
