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
import model.entity.Zone;
import model.entity.Zone.ZoneType;

/**
 *
 * @author Sasha
 */
public class ZoneBuilder extends EntityBuilder {
    
    /** sql query for all zones */
    private static final String SQL_FOR_ALL_ZONES = "SELECT * FROM zone";
    
    /** sql query to get zone by id */
    private static final String SQL_FOR_ZONE_BY_ID = "SELECT * FROM zone WHERE zone_id = ?";

    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ZONE = "INSERT INTO zone (zone_type, admin_id, price) VALUES (?, ?, ?)";
    
    /** sql query for deleting entity from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM zone WHERE zone_id = ?";

    /**
     * Get sql query for all zones 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return list of zones
     * @throws SQLException
     */
    @Override
    protected List<DBEntity> getAllEntities(WrapperConnectionProxy wrapperConnection) 
            throws SQLException {
        List<DBEntity> zones = new ArrayList<>();
        Statement st = wrapperConnection.createStatement();
        ResultSet rs = st.executeQuery(SQL_FOR_ALL_ZONES);
        while (rs.next()) {
            zones.add(getZone(rs));
        }
        return zones;
    }
    
    /**
     * Get one zone by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    private Zone getZone(ResultSet rs) throws SQLException {
        int id = rs.getInt("zone_id");
        ZoneType zoneType = ZoneType.valueOf(rs.getString("zone_type"));
        int adminId = rs.getInt("admin_id");
        BigDecimal price = rs.getBigDecimal("price");
        return new Zone(id, price, zoneType, adminId);
    }

    /**
     * Get zone by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id zone id
     * @return prepared statement 
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntityById(WrapperConnectionProxy wrapperConnection, 
            int id) throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ZONE_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getZone(rs);
        }
        return null;
    }

    /**
     * Insert zone into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity zone might be inserting into the data base
     * @throws SQLException 
     */
    @Override
    protected void insertEntity(WrapperConnectionProxy wrapperConnection, 
            DBEntity entity) throws SQLException {
        Zone zone = (Zone) entity;
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ZONE);
        ps.setString(1, zone.getZoneType().toString());
        ps.setInt(2, zone.getAdminId());
        ps.setBigDecimal(3, zone.getPrice());
        ps.executeUpdate();
    }

    /**
     * Delete zone from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id zone id 
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
