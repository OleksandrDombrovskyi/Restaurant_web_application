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
    
    /**
     * Constructor
     * @param firstName admin first name
     * @param lastName admin last name
     * @param email admin email
     * @param password admin password
     * @param adminType ordinary admin or super admin
     */
    public Admin(String firstName, String lastName, String email, String password, 
            AdminType adminType) {
        super(firstName, lastName, email, password);
        this.adminType = adminType;
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
     * Class admin type
     */
    public enum AdminType {
        ORDINARY_ADMIN,
        SUPER_ADMIN
    }
}
