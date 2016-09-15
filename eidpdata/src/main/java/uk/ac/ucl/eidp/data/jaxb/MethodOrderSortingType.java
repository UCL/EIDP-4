package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for method-order-sorting-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="method-order-sorting-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ASC"/&gt;
 *     &lt;enumeration value="DESC"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "method-order-sorting-type")
@XmlEnum
public enum MethodOrderSortingType {

  ASC,
  DESC;

  public String value() {
    return name();
  }

  public static MethodOrderSortingType fromValue(String value) {
    return valueOf(value);
  }

}
