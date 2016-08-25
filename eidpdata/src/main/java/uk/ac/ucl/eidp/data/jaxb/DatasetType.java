package uk.ac.ucl.eidp.data.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for dataset-type complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="dataset-type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="table" type="{}table-type"/&gt;
 *         &lt;element name="method" type="{}method-type" maxOccurs="unbounded"/&gt;
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
@XmlType(name = "dataset-type", propOrder = {
    "table",
    "method"
    })
public class DatasetType {

  @XmlElement(required = true)
  protected TableType table;
  @XmlElement(required = true)
  protected List<MethodType> method;
  @XmlAttribute(name = "id", required = true)
  protected String id;

  /**
   * Gets the value of the table property.
   *
   * @return possible object is {@link TableType }
   *
   */
  public TableType getTable() {
    return table;
  }

  /**
   * Sets the value of the table property.
   *
   * @param value allowed object is {@link TableType }
   *
   */
  public void setTable(TableType value) {
    this.table = value;
  }

  /**
   * Gets the value of the method property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the method property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getMethod().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link MethodType }
   *
   * @return A list containing the {@link MethodType } node.
   */
  public List<MethodType> getMethod() {
    if (method == null) {
      method = new ArrayList<MethodType>();
    }
    return this.method;
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
