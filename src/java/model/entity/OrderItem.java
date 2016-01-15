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
public class OrderItem {
    
    /** this item meal id */
    private Meal meal;
    
    /** price of this item */
    private BigDecimal totalPrice;
    
    /** number of meals in this item */
    private int mealAmount;
    
    private Zone zone;

    /**
     * COnstructor
     * @param meal meal id
     * @param totalPrice item price
     * @param mealAmount meal number
     */
    public OrderItem(Meal meal, BigDecimal totalPrice, int mealAmount, Zone zone) {
        this.meal = meal;
        this.totalPrice = totalPrice;
        this.mealAmount = mealAmount;
        this.zone = zone;
    }
    
    /**
     * Set meal in this item
     * @param meal meal in this item
     */
    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    /**
     * Get meal id
     * @return int meal id
     */
    public Meal getMeal() {
        return meal;
    }
    
    /**
     * Set item price
     * @param totalPrice bigdecimal iten price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Get item price
     * @return int item price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    /**
     * Set number of meal of this item
     * @param mealAmount int number of this item
     */
    public void setMealAmount(int mealAmount) {
        this.mealAmount = mealAmount;
    }

    /**
     * Get number of meals of this item
     * @return int number of this item
     */
    public int getMealAmount() {
        return mealAmount;
    }
    
    /**
     * Set zone
     * @param zone user's zone
     */
    public void setZone(Zone zone) {
        this.zone = zone;
    }
    
    /**
     * Get user's zone
     * @return zone
     */
    public Zone getZone() {
        return zone;
    }
    
}
