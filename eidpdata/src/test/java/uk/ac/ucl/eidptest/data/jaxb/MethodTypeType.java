//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.31 at 12:48:06 PM BST 
//


package uk.ac.ucl.eidptest.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for method-type-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="method-type-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="get"/>
 *     &lt;enumeration value="set"/>
 *     &lt;enumeration value="remove"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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

    MethodTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MethodTypeType fromValue(String v) {
        for (MethodTypeType c: MethodTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
