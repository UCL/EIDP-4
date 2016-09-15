package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for table-field-type-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="table-field-type-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="String"/&gt;
 *     &lt;enumeration value="Integer"/&gt;
 *     &lt;enumeration value="Float"/&gt;
 *     &lt;enumeration value="Date"/&gt;
 *     &lt;enumeration value="Time"/&gt;
 *     &lt;enumeration value="Timestamp"/&gt;
 *     &lt;enumeration value="Double"/&gt;
 *     &lt;enumeration value="Long"/&gt;
 *     &lt;enumeration value="Boolean"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "table-field-type-type")
@XmlEnum
public enum TableFieldTypeType {

  @XmlEnumValue("String")
  STRING("String"),
  @XmlEnumValue("Integer")
  INTEGER("Integer"),
  @XmlEnumValue("Float")
  FLOAT("Float"),
  @XmlEnumValue("Date")
  DATE("Date"),
  @XmlEnumValue("Time")
  TIME("Time"),
  @XmlEnumValue("Timestamp")
  TIMESTAMP("Timestamp"),
  @XmlEnumValue("Double")
  DOUBLE("Double"),
  @XmlEnumValue("Long")
  LONG("Long"),
  @XmlEnumValue("Boolean")
  BOOLEAN("Boolean");
  private final String value;

  TableFieldTypeType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  /**
   * Obtains a {@link TableFieldTypeType} from its corresponding name.
   * @param value The name of the {@link TableFieldTypeType}.
   * @return The corresponding {@link TableFieldTypeType}.
   */
  public static TableFieldTypeType fromValue(String value) {
    for (TableFieldTypeType c : TableFieldTypeType.values()) {
      if (c.value.equals(value)) {
        return c;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
