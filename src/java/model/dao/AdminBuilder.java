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
import model.entity.Admin;
import model.entity.Admin.AdminType;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public class AdminBuilder extends EntityBuilder {
    
    /** sql query for all admins */
    private static final String SQL_FOR_ALL_ADMINS = "SELECT * FROM admin";
    
    /** sql query to get admin by id */
    private static final String SQL_FOR_ADMIN_BY_ID = "SELECT * FROM admin WHERE admin_id = ?";

    /** sql query for inserting admin into the admin table in the data base */
    private static final String SQL_FOR_ADMIN_INSERTING = "INSERT INTO admin (first_name, last_name, admin_type, email, login, password) VALUES (?, ?, ?, ?, ?, ?)";
    
    /** sql query for deleting admin from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM admin WHERE admin_id = ?";

    /**
     * Get sql query for all admins 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return list of admins
     * @throws SQLException
     */
    @Override
    protected List<DBEntity> getAllEntities(
            WrapperConnectionProxy wrapperConnection) throws SQLException {
        List<DBEntity> admins = new ArrayList<>();
        Statement st = wrapperConnection.createStatement();
        ResultSet rs = st.executeQuery(SQL_FOR_ALL_ADMINS);
        while (rs.next()) {
            admins.add(getAdmin(rs));
        }
        return admins;
    }

    /**
     * Get one admin by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    protected DBEntity getAdmin(ResultSet rs) throws SQLException {
        int id = rs.getInt("admin_id");
        String firstName = rs.getString("first_name");
        String lastName = rs.getString("last_name");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String password = rs.getString("password");
        AdminType adminType = AdminType.valueOf(rs.getString("admin_type"));
        return new Admin(id, firstName, lastName, email, adminType, login, password);
    }

    /**
     * Get admin by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id admin id
     * @return prepared statement 
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntityById(
            WrapperConnectionProxy wrapperConnection, int id) throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ADMIN_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getAdmin(rs);
        }
        return null;
    }

    /**
     * Insert entity into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity entity might be inserting into the data base
     * @throws SQLException 
     */
    @Override
    protected void insertEntity(
            WrapperConnectionProxy wrapperConnection, DBEntity entity) 
            throws SQLException {
        Admin admin = (Admin) entity;
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ADMIN_INSERTING);
        ps.setString(1, admin.getFirstName());
        ps.setString(2, admin.getLastName());
        ps.setString(3, admin.getAdminType().toString());
        ps.setString(4, admin.getEmail());
        ps.setString(5, admin.getLogin());
        ps.setString(6, admin.getPassword());
        ps.executeUpdate();
    }

    /**
     * Delete entity from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id entity id 
     * @throws SQLException 
     */
    @Override
    protected void deleteById(
            WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
}
