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
import model.entity.DBEntity;
import model.entity.Zone;
import model.entity.Zone.ZoneType;

/**
 *
 * @author Sasha
 */
public class ZoneCreator extends EntityCreator {
    
//    /** sql query for all zones */
//    private static final String SQL_FOR_ALL_ZONES = "SELECT * FROM zone";
//    
//    /** sql query to get zone by id */
//    private static final String SQL_FOR_ZONE_BY_ID = "SELECT * FROM zone WHERE zone_id = ?";

    /** sql value of zone table name */
    private final static String ZONE_TABLE = "zone";
    
    /** sql value of zone id name */
    private final static String ZONE_ID = "zone_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ZONE = "INSERT INTO zone (zone_type, admin_id, price) VALUES (?, ?, ?)";
    
//    /** sql query for deleting entity from the data base table */
//    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM zone WHERE zone_id = ?";

    /**
     * Constructor
     */
    public ZoneCreator() {
        super(ZONE_TABLE, ZONE_ID);
    }

//    /**
//     * Get zone by id
//     * @param wrapperConnection wrapper connection regarding to which prepared 
//     *  statement might be created
//     * @param id zone id
//     * @return prepared statement 
//     * @throws SQLException
//     */
//    @Override
//    protected DBEntity getEntityById(WrapperConnectionProxy wrapperConnection, 
//            int id) throws SQLException {
//        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ZONE_BY_ID);
//        ps.setInt(1, id);
//        ResultSet rs = ps.executeQuery();
//        if (rs.next()) {
//            return getZone(rs);
//        }
//        return null;
//    }

    /**
     * Insert zone into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity zone might be inserting into the data base
     * @throws SQLException 
     */
    protected void insertEntity(WrapperConnectionProxy wrapperConnection, 
            DBEntity entity) throws SQLException {
        Zone zone = (Zone) entity;
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ZONE);
        ps.setString(1, zone.getZoneType().toString());
        ps.setInt(2, zone.getAdminId());
        ps.setBigDecimal(3, zone.getPrice());
        ps.executeUpdate();
    }

//    /**
//     * Delete zone from the data base by id
//     * @param wrapperConnection wrapper conection 
//     * @param id zone id 
//     * @throws SQLException 
//     */
//    @Override
//    protected void deleteById(WrapperConnectionProxy wrapperConnection, int id) 
//            throws SQLException {
//        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
//        ps.setInt(1, id);
//        ps.executeUpdate();
//    }
    
    /**
     * Get one zone by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected Zone getEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("zone_id");
        ZoneType zoneType = ZoneType.valueOf(rs.getString("zone_type"));
        int adminId = rs.getInt("admin_id");
        BigDecimal price = rs.getBigDecimal("price");
        Zone newZone = new Zone(price, zoneType, adminId);
        newZone.setId(id);
        return newZone;
    }
    
}
