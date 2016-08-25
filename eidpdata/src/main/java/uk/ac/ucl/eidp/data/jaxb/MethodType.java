package uk.ac.ucl.eidp.data.jaxb;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for method-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="method-type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="role-name" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="type" type="{}method-type-type" minOccurs="0"/&gt;
 *         &lt;element name="fields" type="{}method-fields-type" minOccurs="0"/&gt;
 *         &lt;element name="for" type="{}method-for-type" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="order" type="{}method-order-type" maxOccurs="unbounded" 
 *                     minOccurs="0"/&gt;
 *         &lt;element name="offset" type="{http://www.w3.org/2001/XMLSchema}integer" 
 *                     minOccurs="0"/&gt;
 *         &lt;element name="limit" type="{http://www.w3.org/2001/XMLSchema}integer" 
 *                     minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "method-type", propOrder = {
    "roleName",
    "type",
    "fields",
    "_for",
    "order",
    "offset",
    "limit"
    })
public class MethodType {

  @XmlElement(name = "role-name")
  protected List<String> roleName;
  protected MethodTypeType type;
  protected MethodFieldsType fields;
  @XmlElement(name = "for")
  protected List<MethodForType> forType;
  protected List<MethodOrderType> order;
  protected BigInteger offset;
  protected BigInteger limit;
  @XmlAttribute(name = "id", required = true)
  protected String id;

  /**
   * Gets the value of the roleName property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the roleName property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getRoleName().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link String }
   *
   *
   */
  public List<String> getRoleName() {
    if (roleName == null) {
      roleName = new ArrayList<String>();
    }
    return this.roleName;
  }

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link MethodTypeType }
   *
   */
  public MethodTypeType getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link MethodTypeType }
   *
   */
  public void setType(MethodTypeType value) {
    this.type = value;
  }

  /**
   * Gets the value of the fields property.
   *
   * @return possible object is {@link MethodFieldsType }
   *
   */
  public MethodFieldsType getFields() {
    return fields;
  }

  /**
   * Sets the value of the fields property.
   *
   * @param value allowed object is {@link MethodFieldsType }
   *
   */
  public void setFields(MethodFieldsType value) {
    this.fields = value;
  }

  /**
   * Gets the value of the for property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the for property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getFor().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link MethodForType }
   *
   *
   */
  public List<MethodForType> getFor() {
    if (forType == null) {
      forType = new ArrayList<MethodForType>();
    }
    return this.forType;
  }

  /**
   * Gets the value of the order property.
   *
   * <p>This accessor method returns a reference to the live list, not a snapshot. Therefore any
   * modification you make to the returned list will be present inside the JAXB object. This is why
   * there is not a <CODE>set</CODE> method for the order property.
   *
   * <p>For example, to add a new item, do as follows:
   * <pre>
   *    getOrder().add(newItem);
   * </pre>
   *
   *
   * <p>Objects of the following type(s) are allowed in the list {@link MethodOrderType }
   *
   *
   */
  public List<MethodOrderType> getOrder() {
    if (order == null) {
      order = new ArrayList<MethodOrderType>();
    }
    return this.order;
  }

  /**
   * Gets the value of the offset property.
   *
   * @return possible object is {@link BigInteger }
   *
   */
  public BigInteger getOffset() {
    return offset;
  }

  /**
   * Sets the value of the offset property.
   *
   * @param value allowed object is {@link BigInteger }
   *
   */
  public void setOffset(BigInteger value) {
    this.offset = value;
  }

  /**
   * Gets the value of the limit property.
   *
   * @return possible object is {@link BigInteger }
   *
   */
  public BigInteger getLimit() {
    return limit;
  }

  /**
   * Sets the value of the limit property.
   *
   * @param value allowed object is {@link BigInteger }
   *
   */
  public void setLimit(BigInteger value) {
    this.limit = value;
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
