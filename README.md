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
<<<<<<< HEAD
[![Coverage Status](https://coveralls.io/repos/github/UCL/EIDP-4/badge.svg?branch=master)](https://coveralls.io/github/UCL/EIDP-4?branch=master)
=======
[![codecov](https://codecov.io/gh/UCL/EIDP-4/branch/master/graph/badge.svg)](https://codecov.io/gh/UCL/EIDP-4)
>>>>>>> master
[![Sputnik](https://sputnik.ci/conf/badge)](https://sputnik.ci/app#/builds/UCL/EIDP-4)

## Components

* **eidpauth**: [![Dependency Status](https://www.versioneye.com/user/projects/57a350c54735990011566a66/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57a350c54735990011566a66) EJB module for authentication and authorisation
* **eidpdata**: [![Dependency Status](https://www.versioneye.com/user/projects/57a35202928dc9000d323b82/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57a35202928dc9000d323b82) EJB module for data access
* **eidpweb**:  [![Dependency Status](https://www.versioneye.com/user/projects/57a350c64735990011566a70/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57a350c64735990011566a70) WAR module for serving data to client apps
* **eidpng**:   [![Dependency Status](https://www.versioneye.com/user/projects/57a361a31c1553000cdbf680/badge.svg?style=flat-square)](https://www.versioneye.com/user/projects/57a361a31c1553000cdbf680) Angular 2 client app

```bash
mvn clean verify -P arquillian-glassfish-remote
mvn clean verify -P arquillian-wildfly-remote
```
