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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class JaxbSqlAnsi extends JaxbSqlStatement {

    @Override
    protected String buildGetStatement() {
        StringBuilder stm = new StringBuilder("SELECT ");

        List<String> queryFields = new ArrayList<>();
        methodType.getFields().getField().forEach((String f) -> {
            queryFields.add(translateId(f));
        });
        
        stm.append(String.join(", ", queryFields));
        stm.append(" FROM ").append(tableType.getName());
        
        if (!methodType.getFor().isEmpty()) stm.append(" WHERE ");
        methodType.getFor().forEach((MethodForType m) -> {
            stm.append(translateId(m.getField()));
            switch (m.getOperator()) {
                case EQUAL : stm.append(Operator.EQUAL.getOperator());
            }
            if (isQuotation(m.getField())) {
                stm.append("'").append(parametermap.get(m.getField())).append("'");
            } else {
                stm.append(parametermap.get(m.getField()));
            }
        });
        
        return stm.append(";").toString();
    }

    @Override
    protected String buildSetStatement() {
        StringBuilder stm = new StringBuilder("UPDATE ");
        stm.append(tableType.getName()).append(" SET ");
        
        methodType.getFields().getField().forEach((String f) -> {
            if (!stm.substring(stm.length() - 4).equals("SET ")) stm.append(", ");
            stm.append(f);
            stm.append(" = ");
            if (isQuotation(f)) {
                stm.append("'").append(parametermap.get(f)).append("'");
            } else {
                stm.append(parametermap.get(f));
            }
        });
        
        if (!methodType.getFor().isEmpty()) stm.append(" WHERE ");
        methodType.getFor().forEach((MethodForType m) -> {
            stm.append(translateId(m.getField()));
            switch (m.getOperator()) {
                case EQUAL : stm.append(Operator.EQUAL.getOperator());
            }
            if (isQuotation(m.getField())) {
                stm.append("'").append(parametermap.get(m.getField())).append("'");
            } else {
                stm.append(parametermap.get(m.getField()));
            }
        });
        
        stm.append(";INSERT INTO ");
        stm.append(tableType.getName()).append(" (");
        methodType.getFields().getField().forEach((String f) -> {
            stm.append(f);
            stm.append(", ");
        });
        stm.append(tableType.getPrimaryKey());
        stm.append(") VALUES (");
        methodType.getFields().getField().forEach((String f) -> {
            if (isQuotation(f)) {
                stm.append("'").append(parametermap.get(f)).append("'");
            } else {
                stm.append(parametermap.get(f));
            }
            stm.append(", ");
        });
        stm.append("nextval('").append(generateId()).append("'))");
        return stm.append(";").toString();
    }
    
    @Override
    protected String buildDelStatement() {
        StringBuilder stm = new StringBuilder("DELETE FROM ");
        stm.append(tableType.getName());
        if (!methodType.getFor().isEmpty()) stm.append(" WHERE ");
        methodType.getFor().forEach((MethodForType m) -> {
            stm.append(translateId(m.getField()));
            switch (m.getOperator()) {
                case EQUAL : stm.append(Operator.EQUAL.getOperator());
            }
            if (isQuotation(m.getField())) {
                stm.append("'").append(parametermap.get(m.getField())).append("'");
            } else {
                stm.append(parametermap.get(m.getField()));
            }
        });
        return stm.append(";").toString();
    }
    
    protected String generateId() {
        String sequenceName = tableType.getName() + "_id_seq";
        return sequenceName;
    }

    protected enum Operator {
        
        EQUAL(" = ");
        
        private final String operator;
        
        Operator(String operator) {
            this.operator = operator;
        }
        
        public String getOperator() {
            return operator;
        }
    }
}
