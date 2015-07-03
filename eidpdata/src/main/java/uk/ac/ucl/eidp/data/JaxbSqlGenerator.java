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
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import uk.ac.ucl.eidp.data.jaxb.DatasetType;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class JaxbSqlGenerator implements SqlGenerator {

    private String contextCache = "";
    private final XMLInputFactory xif = XMLInputFactory.newFactory();
    private final String datasettag = "dataset";
    
    @Override
    public String getSqlStatement(String methodPath) {
        
        if (!methodPath.matches("[\\w-]*\\.[\\w-]*\\.[\\w-]*")) 
            throw new IllegalArgumentException("methodpath is invalid");
        
        DatasetType datasetType;
        
        if (!contextCache.equals(methodPath.split(".")[0])) {
            XMLStreamReader xsr;
            contextCache = methodPath.split(".")[0];
            try (InputStream is = getClass().getClassLoader().getResourceAsStream(contextCache + "/resources/db.xml")){               
                xsr = xif.createXMLStreamReader(is, "UTF-8");
            } catch (XMLStreamException | IOException ex) {
                throw new UnsupportedOperationException("Could not generate XMLStreamReader for given context", ex);
            }
            try {
                JAXBContext jc = JAXBContext.newInstance(DatasetType.class);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                JAXBElement<DatasetType> jb = unmarshaller.unmarshal(xsr, DatasetType.class);
                datasetType = jb.getValue();
            } catch (JAXBException ex) {
                throw new UnsupportedOperationException("JAXB could not unmarshall XMLStreamReader for given context", ex);
            }
        }
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
