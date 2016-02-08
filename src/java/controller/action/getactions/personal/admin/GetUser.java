/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.admin;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
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
    
    /** title string key value */
    private final static String TITLE = "administration.user.text.title";

    /**
     * Constructor
     */
    public GetUser() {
        super(TITLE);
    }

    /**
     * Get all users from data base for admin
     * @return property key value
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            setMessages(null, LOGIN_PLEASE);
            return configManager.getProperty(HOME_PAGE);
        }
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            setMessages(null, WRONG_USER_ID);
            return configManager.getProperty(ADMIN_GET_ALL_ORDERS);
        }
        int userId = Integer.parseInt(userIdString);
        User concreteUser = getUserById(userId);
        if (concreteUser == null) {
            return null;
        }
        request.setAttribute("concreteUser", concreteUser);
        return configManager.getProperty("path.page.admin.getuser");
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
