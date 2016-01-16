/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class LogOut extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        request.getSession().removeAttribute("user");
        response.sendRedirect(request.getHeader("Referer")); //????
    }
    
}
