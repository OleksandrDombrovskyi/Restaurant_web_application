/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.User;

/**
 * Get all registered users for admin
 * @author Sasha
 */
public class GetUser extends AdminGetAction {

    /**
     * Get all users from data base for admin
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            sendRedirect(null, "administration.users.errormessage.wronguserid", "getAllOrders");
        }
        int userId = Integer.parseInt(userIdString);
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            return;
        }
        request.setAttribute("concreteUser", concreteUser);
        goToPage("administration.user.text.title", "/view/person/admin/user.jsp");
    }
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    @Override
    public List<ConcreteLink> getLink() {
        return new GetUsers().getLink();
    }
    
}
