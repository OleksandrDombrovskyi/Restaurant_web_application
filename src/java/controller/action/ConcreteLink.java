/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

/**
 *
 * @author Sasha
 */
public class ConcreteLink {
    
    /** value of the link */
    private final String linkValue;
    
    /** name of the link */
    private final String linkName;
    
    /**
     * Constructor
     * @param linkValue value of the link
     * @param linkName name of the link
     */
    public ConcreteLink(String linkValue, String linkName) {
        this.linkValue = linkValue;
        this.linkName = linkName;
    }

    /**
     * Get link value
     * @return string link value
     */
    public String getLinkValue() {
        return linkValue;
    }

    /**
     * Get link name
     * @return string link name
     */
    public String getLinkName() {
        return linkName;
    }
    
}
