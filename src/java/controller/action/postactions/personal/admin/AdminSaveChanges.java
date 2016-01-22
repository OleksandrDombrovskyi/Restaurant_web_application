/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.admin;

import controller.action.postactions.personal.SaveChanges;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.AdminCreator;
import model.dao.ServerOverloadedException;
import model.entity.Admin;
import model.entity.Admin.AdminType;
import model.entity.Person;

/**
 *
 * @author Sasha
 */
public class AdminSaveChanges extends SaveChanges {

    /**
     * Get admin from the session
     * 
     * @return admin object if it is in the session and null otherwise (in this 
     * case redirection will be performed by this method)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected Person getPersonFromSession() throws ServletException, IOException {
        Admin admin = (Admin) session.getAttribute("admin");
        if (admin == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return null;
        }
        return admin;
    }

    /**
     * Update admins' private information (first name, last name and email). If
     * updating is successful, get the same admin from data base by its' email 
     * with updated information and set it to the current session
     * 
     * @param personId admin id
     * @param firstName admin first name 
     * @param lastName admin last name
     * @param email admin email
     * @return true if admin update was seccessful and false otherwise (in this 
     * case redirection will be performed in this method)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected boolean updatePerson(int personId, String firstName, String lastName, 
            String email) throws ServletException, IOException {
        Admin newAdmin = new Admin(firstName, lastName, email, "unusedPassword", 
                AdminType.ORDINARY_ADMIN);
        newAdmin.setId(personId);
        AdminCreator adminCreator = new AdminCreator();
        try {
            if (!adminCreator.updateAdmin(newAdmin)) {
                sendRedirect(null, "settings.errormessage.changesnotsaved", "settings");
                return false;
            }
            return setAdminToSession(newAdmin.getEmail());
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "settings");
            return false;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "settings");
            return false;
        }
    }
    
}
