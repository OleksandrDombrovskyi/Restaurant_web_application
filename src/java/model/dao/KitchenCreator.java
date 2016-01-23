/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.entity.DBEntity;
import model.entity.Kitchen;

/**
 *
 * @author Sasha
 */
public class KitchenCreator extends EntityCreator {
    
    /** sql value of order table name */
    private final static String KITCHEN_TABLE = "kitchen";
    
    /** sql value of order id name */
    private final static String KITCHEN_ID = "kitchen_id";
    
    /** sql to get kitchen by email */
    private final static String SQL_FOR_KITCHEN_BY_EMAIL = 
            "SELECT * FROM kitchen WHERE email = ?";

    /**
     * Constructor
     */
    public KitchenCreator() {
        super(KITCHEN_TABLE, KITCHEN_ID);
    }
    
    /**
     * Get user from DB by email
     * @param email user's email
     * @return user if such user exists or null otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public DBEntity getKitchenByEmail(String email) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_KITCHEN_BY_EMAIL);
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
     * Get one kitchen by result set
     * 
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     * @throws model.dao.ServerOverloadedException
     */
    @Override
    protected DBEntity getEntity(ResultSet rs) throws SQLException, ServerOverloadedException {
        int id = rs.getInt("kitchen_id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String name = rs.getString("name");
        Kitchen newKitchen = new Kitchen(email, password, name);
        newKitchen.setId(id);
        return newKitchen;
    }
    
}
