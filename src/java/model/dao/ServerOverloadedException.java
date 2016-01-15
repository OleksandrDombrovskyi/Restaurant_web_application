/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

/**
 *
 * @author Sasha
 */
public class ServerOverloadedException extends Exception {
    
    /**
     * Get message method
     * @return string with exception description
     */
    @Override
    public String getMessage() {
        return "All existing connections are busy";
    }
    
}
