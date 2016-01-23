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
public class Kitchen extends DBEntity {
    
    /** kitchen first name */
    protected String name;
    
    /** kitchen email */
    protected String email;
    
    /** kitchen password */
    protected String password;
    
    /**
     * Constructor
     * @param email kitchen email
     * @param password kitchen password
     * @param name kitchen name
     */
    public Kitchen(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    
    /**
     * Get email
     * @return string person email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set kitchen email
     * @param email person email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Set kitchen password
     * @param password person password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get kitchen password
     * @return person password
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Set kitchen first name
     * @param firstName admin first name  
     */
    public void setName(String firstName) {
        this.name = firstName;
    }
    
    /**
     * Get kitchen first name
     * @return admin first name
     */
    public String getName() {
        return name;
    }
    
}
