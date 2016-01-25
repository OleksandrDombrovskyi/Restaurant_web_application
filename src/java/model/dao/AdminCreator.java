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
import model.entity.Admin;
import model.entity.Admin.AdminType;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public class AdminCreator extends EntityCreator {
    
    /**
     * Constructor 
     */
    public AdminCreator() {
        super(ADMIN_TABLE, ADMIN_ID);
    }

    /**
     * Create new admin
     * 
     * @param admin admin migth be updated into the data base
     * @return true if inserting is successfull and false otherwise
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public boolean insertAdmin(Admin admin) throws SQLException, ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ADMIN_INSERTING);
            ps.setString(1, admin.getFirstName());
            ps.setString(2, admin.getLastName());
            ps.setString(3, admin.getEmail());
            ps.setString(4, admin.getPassword());
            ps.setString(5, admin.getAdminType().toString());
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
     * Get admin from DB by email
     * @param email admins' email
     * @return admin if such admin exists or null otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public DBEntity getAdminByEmail(String email) throws SQLException, 
            ServerOverloadedException {
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_ADMIN_BY_EMAIL);
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
     * Get one admin by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("admin_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String password = rs.getString("password");
        AdminType adminType = AdminType.valueOf(rs.getString("admin_type"));
        BigDecimal account = rs.getBigDecimal("account");
        Admin newAdmin = new Admin(firstName, lastName, email, password, adminType);
        newAdmin.setAccount(account);
        newAdmin.setId(id);
        return newAdmin;
    }

    /**
     * Update admin' private information (first name, last name and email only)
     * @param newAdmin admin object with new information
     * @return boolean true if updating was successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean updateAdmin(Admin newAdmin) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_FOR_ADMIN_UPDATING)) {
                ps.setString(1, newAdmin.getFirstName());
                ps.setString(2, newAdmin.getLastName());
                ps.setString(3, newAdmin.getEmail());
                ps.setInt(4, newAdmin.getId());
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    }

    /**
     * Change admin password 
     * @param admin admin object (here admin id is required only)
     * @param newPassword new admin password
     * @return true if password changing is successful and false otherwise
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    public boolean changePassword(Admin admin, String newPassword) throws SQLException, 
            ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            try (PreparedStatement ps = wrapperConnection.
                    prepareStatement(SQL_TO_CHANGE_ADMIN_PASSWORD)) {
                ps.setString(1, newPassword);
                ps.setInt(2, admin.getId());
                ps.executeUpdate();
                flag = true;
            }
        } finally {
            if (wrapperConnection != null) {
                wrapperConnection.close();
            }
        }
        return flag;
    } 
    
}
