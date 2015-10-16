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
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator.Parameter;
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
    private final String RS_SCROLL_TYPE = "resultset-scroll-type";
    private final String RS_CONCURRENCY_MODE = "resultset-concurrency-mode";
    private Connection connection;
    
    @Inject
    @StatementProducer
    private StatementGenerator statementGenerator;
    
    private void initialiseConnection() {
        if (!properties.containsKey(DS_JNDI_NAME)) {
            throw new IllegalStateException("Property " + DS_JNDI_NAME + " not found");
        }
        String datasourceJndiName = properties.getProperty(DS_JNDI_NAME);
        DataSource source;
        try {
            Context context = new InitialContext();
            source = (DataSource) context.lookup(datasourceJndiName);
            connection = source.getConnection();
        } catch (SQLException | NamingException ex) {
            throw new IllegalStateException("Could not get Connection from the specified DataSource", ex);
        }
    }

    @Override
    public List<Map<String, String>> processDbCall(String methodPath, Map<String, String> parameters) {
        if (null == connection) initialiseConnection();
        
        String sqlStatement = statementGenerator.getSqlStatement(methodPath);
        
        Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')"); 
        Matcher matcher = findParametersPattern.matcher(sqlStatement); 
        List<String> fields = new ArrayList<>();
        while (matcher.find()) { 
            fields.add(matcher.group().substring(1)); 
        }
        String jdbcStatement = sqlStatement.replaceAll(findParametersPattern.patter‌n(), "?");       
        
        Map<String, Parameter> p = statementGenerator.getParameterSettings(parameters.keySet(), methodPath);
        List<Map<String, String>> l = new ArrayList<>();
        try {
            int scroll_type = 1004; // defaults to ResultSet.TYPE_SCROLL_INSENSITIVE
            int concurrency_mode = 1007; // defaults to ResultSet.CONCUR_READ_ONLY
            if (properties.containsKey(RS_SCROLL_TYPE)) scroll_type = Integer.getInteger(properties.getProperty(RS_SCROLL_TYPE));
            if (properties.containsKey(RS_SCROLL_TYPE)) concurrency_mode = Integer.getInteger(properties.getProperty(RS_CONCURRENCY_MODE));
            PreparedStatement ps = connection.prepareStatement(jdbcStatement, scroll_type, concurrency_mode);
            fields.forEach((String f) -> {
                String value = parameters.get(f);
                String type = p.get(f).getType();
                Integer size = p.get(f).getSize();
//                if (!validateSize(value, size)) throw new RuntimeException();
            });
            ResultSet executeQuery = ps.executeQuery();
            
            
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not execute PreparedStatement " + sqlStatement, ex);
        }
        return l;
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setProperties(Properties p) {
        properties = p;
    }

    @Override
    public List<Map<String, String>> processDbTransaction(String methodPath, Map<String, String> parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean validateSize(String value, Integer size) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
