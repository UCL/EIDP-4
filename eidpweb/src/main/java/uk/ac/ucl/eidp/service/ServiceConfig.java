package uk.ac.ucl.eidp.service;

import org.glassfish.jersey.server.ResourceConfig;

/**
 * Configuration of REST resources.
 * @author David Guzman {@literal d.guzman at ucl.ac.uk}
 */
public class ServiceConfig extends ResourceConfig {

  public ServiceConfig() {
    super();
    packages(true, "uk.ac.ucl.eidp.service");
  }

}
