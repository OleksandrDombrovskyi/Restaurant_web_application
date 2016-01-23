/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.action.postactions.personal.SetOrderStatus;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class PayOrder extends SetOrderStatus {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        
    }
    
}
