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
 * <p>Java class for method-for-operator-type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="method-for-operator-type">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="equal"/>
 *     &lt;enumeration value="notequal"/>
 *     &lt;enumeration value="like"/>
 *     &lt;enumeration value="isnull"/>
 *     &lt;enumeration value="isnotnull"/>
 *     &lt;enumeration value="gt"/>
 *     &lt;enumeration value="get"/>
 *     &lt;enumeration value="lt"/>
 *     &lt;enumeration value="let"/>
 *     &lt;enumeration value="similarto"/>
 *     &lt;enumeration value="fuzzy"/>
 *     &lt;enumeration value="in"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
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

    MethodForOperatorType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MethodForOperatorType fromValue(String v) {
        for (MethodForOperatorType c: MethodForOperatorType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
