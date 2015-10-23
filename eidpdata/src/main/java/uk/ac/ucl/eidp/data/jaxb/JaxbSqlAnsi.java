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
        
        if (!methodType.getFor().isEmpty()) stm.append(generateWhereClause());
             
        if (!methodType.getOrder().isEmpty()) {
            stm.append(" ORDER BY ");
            methodType.getOrder().forEach((MethodOrderType o) -> {
                if (!stm.substring(stm.length() - 9).equals("ORDER BY ")) stm.append(", ");
                stm.append(translateId(o.getField()));
                stm.append(" ");
                stm.append(o.getSorting().value());
            });
        }
        
        stm.append(generateOffsetLimit());
        
        return stm.toString();
    }

    @Override
    protected String buildSetStatement() {
        StringBuilder stm = new StringBuilder("UPDATE ");
        stm.append(tableType.getName()).append(" SET ");
        
        methodType.getFields().getField().forEach((String f) -> {
            if (!stm.substring(stm.length() - 4).equals("SET ")) stm.append(", ");
            stm.append(translateId(f));
            stm.append(" = :");
            stm.append(f);
        });
        
        if (!methodType.getFor().isEmpty()) stm.append(generateWhereClause());
      
        stm.append(";");
        stm.append("INSERT INTO ");
        stm.append(tableType.getName()).append(" (");
        methodType.getFields().getField().forEach((String f) -> {
            stm.append(translateId(f));
            stm.append(", ");
        });
        stm.append(tableType.getPrimaryKey());
        stm.append(") VALUES (");
        methodType.getFields().getField().forEach((String f) -> {
            stm.append(":");
            stm.append(f);
            stm.append(", ");
        });
        stm.append(generateId()).append(")");
        return stm.toString();
    }
    
    @Override
    protected String buildDelStatement() {
        StringBuilder stm = new StringBuilder("DELETE FROM ");
        stm.append(tableType.getName());
        if (!methodType.getFor().isEmpty()) stm.append(generateWhereClause());
        return stm.toString();
    }
    
    protected String generateWhereClause() {
        StringBuilder stm = new StringBuilder(" WHERE ");
        methodType.getFor().forEach((MethodForType m) -> {
            if (!stm.substring(stm.length() - 6).equals("WHERE ")) stm.append(" ").append(m.getType()).append(" ");
            stm.append(translateId(m.getField()));
            boolean noValue = false;
            boolean parenthesis = false;
            switch (m.getOperator()) {
                case EQUAL: 
                    stm.append(Operator.EQUAL.getOperator());
                    break;
                case NOTEQUAL: 
                    stm.append(Operator.NOTEQUAL.getOperator());
                    break;
                case GT: 
                    stm.append(Operator.GT.getOperator());
                    break;
                case GET: 
                    stm.append(Operator.GET.getOperator());
                    break;
                case LT: 
                    stm.append(Operator.LT.getOperator());
                    break;
                case LET: 
                    stm.append(Operator.LET.getOperator());
                    break;
                case ISNULL: 
                    stm.append(Operator.ISNULL.getOperator());
                    noValue = true;
                    break;
                case ISNOTNULL: 
                    stm.append(Operator.ISNOTNULL.getOperator());
                    noValue = true;
                    break;
                case IN:
                    stm.append(Operator.IN.getOperator());
                    parenthesis = true;
                    break;
                    
            }
            if (!noValue) {
                if (parenthesis) stm.append("(");
                stm.append(":");
                stm.append(m.getField());
                if (parenthesis) stm.append(")");
            }
        });
        return stm.toString();
    }
    
    protected String generateId() {
        String sequenceName = "nextval('" + tableType.getName() + "_id_seq')";
        return sequenceName;
    }
    
    protected String generateOffsetLimit() {
        if (null == methodType.getOffset() && null == methodType.getLimit()) return "";
        
        StringBuilder paging = new StringBuilder(" OFFSET ");
        String offset = "0";
        if (null != methodType.getOffset()) offset = methodType.getOffset().toString();
        paging.append(offset).append(" ROWS");
        
        if (null != methodType.getLimit()) {
            paging.append(" FETCH FIRST ");
            paging.append(methodType.getLimit());
            paging.append(" ROWS");
        }
        
        return paging.toString();
    }

    protected enum Operator {
        
        EQUAL(" = "),
        NOTEQUAL(" != "),
        GT(" > "),
        GET(" >= "),
        LT(" < "),
        LET(" <= "),
        ISNULL(" is null"),
        ISNOTNULL(" is not null"),
        IN(" in ");
        
        private final String operator;
        
        Operator(String operator) {
            this.operator = operator;
        }
        
        public String getOperator() {
            return operator;
        }
    }
}
