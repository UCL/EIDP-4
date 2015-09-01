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

import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.testng.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.data.DBMappingStrategy;
import uk.ac.ucl.eidp.data.EidpStrategy;
import uk.ac.ucl.eidp.data.JdbcStrategy;
import uk.ac.ucl.eidp.data.NodeQualifier;
import uk.ac.ucl.eidp.data.NodeType;
import uk.ac.ucl.eidp.data.PoolStrategy;
import uk.ac.ucl.eidp.data.StrategyResolver;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class StrategyCacheIT extends Arquillian {
    
    @Deployment
    public static JavaArchive createDeployment() {
        JavaArchive jar = ShrinkWrap.create(JavaArchive.class)
            .addClasses(StrategyResolver.class, DBMappingStrategy.class, NodeQualifier.class,
                    PoolStrategy.class, JdbcStrategy.class, EidpStrategy.class, NodeType.class)
            .addAsResource("META-INF/eidp/gateway.properties")
            .addAsResource("META-INF/beans.xml");
        return jar;
    }
    
    @EJB
    StrategyResolver strategyResolver;

    @Test
    public void getDbMappingStrategyForId() {
        DBMappingStrategy dbMappingStrategy = strategyResolver.getDbMappingStrategyForId("gateway");
    }

}
