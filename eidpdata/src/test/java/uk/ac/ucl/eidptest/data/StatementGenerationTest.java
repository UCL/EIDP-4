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
    public void translateGetStatement() {
        String expected = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = ?";       
        String sqlStatement = "SELECT id, role FROM UCLBRIT.T_ROLES WHERE login = :login";
        Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')");
        String generated = sqlStatement.replaceAll(findParametersPattern.patter‌n(), "?");
        assertEquals(generated, expected);
    }
    
    @Test
    public void translateSetStatement() {
        String expected = "UPDATE UCLBRIT.T_USERS SET login_err_number = ?, login_err_timestamp = ? WHERE login = ?;INSERT INTO UCLBRIT.T_USERS (login_err_number, login_err_timestamp, id) VALUES (?, ?, nextval('UCLBRIT.T_USERS_id_seq'))";       
        String sqlStatement = "UPDATE UCLBRIT.T_USERS SET login_err_number = :login_err_number, login_err_timestamp = :login_err_timestamp WHERE login = :login;INSERT INTO UCLBRIT.T_USERS (login_err_number, login_err_timestamp, id) VALUES (:login_err_number, :login_err_timestamp, nextval('UCLBRIT.T_USERS_id_seq'))";
        Pattern findParametersPattern = Pattern.compile("(?<!')(:[\\w]*)(?!')");
        String generated = sqlStatement.replaceAll(findParametersPattern.patter‌n(), "?");
        assertEquals(generated, expected);
    }
      
    @Test
    public void datasetPath() {
        String methodPath = "context-test.USERS.getUserDataForLogin";
        String generated = methodPath.substring(0,methodPath.lastIndexOf("."));
        String expected = "context-test.USERS";
        assertEquals(generated, expected);
    }
    
    @Test
    public void splitStatements() {
        
    }
}
