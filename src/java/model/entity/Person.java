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
public abstract class Person extends DBEntity{
    
    /** admin first name */
    protected String firstName;
    
    /** admin last name */
    protected String lastName;
    
    /** person email */
    protected String email;
    
    /** person password */
    protected String password;

    /**
     * Constructor
     * @param firstName first name of person
     * @param lastName persone last name
     * @param email person email
     * @param password person password
     */
    public Person(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
    
    /**
     * Get admin first name
     * @return admin first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Get admin last name
     * @return admin last name
     */
    public String getLastName() {
        return lastName;
    }
    
    /**
     * Set admin first name
     * @param firstName admin first name  
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Set admin last name
     * @param lastName admin last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    /**
     * Get email
     * @return string person email
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Set person email
     * @param email person email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Set person password
     * @param password person password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    
    /**
     * Get person password
     * @return person password
     */
    public String getPassword() {
        return password;
    }
    
}
