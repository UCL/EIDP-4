/*
 * Copyright 2015 David Guzman <d.guzman at ucl.ac.uk>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ucl.eidptest.data;

import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.data.SqlGenerator;
import uk.ac.ucl.eidp.data.SqlGeneratorFactory;
import uk.ac.ucl.eidptest.data.jaxb.DatasetType;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class DbXmlParsing {
    
    private final String datasettag = "dataset";
    private final String datasetid = "ROLES";

    public DbXmlParsing() {
    }

    @Test
    public void basicGetStatement() {
        String expected = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = 'usertest';";
        SqlGeneratorFactory sqlGeneratorFactory = new SqlGeneratorFactory();
        SqlGenerator sqlGenerator = sqlGeneratorFactory.newSqlGenerator();
        Map<String, String> m = new HashMap<>();
        m.put("login", "usertest");
        String generated = sqlGenerator.getSqlStatement("context-test.ROLES.getRolesForLogin", m);
        assertEquals(expected, generated);
    }
    
    @Test
    public void basicSetStatement() {
        String expected = "UPDATE UCLBRIT.T_USERS SET password = 'testpassword', modify_timestamp = 'null' WHERE id = 10;INSERT INTO UCLBRIT.T_USERS (password, modify_timestamp, id) VALUES ('testpassword', 'null', nextval('UCLBRIT.T_USERS_id_seq'));";
        SqlGeneratorFactory sqlGeneratorFactory = new SqlGeneratorFactory();
        SqlGenerator sqlGenerator = sqlGeneratorFactory.newSqlGenerator();
        Map<String, String> m = new HashMap<>();
        m.put("id", "10");
        m.put("password", "testpassword");
        String generated = sqlGenerator.getSqlStatement("context-test.USERS.setPassword", m);
        assertEquals(expected, generated);
    }
    
    @Test
    public void staxParse() throws XMLStreamException {  

        XMLStreamReader msr = mock(XMLStreamReader.class);
        when(msr.getLocalName()).thenReturn(datasettag);
        when(msr.getAttributeValue(0)).thenReturn(datasetid);

        XMLStreamReader xsr = generateXMLStreamReader();

        assertEquals(msr.getLocalName(), xsr.getLocalName());
        assertEquals(msr.getAttributeValue(0), xsr.getAttributeValue(0));

        xsr.close();
    }

    @Test
    public void fromStaxToJaxb() throws XMLStreamException, JAXBException {
        XMLStreamReader xsr = generateXMLStreamReader();

        JAXBContext jc = JAXBContext.newInstance(DatasetType.class);
        Unmarshaller unmarshaller = jc.createUnmarshaller();
        JAXBElement<DatasetType> jb = unmarshaller.unmarshal(xsr, DatasetType.class);
        DatasetType dataset = jb.getValue();

        System.out.println(dataset.getTable().getName());
        xsr.close();

    }

    private XMLStreamReader generateXMLStreamReader() throws XMLStreamException {
        XMLInputFactory xif = XMLInputFactory.newFactory();
        StreamSource xml = new StreamSource("src/test/resources/db.xml");
        XMLStreamReader xsr = xif.createXMLStreamReader(xml);

        while (xsr.hasNext()) {
            int event = xsr.next();
            if (XMLStreamConstants.START_ELEMENT == event
                    && datasettag.equals(xsr.getLocalName())
                    && datasetid.equals(xsr.getAttributeValue(0))) {
                break;
            }
        }
        return xsr;
    }
        
}
