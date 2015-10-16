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

import java.util.regex.Pattern;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
public class StatementGenerationTest {
    
    public StatementGenerationTest() {
    }
    
    @Test
    public void translateStatement() {
        String expected = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = ?";       
        String sqlStatement = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = :login";
        Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')");
        String generated = sqlStatement.replaceAll(findParametersPattern.patter‌n(), "?");
        assertEquals(generated, expected);
    }
}
