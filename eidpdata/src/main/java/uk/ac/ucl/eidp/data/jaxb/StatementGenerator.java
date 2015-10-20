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
package uk.ac.ucl.eidp.data.jaxb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class StatementGenerator {

    private final XMLInputFactory xif = XMLInputFactory.newFactory();
    private final String DATASET_TAG = "dataset";
    private String SQL_DIALECT = "";
    
    public String getSqlStatement(String methodPath) {
        
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodpath is invalid");
        
        DatasetType datasetType = getDatasetTypeObject(methodPath);
        
        JaxbSqlStatement jaxbSqlStatement;
        try {
            jaxbSqlStatement = (JaxbSqlStatement) Class.forName(SQL_DIALECT).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(StatementGenerator.class.getName()).log(Level.SEVERE, "Cannot create instance of " + SQL_DIALECT, ex);
            jaxbSqlStatement = new JaxbSqlAnsi();
        }
        
        return jaxbSqlStatement.buildStatement(datasetType, methodPath.split("\\.")[2]);

    }

    public void setSqlDialect(String SQL_DIALECT) {
        this.SQL_DIALECT = SQL_DIALECT;
    }

    public Map<String, Parameter> getParameterSettings(Set<String> keyset, String datasetPath) {
        
        if (!datasetPath.matches("[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("datasetPath is invalid");
        
        Map<String, Parameter> m = new HashMap<>();
        DatasetType datasetType = getDatasetTypeObject(datasetPath);

        keyset.forEach((String k) -> {
            TableFieldType field = datasetType.getTable().getField().stream()
                    .filter(t -> t.getId().equals(k))
                    .findFirst()
                    .get();
            Parameter p = new Parameter();
            p.setType(field.getType().value());
            p.setSize(field.getSize());
            m.put(k, p);
        });
                     
        return m;
    }
    
    public TableFieldType getTableField(String fieldId, String datasetPath) {
        if (!datasetPath.matches("[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("datasetPath is invalid");
        DatasetType datasetType = getDatasetTypeObject(datasetPath);
        return datasetType.getTable().getField().stream()
                .filter(t -> t.getId().equals(fieldId))
                .findFirst()
                .get();
    }

    public List<String> getMethodRoles(String methodPath) {
        
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodPath is invalid");
        
        DatasetType datasetType = getDatasetTypeObject(methodPath);
        String method = methodPath.split("\\.")[2];
        MethodType methodType = getMethodType(datasetType, method);
        return methodType.getRoleName();

    }
    
    public List<String> getMethodFields(String methodPath) {
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodPath is invalid");
        
        DatasetType datasetType = getDatasetTypeObject(methodPath);
        String method = methodPath.split("\\.")[2];
        MethodType methodType = getMethodType(datasetType, method);
        return methodType.getFields().getField();
    }
    
    private DatasetType getDatasetTypeObject(String path) {
        
        DatasetType datasetType = null;
        XMLStreamReader xsr = null;
        
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/eidp/" + path.split("\\.")[0] + "/resources/db.xml")) {

            xsr = xif.createXMLStreamReader(is, "UTF-8");
            while (xsr.hasNext()) {
                int event = xsr.next();
                if (XMLStreamConstants.START_ELEMENT == event
                        && DATASET_TAG.equals(xsr.getLocalName())
                        && path.split("\\.")[1].equals(xsr.getAttributeValue(0))) {
                    break;
                }
            }

            JAXBContext jc = JAXBContext.newInstance("uk.ac.ucl.eidp.data.jaxb", ObjectFactory.class.getClassLoader());
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<DatasetType> jb = unmarshaller.unmarshal(xsr, DatasetType.class);
            datasetType = jb.getValue();

        } catch (XMLStreamException | IOException ex) {
            throw new UnsupportedOperationException("Could not generate XMLStreamReader for given context or dataset" , ex);
        } catch (JAXBException ex) {
            throw new UnsupportedOperationException("JAXB could not unmarshall XMLStreamReader for given context", ex);
        }

        try {
            xsr.close();
        } catch (XMLStreamException ex) {
            Logger.getLogger(StatementGenerator.class.getName()).log(Level.SEVERE, "Cannot close XMLStreamReader", ex);
        }
        
        return datasetType;
    }
    
    private MethodType getMethodType(DatasetType datasetType, String method) {
        return datasetType.getMethod().stream().filter(
                m -> (m.getId() == null ? method == null : m.getId().equals(method))
        ).findFirst().get();
    }
    
    public class Parameter {
           
        private String type;
        private Integer size;
//        private String format;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }
        
//        public String getFormat() {
//            return format;
//        }
//        
//        public void setFormat(String format) {
//            this.format = format;
//        }
        
        public Integer getSqlType() {
            TableFieldTypeType fieldType = TableFieldTypeType.fromValue(type);
            switch (fieldType) {
                case STRING : 
                    if (getSize() > 254) return Types.LONGVARCHAR;
                    return Types.VARCHAR;
                case INTEGER :
                    return Types.INTEGER;
                case FLOAT :
                    return Types.FLOAT;
                case DATE :
                    return Types.DATE;
                default : return null;
            }
        }

    }
}
