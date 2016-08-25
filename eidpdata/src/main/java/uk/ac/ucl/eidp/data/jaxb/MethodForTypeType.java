package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * Java class for method-for-type-type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;simpleType name="method-for-type-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="and"/&gt;
 *     &lt;enumeration value="or"/&gt;
 *     &lt;enumeration value="not"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 *
 */
@XmlType(name = "method-for-type-type")
@XmlEnum
public enum MethodForTypeType {

  @XmlEnumValue("and")
  AND("and"),
  @XmlEnumValue("or")
  OR("or"),
  @XmlEnumValue("not")
  NOT("not");
  private final String value;

  MethodForTypeType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  /**
   * Obtains a {@link MethodForTypeType} from its corresponding name.
   * @param value The name of the method.
   * @return The corresponding {@link MethodForTypeType}.
   */
  public static MethodForTypeType fromValue(String value) {
    for (MethodForTypeType c : MethodForTypeType.values()) {
      if (c.value.equals(value)) {
        return c;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
