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

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import uk.ac.ucl.eidp.data.DBMappingStrategy;
import uk.ac.ucl.eidp.data.StrategyCache;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class StrategyCacheTest {
    
    public StrategyCacheTest() {
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void getDbMappingStrategyForId() {
        StrategyCache strategyCache = new StrategyCache();
        DBMappingStrategy dbMappingStrategy = strategyCache.getDbMappingStrategyForId("gateway");
    }

}
