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
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Stateless
public class StrategyResolver {

    private final Map<String, Properties> databaseNodes = new HashMap<>();
    private final String NODETYPE_PROP = "uk.ac.ucl.eidp.data.NodeType";
    private final String CUSTOM_STRATEGY_JNDI = "uk.ac.ucl.eidp.data.DBMappingStrategy";

    @Inject
    @NodeQualifier(NodeType.POOL)
    private DBMappingStrategy poolStrategy;
    
    @Inject
    @NodeQualifier(NodeType.JDBC)
    private DBMappingStrategy jdbcStrategy;
    
    @Inject
    @NodeQualifier(NodeType.EIDP)
    private DBMappingStrategy eidpStrategy;

    public DBMappingStrategy getDbMappingStrategyForId(String databaseNodeId) {

        Properties p;
        if (!databaseNodes.containsKey(databaseNodeId)) {
            p = new Properties();
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/eidp/" + databaseNodeId + ".properties")) {
                p.load(is);
            } catch (IOException ex) {
                Logger.getLogger(DBMapping.class.getName()).log(Level.SEVERE, null, ex);
            }
            databaseNodes.put(databaseNodeId, p);
        } else {
            p = databaseNodes.get(databaseNodeId);
        }
        DBMappingStrategy dbMappingStrategy = null;
        switch (NodeType.valueOf(p.getProperty(NODETYPE_PROP))) {
            case JDBC:
                dbMappingStrategy = jdbcStrategy;
                break;
            case POOL:
                dbMappingStrategy = poolStrategy;
                break;
            case EIDP:
                dbMappingStrategy = eidpStrategy;
                break;
            case CUSTOM: {
                try {
                    Context ctx = new InitialContext();
                    dbMappingStrategy = (DBMappingStrategy) ctx.lookup(p.getProperty(CUSTOM_STRATEGY_JNDI));
                } catch (NamingException ex) {
                    throw new UnsupportedOperationException(CUSTOM_STRATEGY_JNDI + " could not be loaded. ", ex);
                }
            }
            break;
            default:
                throw new AssertionError(NodeType.valueOf(p.getProperty(NODETYPE_PROP)).name());
        }
        if (null == dbMappingStrategy) {
            throw new UnsupportedOperationException("Instance DBMappingStrategy could not be created");
        }
        dbMappingStrategy.setProperties(p);
        return dbMappingStrategy;

    }

}
