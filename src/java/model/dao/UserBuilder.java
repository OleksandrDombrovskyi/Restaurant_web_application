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
    private static final String SQL_FOR_USER_INSERTING = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    
    /** sql query for deleting user from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM user WHERE user_id = ?";
    
    /** sql query for getting the user from data base by his email */
    private static final String SQL_FOR_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";
    
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
        String password = rs.getString("password");
        BigDecimal account = rs.getBigDecimal("account");
        int zoneId = rs.getInt("zone_id");
        User newUser = new User(firstName, lastName, email, password);
        newUser.setId(id);
        newUser.setAccount(account);
        newUser.setZoneId(zoneId);
        //TODO: get all user's oders and add to the object's order list
        return newUser;
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
        ps.setString(4, user.getPassword());
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
    
    /**
     * get user from DB by email
     * @param email user's email
     * @return user if such user exists or null otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public DBEntity getUserByEmail(String email) throws SQLException, ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_USER_BY_EMAIL);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return getUser(rs);
            }
            return null;
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
    }
    
}
