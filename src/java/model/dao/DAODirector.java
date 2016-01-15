/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public class DAODirector {
    
    /** builder object corresponding to required entity */
    private final EntityBuilder builder;
    
    /**
     * Constructor
     * @param entityMode 
     */
    public DAODirector(ENUMEntity entityMode) {
        switch (entityMode) {
            case ADMIN:
                builder = new AdminBuilder();
                break;
            case USER:
                builder = new UserBuilder();
                break;
            case MEAL:
                builder = new MealBuilder();
                break;
            case ORDER:
                builder = new OrderBuilder();
                break;
            case ZONE:
                builder = new ZoneBuilder();
                break;
            default:
                builder = null;
        }
    }
    
    /**
     * Get all entities 
     * @return list of entities 
     */
    public List<DBEntity> getAllEntities() {
        return builder.getAllEntities();
    }
    
    /**
     * Get entity bi id
     * @param id entity id
     * @return DBEntity object
     */
    public DBEntity getEntityById(int id) {
        return builder.getEntityById(id);
    }
    
    /**
     * Insert entity into the data base table
     * @param entity object oriented entity
     * @return boolean true if inserting is successful of false otherwise
     */
    public boolean createEntity(DBEntity entity) {
        return builder.createEntity(entity);
    }
    
    /**
     * Delete entity from the data base table bi id
     * @param id entity id
     * @return boolean true if deleting is successful of false otherwise
     */
    public boolean deleteById(int id) {
        return builder.deleteById(id);
    }
    
}
