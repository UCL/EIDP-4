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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import uk.ac.ucl.eidp.data.jaxb.StatementProducer;
import uk.ac.ucl.eidp.data.jaxb.StatementGenerator;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class StatementGeneratorProducer {
    
    private final String SQLGENERATOR_PROP = "uk.ac.ucl.eidp.data.StatementGenerator";
    private String SQLGENERATOR_CLASS = "uk.ac.ucl.eidp.data.jaxb.JaxbStatementGenerator";
    private final String PROPERTIESFILE = "META-INF/eidp.properties";
    private final String SQL_DIALECT_PROP = "uk.ac.ucl.eidp.data.jaxb.JaxbSqlStatement";
    private String SQL_DIALECT = "uk.ac.ucl.eidp.data.jaxb.JaxbSqlAnsi";
    
    @Inject
    public StatementGeneratorProducer() {
        
        Properties p = new Properties();
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTIESFILE)) {
            p.load(is);
        } catch (IOException ex) {
            Logger.getLogger(StatementGeneratorProducer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (p.containsKey(SQLGENERATOR_PROP) && !SQLGENERATOR_CLASS.equals(p.getProperty(SQLGENERATOR_PROP))) {
            SQLGENERATOR_CLASS = p.getProperty(SQLGENERATOR_PROP);
        }
        if (p.containsKey(SQL_DIALECT_PROP))
            SQL_DIALECT = p.getProperty(SQL_DIALECT_PROP);
        
    }
    
    @Produces
    @StatementProducer
    public StatementGenerator newSqlGenerator() {
        StatementGenerator sqlGenerator = new StatementGenerator();
        sqlGenerator.setSqlDialect(SQL_DIALECT);
        return sqlGenerator;
    }
    
}
