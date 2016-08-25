package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for method-for-operator-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="method-for-operator-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="equal"/&gt;
 *     &lt;enumeration value="notequal"/&gt;
 *     &lt;enumeration value="like"/&gt;
 *     &lt;enumeration value="isnull"/&gt;
 *     &lt;enumeration value="isnotnull"/&gt;
 *     &lt;enumeration value="gt"/&gt;
 *     &lt;enumeration value="get"/&gt;
 *     &lt;enumeration value="lt"/&gt;
 *     &lt;enumeration value="let"/&gt;
 *     &lt;enumeration value="similarto"/&gt;
 *     &lt;enumeration value="fuzzy"/&gt;
 *     &lt;enumeration value="in"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "method-for-operator-type")
@XmlEnum
public enum MethodForOperatorType {

  @XmlEnumValue("equal")
  EQUAL("equal"),
  @XmlEnumValue("notequal")
  NOTEQUAL("notequal"),
  @XmlEnumValue("like")
  LIKE("like"),
  @XmlEnumValue("isnull")
  ISNULL("isnull"),
  @XmlEnumValue("isnotnull")
  ISNOTNULL("isnotnull"),
  @XmlEnumValue("gt")
  GT("gt"),
  @XmlEnumValue("get")
  GET("get"),
  @XmlEnumValue("lt")
  LT("lt"),
  @XmlEnumValue("let")
  LET("let"),
  @XmlEnumValue("similarto")
  SIMILARTO("similarto"),
  @XmlEnumValue("fuzzy")
  FUZZY("fuzzy"),
  @XmlEnumValue("in")
  IN("in");
  private final String value;

  MethodForOperatorType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  /**
   * Obtains a {@link MethodForOperatorType} from its corresponding method name.
   * @param value The name of the method.
   * @return The corresponding {@link MethodForOperatorType}.
   */
  public static MethodForOperatorType fromValue(String value) {
    for (MethodForOperatorType c : MethodForOperatorType.values()) {
      if (c.value.equals(value)) {
        return c;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
