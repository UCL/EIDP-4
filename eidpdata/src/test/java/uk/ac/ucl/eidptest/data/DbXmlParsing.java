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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import uk.ac.ucl.eidp.data.StatementGenerator;
import uk.ac.ucl.eidp.data.StatementGeneratorFactory;
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
        String expected = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = ?;";
        String generated = buildStatement("context-test.ROLES.getRolesForLogin");
        assertEquals(generated, expected);
    }
    
    @Test
    public void basicSetStatement() {
        String expected = "UPDATE UCLBRIT.T_USERS SET password = ?, modify_timestamp = ? WHERE id = ?;INSERT INTO UCLBRIT.T_USERS (password, modify_timestamp, id) VALUES (?, ?, nextval('UCLBRIT.T_USERS_id_seq'));";
        String generated = buildStatement("context-test.USERS.setPassword");
        assertEquals(generated, expected);
    }
    
    @Test
    public void basicDelStatement() {
        String expected = "DELETE FROM UCLBRIT.T_ROLES WHERE login = ?;";
        String generated = buildStatement("context-test.ROLES.removeRolesForLogin");
        assertEquals(generated, expected);
    }
    
    @Test
    public void whereClause() {
        String expected = "SELECT id, password, login_err_number, login_err_timestamp, create_timestamp, modify_timestamp FROM UCLBRIT.T_USERS WHERE login = ? AND center_id = ?;";
        String generated = buildStatement("context-test.USERS.getUserDataForLogin");
        assertEquals(generated, expected);
    }
    
    @Test
    public void sortingGetStatement() {
        String expected = "SELECT id, center_id, login, create_timestamp, modify_timestamp, forename, surname FROM UCLBRIT.T_USERS ORDER BY login ASC, create_timestamp DESC;";
        String generated = buildStatement("context-test.USERS.getAllUserData");
        assertEquals(generated, expected);
    }
    
    
    @Test
    public void notequalGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND permission != ?;";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndPermissionNotEqual");
        assertEquals(generated, expected);
    }
    
    @Test
    public void lessthanGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND id < ?;";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndIdLessThan");
        assertEquals(generated, expected);
    }
    
    @Test
    public void lessequalthanGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND id <= ?;";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndIdLessEqualThan");
        assertEquals(generated, expected);
    }
    
    @Test
    public void greaterthanGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND id > ?;";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndIdGreaterThan");
        assertEquals(generated, expected);
    }

    @Test
    public void greaterequalthanGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND id >= ?;";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndIdGreaterEqualThan");
        assertEquals(generated, expected);
    }
    
    @Test
    public void inGetStatement() {
        String expected = "SELECT id, center, status FROM UCLBRIT.T_CENTER_ROLES WHERE login = ? AND id in (?);";
        String generated = buildStatement("context-test.CENTER_ROLES.getCentersForUserAndIdIn");
        assertEquals(generated, expected);
    }
    
    @Test
    public void offsetLimitStatement() {
        String expected = "SELECT id, login, center, status, permission FROM UCLBRIT.T_CENTER_ROLES ORDER BY login ASC OFFSET 5 ROWS FETCH FIRST 10 ROWS;";
        String generated = buildStatement("context-test.CENTER_ROLES.getAllCenterRolesForAllLoginsOffsetLimit");
        assertEquals(generated, expected);
    }
    
    @Test
    public void translateParameters() {
        Map<String, String> expected = new HashMap<>();
        expected.put("participantIdCol", "o784qhfcadbcadfbha");
        expected.put("applicationCol", "test-context");
        expected.put("centreCol", "10000");
        Map<String, String> m = new HashMap<>();
        m.put("participant_id", "o784qhfcadbcadfbha");
        m.put("application", "test-context");
        m.put("centre", "10000");
        StatementGeneratorFactory sqlGeneratorFactory = new StatementGeneratorFactory();
        StatementGenerator sqlGenerator = sqlGeneratorFactory.newSqlGenerator();
        Map<String, String> generated = sqlGenerator.translateParameters(m, "context-test.PARTICIPANT_LIST");
        assertEquals(generated, expected);
    }
    
    @Test
    public void getMethodRoles() {
        String[] a = {"brit","admin","test"};
        List<String> expected = new ArrayList<>(Arrays.asList(a));
        StatementGeneratorFactory sqlGeneratorFactory = new StatementGeneratorFactory();
        StatementGenerator sqlGenerator = sqlGeneratorFactory.newSqlGenerator();
        List<String> generated = sqlGenerator.getMethodRoles("context-test.PARTICIPANT_LIST.setParticipantForID");
        assertEquals(generated, expected);
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
        assertEquals(dataset.getTable().getName(),"UCLBRIT.T_ROLES");
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
    
    private String buildStatement(String method) {
        StatementGeneratorFactory sqlGeneratorFactory = new StatementGeneratorFactory();
        StatementGenerator sqlGenerator = sqlGeneratorFactory.newSqlGenerator();
        return sqlGenerator.getSqlStatement(method);
    }
        
}
