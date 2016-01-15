/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public abstract class EntityBuilder {
    
    /** connection pool object */
    private static final ConnectionPool CONNECTION_POOL = new ConnectionPool();
    
    /**
     * Get all entities from the db
     * @return list of data base entities
     */
    public List<DBEntity> getAllEntities() {
        List<DBEntity> entities = null;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            entities = getAllEntities(wrapperConnection);
        } catch (SQLException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getAllEntities.SQLException: " + e.getMessage());
        } catch (ServerOverloadedException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getAllEntities.ServerOverloadedException: " + e.getMessage());
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return entities;
    }
    
    /**
     * Get sql query for all entities 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return strng sql query
     * @throws SQLException
     */
    protected abstract List<DBEntity> getAllEntities(
            WrapperConnectionProxy wrapperConnection) throws SQLException; 
    
    /**
     * Get entity by id
     * @param id id of entity
     * @return data base entity
     */
    public DBEntity getEntityById(int id) {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            return getEntityById(wrapperConnection, id);
        } catch (SQLException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.SQLException: " + e.getMessage());
        } catch (ServerOverloadedException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.ServerOverloadedException: " + e.getMessage());
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return null;
    }
    
    /**
     * Get prepared statement for finding entity by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id entity id
     * @return prepared statement 
     * @throws SQLException
     */
    protected abstract DBEntity getEntityById(
            WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException;
    
    /**
     * Create new entity
     * @param entity entity migth be updated into the data base
     * @return true if updating is successfull and false otherwise
     */
    public boolean createEntity(DBEntity entity) {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            insertEntity(wrapperConnection, entity);
            flag = true;
        } catch (SQLException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.SQLException: " + e.getMessage());
        } catch (ServerOverloadedException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.ServerOverloadedException: " + e.getMessage());
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    /**
     * Insert entity into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity entity might be inserting into the data base
     * @throws SQLException 
     */
    protected abstract void insertEntity(
            WrapperConnectionProxy wrapperConnection, DBEntity entity)
            throws SQLException;
    
    /**
     * Delete entity from the data base by id
     * @param id entity id
     * @return true if deleting is successfull or false otherwise
     */
    public boolean deleteById(int id) {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            deleteById(wrapperConnection, id);
            flag = true;
        } catch (SQLException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.SQLException: " + e.getMessage());
        } catch (ServerOverloadedException e) {
            //TODO: show error message
            System.out.println("EntityBuilder.getEntityById.ServerOverloadedException: " + e.getMessage());
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    /**
     * Delete entity from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id entity id
     * @throws SQLException 
     */
    protected abstract void deleteById(WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException;
    
}
