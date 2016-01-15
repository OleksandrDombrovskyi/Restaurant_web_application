/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.entity.DBEntity;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserBuilder extends EntityBuilder {

    /** sql query for all users */
    private static final String SQL_FOR_ALL_USERS = "SELECT * FROM user";
    
    /** sql query to get entity by id */
    private static final String SQL_FOR_USER_BY_ID = "SELECT * FROM user WHERE user_id = ?";

    /** sql query for inserting user into the user table in the data base */
    private static final String SQL_FOR_USER_INSERTING = "INSERT INTO user (first_name, last_name, email, account, zone_id) VALUES (?, ?, ?, ?, ?)";
    
    /** sql query for deleting user from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM user WHERE user_id = ?";
    
    /**
     * Get sql query for all user 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return list of users
     * @throws SQLException
     */
    @Override
    protected List<DBEntity> getAllEntities(WrapperConnectionProxy wrapperConnection) 
            throws SQLException {
        List<DBEntity> users = new ArrayList<>();
        Statement st = wrapperConnection.createStatement();
        ResultSet rs = st.executeQuery(SQL_FOR_ALL_USERS);
        while (rs.next()) {
            users.add(getUser(rs));
        }
        return users;
    }
    
    /**
     * Get one user by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    private DBEntity getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        BigDecimal account = rs.getBigDecimal("account");
        int zoneId = rs.getInt("zone_id");
        return new User(id, firstName, lastName, email, account, zoneId);
    }

    /**
     * Get user by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id user id
     * @return prepared statement 
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntityById(WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_USER_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getUser(rs);
        }
        return null;
    }

    /**
     * Insert user into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity user might be inserting into the data base
     * @throws SQLException 
     */
    @Override
    protected void insertEntity(WrapperConnectionProxy wrapperConnection, 
            DBEntity entity) throws SQLException {
        User user = (User) entity;
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_USER_INSERTING);
        ps.setString(1, user.getFirstName());
        ps.setString(2, user.getLastName());
        ps.setString(3, user.getEmail());
        ps.setBigDecimal(4, user.getAccount());
        ps.setInt(5, user.getZoneId());
        ps.executeUpdate();
    }

    /**
     * Delete user from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id entity id 
     * @throws SQLException 
     */
    @Override
    protected void deleteById(WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
}
