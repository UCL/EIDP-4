/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ucl.eidp.auth;

import javax.ejb.Local;
import uk.ac.ucl.eidp.auth.model.UserE;

/**
 *
 * @author David Guzman <d.guzman at ucl.ac.uk>
 */
@Local
public interface UserControllerLocal {

    public UserE findUser(final String login, final String password);
    
}
