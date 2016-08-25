package uk.ac.ucl.eidp.data.jaxb;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class JaxbSqlHsqldb extends JaxbSqlAnsi {

  @Override
  protected String generateId() {
    String sequenceName = "(next value for " + tableType.getName() + "_id_seq)";
    return sequenceName;
  }

}
