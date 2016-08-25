package uk.ac.ucl.eidp.data.jaxb;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class JaxbSqlAnsi extends JaxbSqlStatement {

  @Override
  protected String buildGetStatement() {
    StringBuilder stm = new StringBuilder("SELECT ");

    List<String> queryFields = new ArrayList<>();
    methodType.getFields().getField().forEach((String field) -> {
      queryFields.add(translateId(field));
    });

    stm.append(String.join(", ", queryFields)).append(" FROM ").append(tableType.getName());

    if (!methodType.getFor().isEmpty()) {
      stm.append(generateWhereClause());
    }

    if (!methodType.getOrder().isEmpty()) {
      stm.append(" ORDER BY ");
      methodType.getOrder().forEach((MethodOrderType order) -> {
        if (!stm.substring(stm.length() - 9).equals("ORDER BY ")) {
          stm.append(", ");
        }
        stm.append(translateId(order.getField())).append(' ').append(order.getSorting().value());
      });
    }

    stm.append(generateOffsetLimit());

    return stm.toString();
  }

  @Override
  protected String buildSetStatement() {
    StringBuilder stm = new StringBuilder("UPDATE ");
    stm.append(tableType.getName()).append(" SET ");

    methodType.getFields().getField().forEach((String field) -> {
      if (!stm.substring(stm.length() - 4).equals("SET ")) {
        stm.append(", ");
      }
      stm.append(translateId(field)).append(" = :").append(field);
    });

    if (!methodType.getFor().isEmpty()) {
      stm.append(generateWhereClause());
    }

    stm.append(";INSERT INTO ").append(tableType.getName()).append(" (");
    methodType.getFields().getField().forEach((String field) -> {
      stm.append(translateId(field)).append(", ");
    });
    stm.append(tableType.getPrimaryKey()).append(") VALUES (");
    methodType.getFields().getField().forEach((String field) -> {
      stm.append(':').append(field).append(", ");
    });
    stm.append(generateId()).append(')');
    return stm.toString();
  }

  @Override
  protected String buildDelStatement() {
    StringBuilder stm = new StringBuilder("DELETE FROM ");
    stm.append(tableType.getName());
    if (!methodType.getFor().isEmpty()) {
      stm.append(generateWhereClause());
    }
    return stm.toString();
  }

  protected String generateWhereClause() {
    StringBuilder stm = new StringBuilder(" WHERE ");
    methodType.getFor().forEach((MethodForType methodFor) -> {
      if (!stm.substring(stm.length() - 6).equals("WHERE ")) {
        stm.append(' ').append(methodFor.getType()).append(' ');
      }
      stm.append(translateId(methodFor.getField()));
      boolean noValue = false;
      boolean parenthesis = false;
      switch (methodFor.getOperator()) {
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
        default:
          noValue = true;
          break;
      }
      if (!noValue) {
        if (parenthesis) {
          stm.append('(');
        }
        stm.append(':').append(methodFor.getField());
        if (parenthesis) {
          stm.append(')');
        }
      }
    });
    return stm.toString();
  }

  protected String generateId() {
    String sequenceName = "nextval('" + tableType.getName() + "_id_seq')";
    return sequenceName;
  }

  protected String generateOffsetLimit() {
    if (null == methodType.getOffset() && null == methodType.getLimit()) {
      return "";
    }

    StringBuilder paging = new StringBuilder(" OFFSET ");
    String offset = "0";
    if (null != methodType.getOffset()) {
      offset = methodType.getOffset().toString();
    }
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
