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
package uk.ac.ucl.eidptest.data;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.*;
import org.testng.TestException;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.data.DBMapping;
import uk.ac.ucl.eidp.data.PoolStrategy;
import uk.ac.ucl.eidp.data.StrategyCache;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class DBMappingTest extends Arquillian {

    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(DBMapping.class, StrategyCache.class);
            System.out.println(jar.toString(true));
        return jar;
    }
    
    @EJB
    DBMapping dbMapping;

    /**
     * Test of loadMapping method, of class DBMapping.
     */
    @Test
    public void testDbAction() {
        List<Map<String, String>> dbActionR = dbMapping.dbAction("context-test.USERS.getUserDataForLogin", null);
    }

    
}
