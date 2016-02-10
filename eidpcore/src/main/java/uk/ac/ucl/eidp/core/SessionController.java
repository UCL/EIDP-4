/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ucl.eidp.core;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
@DeclareRoles({"PUBLIC","SIGNEDIN"})
public class SessionController {

    @RolesAllowed({"SIGNEDIN"})
    public void dataQuery() {
    }
    
    @PermitAll
    public String authCall() {
        return "";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

}
