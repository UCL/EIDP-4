package uk.ac.ucl.eidp.service;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author David Guzman
 */
public class ServiceConfig extends ResourceConfig {

  public ServiceConfig() {
    super();
    packages(true, "uk.ac.ucl.eidp.service");
  }

}
