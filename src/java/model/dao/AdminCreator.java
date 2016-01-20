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
import static model.dao.EntityCreator.CONNECTION_POOL;
import model.entity.Admin;
import model.entity.Admin.AdminType;
import model.entity.DBEntity;

/**
 *
 * @author Sasha
 */
public class AdminCreator extends EntityCreator {
    
//    /** sql query for all admins */
//    private static final String SQL_FOR_ALL_ADMINS = "SELECT * FROM admin";
//    
//    /** sql query to get admin by id */
//    private static final String SQL_FOR_ADMIN_BY_ID = "SELECT * FROM admin WHERE admin_id = ?";

    /** sql value of admin table name */
    private final static String ADMIN_TABLE = "admin";
    
    /** sql value of admin id name */
    private final static String ADMIN_ID = "admin_id";
    
    /** sql query for inserting admin into the admin table in the data base */
    private static final String SQL_FOR_ADMIN_INSERTING = "INSERT INTO admin (first_name, last_name, email, password, admin_type) VALUES (?, ?, ?, ?, ?)";
    
//    /** sql query for deleting admin from the data base table */
//    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM admin WHERE admin_id = ?";
    
    /** sql query for getting the admin from data base by his email */
    private static final String SQL_FOR_ADMIN_BY_EMAIL = 
            "SELECT * FROM restaurantdatabase.admin WHERE email = ?";
    
    /**
     * Constructor 
     */
    public AdminCreator() {
        super(ADMIN_TABLE, ADMIN_ID);
    }

//    /**
//     * Get all admins from db
//     * 
//     * @return list of data base admins
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public List<DBEntity> getAllAdmin() throws SQLException, ServerOverloadedException {
//        List<DBEntity> admins = new ArrayList<>();
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            Statement st = wrapperConnection.createStatement();
//            ResultSet rs = st.executeQuery(SQL_FOR_ALL_ADMINS);
//            while (rs.next()) {
//                admins.add(getAdmin(rs));
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return admins;
//    }

//    /**
//     * Get admin by id
//     * 
//     * @param id id of admin
//     * @return data base admin
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public DBEntity getAdminById(int id) throws SQLException, ServerOverloadedException {
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ADMIN_BY_ID);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return getAdmin(rs);
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return null;
//    }

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

//    /**
//     * Delete admin from the data base by id
//     * 
//     * @param id admin id
//     * @return true if deleting is successfull or false otherwise
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public boolean deleteAdminById(int id) throws SQLException, ServerOverloadedException {
//        boolean flag = false;
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
//            ps.setInt(1, id);
//            ps.executeUpdate();
//            flag = true;
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return flag;
//    }
    
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
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ADMIN_BY_EMAIL);
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
        Admin newAdmin = new Admin(firstName, lastName, email, password, adminType);
        newAdmin.setId(id);
        return newAdmin;
    }
    
}
