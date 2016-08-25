package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for method-type-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="method-type-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="get"/&gt;
 *     &lt;enumeration value="set"/&gt;
 *     &lt;enumeration value="remove"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "method-type-type")
@XmlEnum
public enum MethodTypeType {

  @XmlEnumValue("get")
  GET("get"),
  @XmlEnumValue("set")
  SET("set"),
  @XmlEnumValue("remove")
  REMOVE("remove");
  private final String value;

  MethodTypeType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  /**
   * Obtains a {@link MethodTypeType} from its corresponding method name.
   * @param value The name of the method.
   * @return The corresponding {@link MethodTypeType}.
   */
  public static MethodTypeType fromValue(String value) {
    for (MethodTypeType c : MethodTypeType.values()) {
      if (c.value.equals(value)) {
        return c;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
