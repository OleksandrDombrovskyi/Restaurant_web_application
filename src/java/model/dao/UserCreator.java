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
import static model.dao.EntityCreator.CONNECTION_POOL;
import model.entity.DBEntity;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserCreator extends EntityCreator {

    /** sql value of user table name */
    private final static String USER_TABLE = "user";
    
    /** sql value of user id name */
    private final static String USER_ID = "user_id";
    
    /** sql query for inserting user into the user table in the data base */
    private static final String SQL_FOR_USER_INSERTING = "INSERT INTO user (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";
    
//    /** sql query for deleting user from the data base table */
//    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM user WHERE user_id = ?";
    
    /** sql query for getting the user from data base by his email */
    private static final String SQL_FOR_USER_BY_EMAIL = "SELECT * FROM user WHERE email = ?";

    /**
     * Constructor
     */
    public UserCreator() {
        super(USER_TABLE, USER_ID);
    }
    
    /**
     * Insert user into the data base from the object oriented entity
     * 
     * @param user user might be inserting into the data base
     * @throws SQLException 
     * @throws ServerOverloadedException
     * @return true is user inserting was successfull and false otherwise
     */
    public boolean insertUser(User user) throws SQLException, ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_USER_INSERTING);
            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getEmail());
            ps.setString(4, user.getPassword());
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
     * Get one user by result set
     * 
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntity(ResultSet rs) throws SQLException {
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
    
}
