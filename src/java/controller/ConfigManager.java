/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ResourceBundle;

/**
 *
 * @author Sasha
 */
public class ConfigManager {
    
    /** resource bundle */
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("controller.pages");
    
    /**
     * Get antire page path
     * @param key key value of property
     * @return string of required page path
     */
    public static String getProperty(String key) {
        return RESOURCE_BUNDLE.getString(key);
    }
    
}
