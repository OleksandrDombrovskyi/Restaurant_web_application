/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class SetAuthorizationBlock extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            request.getRequestDispatcher("/view/user/authorization.jsp").
                    include(request, response);
            return;
        }
        Admin admin = (Admin) request.getSession().getAttribute("admin");
        if (admin != null) {
            request.getRequestDispatcher("/view/admin/authorization.jsp").
                    include(request, response);
            return;
        }
        request.getRequestDispatcher("/view/guest/authorization.jsp").
                include(request, response);
    }
    
}
