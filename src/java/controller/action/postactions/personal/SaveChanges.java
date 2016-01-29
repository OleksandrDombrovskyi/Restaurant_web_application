/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal;

import controller.action.Validator;
import java.io.IOException;
import javax.servlet.ServletException;
import model.entity.Person;

/**
 * Saving all changes of users' private information (name, last name, email)
 * @author Sasha
 */
public abstract class SaveChanges extends PersonalPostAction {

    /**
     * Save all changes of users' private information (name, last name, email)
     * @throws ServletException
     * @throws IOException 
     */    
    @Override
    protected void doExecute() throws ServletException, IOException {
        Person person = getPersonFromSession();
        if (person == null) {
            sendRedirect(null, "login.errormessage.loginplease");
            return;
        }
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        saveFieldValues(firstName, lastName, email);
        
        if (!checkFields(firstName, lastName, email)) {
            return;
        }
        int personId = person.getId();
        if (!updatePerson(personId, firstName, lastName, email)) {
            return;
        }
        sendRedirect("settings.message.changeswassaved", null, "link.settings");
    }
    
    /**
     * Check all input fields
     * @param firstName users' first name
     * @param lastName users' last name
     * @param email users' email
     * @return boolean true if all fields are correct and false otherwise (in 
     * this case redirection will be performed in this method)
     */
    private boolean checkFields(String firstName, String lastName, 
            String email) throws ServletException, IOException {
        if (firstName == null || firstName.equals("")) {
            sendRedirect(null, "settings.errormessage.emptyname", "link.settings");
            return false;
        }
        if (lastName == null || lastName.equals("")) {
            sendRedirect(null, "settings.errormessage.emptylastname", "link.settings");
            return false;
        }
        if (email == null || email.equals("")) {
            sendRedirect(null, "settings.errormessage.emptyemail", "link.settings");
            return false;
        }
        Validator validator = new Validator();
        if (!validator.checkEmail(email)) {
            sendRedirect(null, "settings.errormessage.uncorrectemail", "link.settings");
            return false;
        }
        return true;
    }
    
    /**
     * Save previous fields values 
     * @param name user name
     * @param lastName user last name
     * @param email user email
     * @param request http servlet request
     */
    private void saveFieldValues(String firstName, String lastName, String email) {
        request.setAttribute("firstName", firstName);
        request.setAttribute("lastName", lastName);
        request.setAttribute("email", email);
    }

    /**
     * Update persons' private information (first name, last name and email). If
     * updating is successful, get the same person from data base by its' email 
     * with updated information and set it to the current session
     * 
     * @param personId person id
     * @param firstName person first name 
     * @param lastName person last name
     * @param email person email
     * @return true if person update was seccessful and false otherwise (in this 
     * case redirection will be performed in this method)
     * @throws ServletException
     * @throws IOException 
     */
    protected abstract boolean updatePerson(int personId, String firstName, 
            String lastName, String email) throws ServletException, IOException;
    
}
