```
EEEEEEEEEEEEEEEEEEEEEEIIIIIIIIIIDDDDDDDDDDDDD      PPPPPPPPPPPPPPPPP        444444444  
E::::::::::::::::::::EI::::::::ID::::::::::::DDD   P::::::::::::::::P      4::::::::4  
E::::::::::::::::::::EI::::::::ID:::::::::::::::DD P::::::PPPPPP:::::P    4:::::::::4  
EE::::::EEEEEEEEE::::EII::::::IIDDD:::::DDDDD:::::DPP:::::P     P:::::P  4::::44::::4  
  E:::::E       EEEEEE  I::::I    D:::::D    D:::::D P::::P     P:::::P 4::::4 4::::4  
  E:::::E               I::::I    D:::::D     D:::::DP::::P     P:::::P4::::4  4::::4  
  E::::::EEEEEEEEEE     I::::I    D:::::D     D:::::DP::::PPPPPP:::::P4::::4   4::::4  
  E:::::::::::::::E     I::::I    D:::::D     D:::::DP:::::::::::::PP4::::444444::::444
  E:::::::::::::::E     I::::I    D:::::D     D:::::DP::::PPPPPPPPP  4::::::::::::::::4
  E::::::EEEEEEEEEE     I::::I    D:::::D     D:::::DP::::P          4444444444:::::444
  E:::::E               I::::I    D:::::D     D:::::DP::::P                    4::::4  
  E:::::E       EEEEEE  I::::I    D:::::D    D:::::D P::::P                    4::::4  
EE::::::EEEEEEEE:::::EII::::::IIDDD:::::DDDDD:::::DPP::::::PP                  4::::4  
E::::::::::::::::::::EI::::::::ID:::::::::::::::DD P::::::::P                44::::::44
E::::::::::::::::::::EI::::::::ID::::::::::::DDD   P::::::::P                4::::::::4
EEEEEEEEEEEEEEEEEEEEEEIIIIIIIIIIDDDDDDDDDDDDD      PPPPPPPPPP                4444444444
```

# EIDP
## Platform for development and integration of clinical databases for Java EE

[![Build Status](https://travis-ci.org/UCL/EIDP-4.svg?branch=master)](https://travis-ci.org/UCL/EIDP-4)
[![Coverage Status](https://coveralls.io/repos/github/UCL/EIDP-4/badge.svg?branch=master)](https://coveralls.io/github/UCL/EIDP-4?branch=master)
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#/builds/UCL/EIDP-4)

## Components

* **eidpauth**: EJB module for authentication and authorisation
* **eidpdata**: EJB module for data access
* **eidpweb**:  WAR module for serving data to client apps
* **eidpng**:   Angular 2 client app

```bash
mvn clean verify -P arquillian-glassfish-remote
mvn clean verify -P arquillian-wildfly-remote
```
