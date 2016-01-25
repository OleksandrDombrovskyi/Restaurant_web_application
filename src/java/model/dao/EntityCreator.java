/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public abstract class EntityCreator implements DAO {
    
    /** sql query for all entities */
    private String sqlForAllEntities;
    
    /** sql query to get entity by id */
    private String sqlForSearchingBiId;
    
    /** sql query for deleting entity from the data base table */
    private String sqlForDeletingById;
    
    /**
     * Constructor
     * @param entityTable sql value of concrete entity table name 
     * @param entityId sql value of concrete entity id name 
     */
    public EntityCreator(String entityTable, String entityId) {
        sqlForAllEntities =  "SELECT * FROM restaurantdatabase." + entityTable;
        sqlForSearchingBiId = "SELECT * FROM restaurantdatabase." + entityTable + " WHERE " 
                + entityId + " = ?";
        sqlForDeletingById = "DELETE FROM restaurantdatabase." + entityTable + " WHERE " 
                + entityId + " = ?";
    }
    
    /**
     * Get all entities from the db
     * 
     * @return list of entities
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public Object getAllEntities() throws SQLException, 
            ServerOverloadedException {
        List<DBEntity> entities = new ArrayList<>();
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            Statement st = wrapperConnection.createStatement();
            ResultSet rs = st.executeQuery(sqlForAllEntities);
            while (rs.next()) {
                entities.add(getEntity(rs));
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return entities;
    }
    
    /**
     * Get entity by id
     * 
     * @param id id of entity
     * @return data base entity
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public DBEntity getEntityById(int id) 
            throws SQLException, ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.
                    prepareStatement(sqlForSearchingBiId);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getEntity(rs);
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return null;
    }
    
    /**
     * Delete entity from the data base by id
     * 
     * @param id entity id
     * @return true if deleting is successfull or false otherwise
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public boolean deleteById(int id) 
            throws SQLException, ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = 
                    wrapperConnection.prepareStatement(sqlForDeletingById);
            ps.setInt(1, id);
            ps.executeUpdate();
            flag = true;
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }
    
    /**
     * Get one entity by result set
     * 
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     * @throws model.dao.ServerOverloadedException
     */
    protected abstract DBEntity getEntity(ResultSet rs) throws SQLException, 
            ServerOverloadedException;
    
}
