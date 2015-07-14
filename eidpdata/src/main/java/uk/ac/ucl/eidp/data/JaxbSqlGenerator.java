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
import uk.ac.ucl.eidp.data.jaxb.DatasetType;
import uk.ac.ucl.eidp.data.jaxb.JaxbSqlAnsi;
import uk.ac.ucl.eidp.data.jaxb.JaxbSqlStatement;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class JaxbSqlGenerator implements SqlGenerator {

    private final XMLInputFactory xif = XMLInputFactory.newFactory();
    private final String DATASET_TAG = "dataset";
    private String SQL_DIALECT = "";
    
    @Override
    public String getSqlStatement(String methodPath) {
        
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodpath is invalid");
        
        DatasetType datasetType = null;
        XMLStreamReader xsr = null;
        
        if (null == datasetType || !methodPath.split("\\.")[0].equals(datasetType.getId())) {
            
            try (InputStream is = getClass().getClassLoader().getResourceAsStream("META-INF/" + methodPath.split("\\.")[0] + "/resources/db.xml")) {
                
                xsr = xif.createXMLStreamReader(is, "UTF-8");
                while (xsr.hasNext()) {
                    int event = xsr.next();
                    if (XMLStreamConstants.START_ELEMENT == event
                            && DATASET_TAG.equals(xsr.getLocalName())
                            && methodPath.split("\\.")[1].equals(xsr.getAttributeValue(0))) {
                        break;
                    }
                }
            } catch (XMLStreamException | IOException ex) {
                throw new UnsupportedOperationException("Could not generate XMLStreamReader for given context or dataset", ex);
            } 

        }

        try {
            JAXBContext jc = JAXBContext.newInstance(DatasetType.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            JAXBElement<DatasetType> jb = unmarshaller.unmarshal(xsr, DatasetType.class);
            datasetType = jb.getValue();
        } catch (JAXBException ex) {
            throw new UnsupportedOperationException("JAXB could not unmarshall XMLStreamReader for given context", ex);
        }
        
        if (null != xsr) try {
            xsr.close();
        } catch (XMLStreamException ex) {
            Logger.getLogger(JaxbSqlGenerator.class.getName()).log(Level.SEVERE, "Cannot close XMLStreamReader", ex);
        }
        
        JaxbSqlStatement jaxbSqlStatement;
        try {
            jaxbSqlStatement = (JaxbSqlStatement) Class.forName(SQL_DIALECT).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(JaxbSqlGenerator.class.getName()).log(Level.SEVERE, "Cannot create instance of " + SQL_DIALECT, ex);
            jaxbSqlStatement = new JaxbSqlAnsi();
        }
        
        return jaxbSqlStatement.buildStatement(datasetType, methodPath.split("\\.")[2]);

    }

    @Override
    public void setSqlDialect(String SQL_DIALECT) {
        this.SQL_DIALECT = SQL_DIALECT;
    }
}
