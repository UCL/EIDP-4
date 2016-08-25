package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for table-field-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="table-field-type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="type" type="{}table-field-type-type"/&gt;
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "table-field-type", propOrder = {
    "name",
    "type",
    "size"
    })
public class TableFieldType {

  @XmlElement(required = true)
  protected String name;
  @XmlElement(required = true)
  protected TableFieldTypeType type;
  protected int size;
  @XmlAttribute(name = "id", required = true)
  protected String id;

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
   * Gets the value of the type property.
   *
   * @return possible object is {@link TableFieldTypeType }
   *
   */
  public TableFieldTypeType getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link TableFieldTypeType }
   *
   */
  public void setType(TableFieldTypeType value) {
    this.type = value;
  }

  /**
   * Gets the value of the size property.
   *
   */
  public int getSize() {
    return size;
  }

  /**
   * Sets the value of the size property.
   *
   */
  public void setSize(int value) {
    this.size = value;
  }

  /**
   * Gets the value of the id property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getId() {
    return id;
  }

  /**
   * Sets the value of the id property.
   *
   * @param value allowed object is {@link String }
   *
   */
  public void setId(String value) {
    this.id = value;
  }

}
