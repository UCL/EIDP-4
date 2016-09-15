package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * Java class for primary-key-generation-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;simpleType name="primary-key-generation-type"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SERIAL-SEQ"/&gt;
 *     &lt;enumeration value="SERIAL"/&gt;
 *     &lt;enumeration value="AUTO-STRING"/&gt;
 *     &lt;enumeration value="GUID-TO-FILL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "primary-key-generation-type")
@XmlEnum
public enum PrimaryKeyGenerationType {

  @XmlEnumValue("SERIAL-SEQ")
  SERIAL_SEQ("SERIAL-SEQ"),
  SERIAL("SERIAL"),
  @XmlEnumValue("AUTO-STRING")
  AUTO_STRING("AUTO-STRING"),
  @XmlEnumValue("GUID-TO-FILL")
  GUID_TO_FILL("GUID-TO-FILL");
  private final String value;

  PrimaryKeyGenerationType(String value) {
    this.value = value;
  }

  public String value() {
    return value;
  }

  /**
   * Obtains a {@link PrimaryKeyGenerationType} from its corresponding name.
   * @param value The name of the {@link PrimaryKeyGenerationType}.
   * @return The corresponding {@link PrimaryKeyGenerationType}.
   */
  public static PrimaryKeyGenerationType fromValue(String value) {
    for (PrimaryKeyGenerationType c : PrimaryKeyGenerationType.values()) {
      if (c.value.equals(value)) {
        return c;
      }
    }
    throw new IllegalArgumentException(value);
  }

}
