//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.31 at 12:47:33 PM BST 
//


package uk.ac.ucl.eidp.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for primary-key-generation-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="primary-key-generation-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="SERIAL-SEQ"/>
 *     &lt;enumeration value="SERIAL"/>
 *     &lt;enumeration value="AUTO-STRING"/>
 *     &lt;enumeration value="GUID-TO-FILL"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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

    PrimaryKeyGenerationType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PrimaryKeyGenerationType fromValue(String v) {
        for (PrimaryKeyGenerationType c: PrimaryKeyGenerationType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
