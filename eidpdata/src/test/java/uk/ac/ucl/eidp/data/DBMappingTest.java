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
package uk.ac.ucl.eidp.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class DBMappingTest {
    
    public DBMappingTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of loadMapping method, of class DBMapping.
     */
    @Test
    public void testLoadMapping() {
        Properties mappingProperties = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/eidp/mapping.properties")) {
            mappingProperties.load(is);
        } catch (IOException ex) {
            throw new UnsupportedOperationException("Cannot load mapping.properties ", ex);
        }
        boolean expected = false;
        boolean generated = mappingProperties.isEmpty();
        assertEquals(generated, expected);
    }

    /**
     * Test of dbAction method, of class DBMapping.
     */
    @Test
    public void testDbAction() {
        StrategyCache strategyCache = mock(StrategyCache.class);       
        when(strategyCache.getDbMappingStrategyForId("databaseId")).thenReturn(new JdbcStrategy());
        DBMapping dbMapping = new DBMapping();
        dbMapping.loadMapping();
        dbMapping.dbAction("context-test.USERS.getUserDataForLogin", null);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
