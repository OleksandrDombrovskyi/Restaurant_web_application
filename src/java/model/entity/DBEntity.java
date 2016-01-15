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
public abstract class DBEntity {
    
    /** entity id */
    protected int id;

    /**
     * Constructor
     * @param id entity id
     */
    public DBEntity(int id) {
        this.id = id;
    }

    /**
     * Set entity id
     * @param id entity id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get entity id
     * @return int entity id
     */
    public int getId() {
        return id;
    }
    
}
