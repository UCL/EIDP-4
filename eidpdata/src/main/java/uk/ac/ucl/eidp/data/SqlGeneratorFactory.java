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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class SqlGeneratorFactory {
    
    private final String SQLGENERATOR_PROP = "uk.ac.ucl.eidp.data.SqlGenerator";
    private String SQLGENERATOR_CLASS = "uk.ac.ucl.eidp.data.JaxbSqlGenerator";
    private final String PROPERTIESFILE = "eidp.properties";

    public SqlGeneratorFactory() {
        
        Properties p = new Properties();
        try {
            p.load(getClass().getResourceAsStream(PROPERTIESFILE));
        } catch (IOException ex) {
            Logger.getLogger(SqlGeneratorFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (p.containsKey(SQLGENERATOR_PROP) && !SQLGENERATOR_CLASS.equals(p.getProperty(SQLGENERATOR_PROP))) {
            SQLGENERATOR_CLASS = p.getProperty(SQLGENERATOR_PROP);
        }
        
    }
    
    public SqlGenerator newSQLGenerator() {
        try {
            return (SqlGenerator) Class.forName(SQLGENERATOR_CLASS).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(SqlGeneratorFactory.class.getName()).log(Level.SEVERE, null, ex);
            return new JaxbSqlGenerator();
        }
    }
    
}
