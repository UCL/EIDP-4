//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.05.12 at 01:37:52 PM BST 
//


package uk.ac.ucl.eidptest.data.jaxb;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for method-for-type-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="method-for-type-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="and"/>
 *     &lt;enumeration value="or"/>
 *     &lt;enumeration value="not"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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

    MethodForTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MethodForTypeType fromValue(String v) {
        for (MethodForTypeType c: MethodForTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
