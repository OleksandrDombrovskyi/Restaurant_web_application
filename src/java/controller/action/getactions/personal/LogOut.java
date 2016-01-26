/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal;

import controller.action.getactions.GetAction;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Log out
 * @author Sasha
 */
public class LogOut extends GetAction {

    /**
     * Log out user or admin (go to user role)
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        session.invalidate();
        return request.getHeader("Referer");
    }
    
}
