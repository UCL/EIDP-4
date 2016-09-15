package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for method-for-type complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="method-for-type"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;all&gt;
 *         &lt;element name="type" type="{}method-for-type-type"/&gt;
 *         &lt;element name="field" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="operator" type="{}method-for-operator-type"/&gt;
 *       &lt;/all&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "method-for-type", propOrder = {})
public class MethodForType {

  @XmlElement(required = true)
  protected MethodForTypeType type;
  @XmlElement(required = true)
  protected String field;
  @XmlElement(required = true)
  protected MethodForOperatorType operator;

  /**
   * Gets the value of the type property.
   *
   * @return possible object is {@link MethodForTypeType }
   *
   */
  public MethodForTypeType getType() {
    return type;
  }

  /**
   * Sets the value of the type property.
   *
   * @param value allowed object is {@link MethodForTypeType }
   *
   */
  public void setType(MethodForTypeType value) {
    this.type = value;
  }

  /**
   * Gets the value of the field property.
   *
   * @return possible object is {@link String }
   *
   */
  public String getField() {
    return field;
  }

  /**
   * Sets the value of the field property.
   *
   * @param value allowed object is {@link String }
   *
   */
  public void setField(String value) {
    this.field = value;
  }

  /**
   * Gets the value of the operator property.
   *
   * @return possible object is {@link MethodForOperatorType }
   *
   */
  public MethodForOperatorType getOperator() {
    return operator;
  }

  /**
   * Sets the value of the operator property.
   *
   * @param value allowed object is {@link MethodForOperatorType }
   *
   */
  public void setOperator(MethodForOperatorType value) {
    this.operator = value;
  }

}
