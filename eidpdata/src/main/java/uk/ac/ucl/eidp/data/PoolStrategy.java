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

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;
import uk.ac.ucl.eidp.data.jaxb.StatementProducer;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Stateless
@NodeQualifier(NodeType.POOL)
public class PoolStrategy implements DBMappingStrategy {
    
    private Properties properties;
    private final String DS_JNDI_NAME = "datasource-jndi-name";
    private Connection connection;
    
    @Resource 
    private SessionContext ejbContext;
    
    @Inject
    @StatementProducer
    private StatementGenerator statementGenerator;
    
    private void initialiseConnection() {
        if (!properties.containsKey(DS_JNDI_NAME)) throw new IllegalStateException("Property " + DS_JNDI_NAME + " not found");
        String datasourceJndiName = properties.getProperty(DS_JNDI_NAME);
        System.out.println("JNDIIIIIIIIIIIIIIIIIIIIIIIII" + datasourceJndiName);
        DataSource source = (DataSource) ejbContext.lookup(datasourceJndiName);
        try {
            connection = source.getConnection();
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not get Connection from the specified DataSource", ex);
        }
    }

    @Override
    public List<Map<String, String>> processDbCall(String methodPath, Map<String, String> parameters) {
        if (null == connection) initialiseConnection();
        
        String sqlStatement = statementGenerator.getSqlStatement(methodPath);
        List<Map<String, String>> l = new ArrayList<>();
        return l;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setProperties(Properties p) {
        properties = p;
    }
    
}
