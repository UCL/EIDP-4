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

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public abstract class JaxbSqlStatement {
    
    protected TableType tableType;
    protected MethodType methodType;
    
    public String buildStatement(DatasetType datasetType, String method) {
        this.tableType = datasetType.getTable();
        methodType = datasetType.getMethod().stream().filter(m -> (m.getId() == null ? method == null : m.getId().equals(method))
        ).findFirst().get();
        
        switch (methodType.getType()) {
            case GET:
                return buildGetStatement();
            case SET:
                return buildSetStatement();
            case REMOVE:
                return buildDelStatement();
        }
        throw new UnsupportedOperationException("Method not found." + methodType.getId());
    }
    
    protected abstract String buildGetStatement();
    
    protected abstract String buildSetStatement();
    
    protected abstract String buildDelStatement();
    
    protected String translateId(String id) {
        String tid;
        tid = tableType.getField().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .get()
                .getName();
        return tid;
    }
    
    protected boolean isQuotation(String id) {
        return tableType.getField().stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .get()
                .getType()
                .value().matches("String|Date|Timestamp");
    }
    
}
