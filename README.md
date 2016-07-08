# EIDP 4
EIDP Version 4

![alt tag](https://travis-ci.org/UCL/EIDP-4.svg?branch=master)

## Components

* **eidpauth**: EJB module for authentication and authorisation
* **eidpdata**: EJB module for data access
* **eidpweb**:  WAR module for serving data to client apps
* **eidpng**:   Angular 2 client app

```bash
mvn clean verify -P arquillian-glassfish-remote
mvn clean verify -P arquillian-wildfly-remote
```
