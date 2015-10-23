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

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.data.DBMapping;
import uk.ac.ucl.eidp.data.DBMappingStrategy;
import uk.ac.ucl.eidp.data.EidpStrategy;
import uk.ac.ucl.eidp.data.JdbcStrategy;
import uk.ac.ucl.eidp.data.NodeQualifier;
import uk.ac.ucl.eidp.data.NodeType;
import uk.ac.ucl.eidp.data.PoolStrategy;
import uk.ac.ucl.eidp.data.StatementGeneratorProducer;
import uk.ac.ucl.eidp.data.StrategyResolver;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;
import uk.ac.ucl.eidp.data.jaxb.StatementProducer;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class DBMappingIT extends Arquillian {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(DBMapping.class, StrategyResolver.class, DBMappingStrategy.class, NodeQualifier.class,
                    PoolStrategy.class, JdbcStrategy.class, EidpStrategy.class, NodeType.class, 
                    StatementGenerator.class, StatementGeneratorProducer.class)
            .addPackage("uk.ac.ucl.eidp.data.jaxb")
            .addAsResource("META-INF/eidp/mapping.properties")
            .addAsResource("META-INF/eidp/gateway.properties")
            .addAsResource("META-INF/eidp.properties")
            .addAsResource("META-INF/eidp/context-test/resources/db.xml")
            .addAsResource("META-INF/beans.xml");
        return jar;
    }
    
    @EJB
    DBMapping dbMapping;
    
    @Inject
    @StatementProducer
    StatementGenerator statementGenerator;
      
    private Connection connection;
    
    @Test
    public void testStatementGenerator() throws Exception {
        String expected = "SELECT id, password, login_err_number, login_err_timestamp, create_timestamp, modify_timestamp FROM UCLBRIT.T_USERS WHERE login = :login AND center_id = :center_id";
        String generated = statementGenerator.getSqlStatement("context-test.USERS.getUserDataForLogin");
        assertEquals(generated, expected);
    }
    
    @Test
    public void testDatabaseConnection() throws Exception {
        Properties p = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/eidp/gateway.properties");
        p.load(is);

        String datasourceJndi = p.getProperty("datasource-jndi-name");

        Context context = new InitialContext();
        DataSource source = (DataSource) context.lookup(datasourceJndi);
        try {
            connection = source.getConnection();
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not get Connection from the specified DataSource", ex);
        }
        String sqlStatement = statementGenerator.getSqlStatement("context-test.USERS.getUserDataForLogin");
        int expected;
        int generated;
        try (PreparedStatement ps = connection.prepareStatement(sqlStatement, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            ps.setString(1, "testuser");
            ps.setInt(2, 1000);
            try (ResultSet rs = ps.executeQuery()) {
                rs.last();
                expected = 1;
                generated = rs.getRow();
            }
        }
        assertEquals(generated, expected);       
    }

    @Test
    public void testDbaGetAction() {
        Map<String, String> m = new HashMap<>();
        m.put("login", "testuser");
        m.put("center_id", "1000");
        List<Map<String, String>> l = dbMapping.dbAction("context-test.USERS.getUserDataForLogin", m);
        String expected = "password=password, login_err_number=1, create_timestamp=0987654321, login_err_timestamp=1234567890, modify_timestamp=0123456789, id=10, ";
        String generated = "";
        generated = l.get(0).entrySet().stream().map((entry) -> entry.getKey() + "=" + entry.getValue() + ", ").reduce(generated, String::concat);
        assertEquals(generated, expected); 
    }
    
    @Test
    public void testDbaSetUpdateAction() {
        Map<String, String> m = new HashMap<>();
        m.put("login", "testuser");
        m.put("login_err_number", "0");
        m.put("login_err_timestamp", "1445350688");
        List<Map<String, String>> l = dbMapping.dbAction("context-test.USERS.setLoginError", m);
        String expected = "updateCount=1";
        String generated = "";
        generated = l.get(0).entrySet().stream().map((entry) -> entry.getKey() + "=" + entry.getValue()).reduce(generated, String::concat);
        assertEquals(generated, expected);
    }
    
    @Test
    public void testDbaSetInsertAction() {
        Map<String, String> m = new HashMap<>();
        m.put("id", "0");
        m.put("login", "testuser2");
        m.put("password", "password2");
        m.put("create_timestamp", "1445350688");
        m.put("modify_timestamp", "1445350688");
        m.put("center_id", "1000");
        m.put("surname", "Surname");
        m.put("forename", "Forename");
        List<Map<String, String>> l = dbMapping.dbAction("context-test.USERS.setNewUserDataForID", m);
        String expected = "updateCount=1";
        String generated = "";
        generated = l.get(0).entrySet().stream().map((entry) -> entry.getKey() + "=" + entry.getValue()).reduce(generated, String::concat);
        assertEquals(generated, expected);
    }
}
