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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.sql.DataSource;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Stateless
@LocalBean
public class PoolStrategy implements DBMappingStrategy {
    
    private final Properties properties;
    private final String DS_JNDI_NAME = "datasource-jndi-name";
    @Resource SessionContext ejbContext;
    private Connection connection;

    protected PoolStrategy() {
        throw new IllegalStateException();
    }
    
    @Inject
    public PoolStrategy(Properties properties) {
        this.properties = properties;
    }
    
    @PostConstruct
    public void initialise() {
        if (!properties.containsKey(DS_JNDI_NAME)) throw new IllegalStateException("Property " + DS_JNDI_NAME + " not found");
        String datasourceJndiName = properties.getProperty(DS_JNDI_NAME);
        DataSource source = (DataSource) ejbContext.lookup(datasourceJndiName);
        try {
            connection = source.getConnection();
        } catch (SQLException ex) {
            throw new IllegalStateException("Could not get Connection from the specified DataSource", ex);
        }
    }

    @Override
    public List<Map<String, String>> processDbCall(String methodPath, Map<String, String> parameters) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadProperties(Properties p) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}