package uk.ac.ucl.eidp.data.jaxb;

/**
 *
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class JaxbSqlPostgresql extends JaxbSqlAnsi {

  @Override
  protected String generateOffsetLimit() {
    if (null == methodType.getOffset() && null == methodType.getLimit()) {
      return "";
    }

    StringBuilder paging = new StringBuilder("");

    if (null != methodType.getLimit()) {
      paging.append(" LIMIT ");
      paging.append(methodType.getLimit());
    }

    if (null != methodType.getOffset()) {
      paging.append(" OFFSET ");
      paging.append(methodType.getOffset());
    }

    return paging.toString();
  }

}
