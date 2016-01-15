/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.math.BigDecimal;

/**
 *
 * @author Sasha
 */
public abstract class Service extends DBEntity {
    
    /** zone price */
    private BigDecimal price;

    /**
     * Constructor
     * @param id service id
     * @param price service price
     */
    public Service(int id, BigDecimal price) {
        super(id);
        this.price = price;
    }
    
    /**
     * Get zone price
     * @return zone price
     */
    public BigDecimal getPrice() {
        return price;
    }
    
    /**
     * Set zone price 
     * @param price zone price
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
}
