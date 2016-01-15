/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

/**
 *
 * @author Sasha
 */
public class Admin extends Person {
    
    /** admin type */
    private AdminType adminType;
    
    /** admin login */
    private String login;
    
    /** admin password */
    private String password;
    
    /**
     * Constructor
     * @param id admin id
     * @param firstName admin first name
     * @param lastName admin last name
     * @param email admin email
     * @param adminType ordinary admin or super admin
     * @param login admin login
     * @param password admin password
     */
    public Admin(int id, String firstName, String lastName, String email, 
            AdminType adminType, String login, String password) {
        super(id, firstName, lastName, email);
        this.adminType = adminType;
        this.login = login;
        this.password = password;
    }
    
    /**
     * Set admin type
     * @param adminType admin type
     */
    public void setAdminType(AdminType adminType) {
        this.adminType = adminType;
    }
    
    /**
     * Get admin type
     * @return admin type
     */
    public AdminType getAdminType() {
        return adminType;
    }
    
    /**
     * Set login
     * @param login string login
     */
    public void setLogin(String login) {
        this.login = login;
    }
    
    /**
     * Get login
     * @return string login
     */
    public String getLogin() {
        return login;
    }
    
    /**
     * Set password
     * @param password string password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get password
     * @return string password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Class admin type
     */
    public enum AdminType {
        ORDINARY_ADMIN,
        SUPER_ADMIN
    }
}
