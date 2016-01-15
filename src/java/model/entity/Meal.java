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
public class Meal extends Service {
    
    /** meal type */
    private MealType type;
    
    /** meal name */
    private String name;
    
    /** meal description */
    private String description;
    
    /** number of goods which are in the storehouse */
    private int presenceNumber;
    
    /**
     * Constructor
     * @param id meal id
     * @param type meal type
     * @param name meal name
     * @param description meal description 
     * @param price meal price
     * @param presenceNumber number of goods which are in the storehouse
     */
    public Meal(int id, BigDecimal price, String type, String name, 
            String description, int presenceNumber) {
        super(id, price);
        setType(type);
        this.name = name;
        this.description = description;
        this.presenceNumber = presenceNumber;
    }
    
    /**
     * Set meal type
     * @param type meal type
     */
    public void setType(MealType type) {
        this.type = type;
    }
    
    /**
     * Set meal type
     * @param typeString meal type as string variable
     */
    public final void setType(String typeString) {
        switch (typeString) {
            case "Express Lunch":
                this.type = MealType.EXPRESS_LUNCH;
                break;
            case "Dessert":
                this.type = MealType.DESSERT;
                break;
            case "Beverage":
                this.type = MealType.BEVERAGE;
                break;
            case "Bottled Beer":
                this.type = MealType.BOTTLE_BEER;
                break;   
        }
    }

    /**
     * Get meal type
     * @return 
     */
    public MealType getType() {
        return type;
    }
    
    public String getTypeString() {
        switch (type) {
            case EXPRESS_LUNCH:
                return "Express Lunch";
            case DESSERT:
                return "Dessert";
            case BEVERAGE:
                return "Beverage";
            case BOTTLE_BEER:
                return "Bottled Beer"; 
            default: 
                return null;
        }
    }
    
    /**
     * Set meal name
     * @param name meal name
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * get meal name
     * @return meal name
     */
    public String getName() {
        return name;
    }
    
    /**
     * Set meal description
     * @param description meal description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get meal description 
     * @return meal description
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * Set presence number
     * @param presenceNumber number of goods which are in the storehouse
     */
    public void setPresenceNumber(int presenceNumber) {
        this.presenceNumber = presenceNumber;
    }
    
    /**
     * Get presence number 
     * @return number of goods which are in the storehouse
     */
    public int getPresenceNumber() {
        return presenceNumber;
    }
    
    /**
     * Class of the meal type
     */
    public enum MealType {
        EXPRESS_LUNCH, DESSERT, BEVERAGE, BOTTLE_BEER
    }
}
