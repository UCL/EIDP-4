package uk.ac.ucl.eidp.data;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
@Qualifier
@Retention(RUNTIME)
@Target( {TYPE, METHOD, FIELD, PARAMETER} )
public @interface NodeQualifier {
  
  /**
   * The NodeType value will tell {@link StrategyResolver} how to connect to a database.
   * @return {@link NodeType} the type of database node to connect to (pool, remote EIDP, jdbc)
   */ 
  NodeType value();
    
}
