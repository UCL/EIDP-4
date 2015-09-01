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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.inject.Inject;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Stateless
@LocalBean
public class DBMapping {
    
    private final String MAPPING_PROPS = "META-INF/eidp/mapping.properties";
    private final Properties mappingProperties = new Properties();
    
    @Inject
    private StrategyResolver strategyResolver;
    
    @PostConstruct
    public void loadMapping() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(MAPPING_PROPS)) {
            mappingProperties.load(is);
        } catch (IOException ex) {
            throw new UnsupportedOperationException("Cannot load mapping.properties " , ex);
        }
    }

    public List<Map<String, String>> dbAction(String methodPath, Map<String, String> parameters) {
        
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodPath is invalid");
        String databaseNodeId = mappingProperties.getProperty(methodPath);
        
        DBMappingStrategy dbMappingStrategy = strategyResolver.getDbMappingStrategyForId(databaseNodeId);
            
        return dbMappingStrategy.processDbCall(methodPath, parameters);
    }
    
}