/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.math.BigDecimal;

/**
 *
 * @author Sasha
 */
public class Zone extends Service {
    
    /** zone type */
    private ZoneType zoneType;
    
    /** admin id */
    private int adminId;

    /**
     * Constructor
     * @param id user zone id
     * @param zoneType zone type
     * @param adminId admin id
     * @param price zone price
     */
    public Zone(int id, BigDecimal price, ZoneType zoneType, int adminId) {
        super(id, price);
        this.zoneType = zoneType;
        this.adminId = adminId;
    }
    
    /**
     * Set zone type
     * @param zoneType zone type
     */
    public void setZoneType(ZoneType zoneType) {
        this.zoneType = zoneType;
    }

    /**
     * Get zone type
     * @return zone type
     */
    public ZoneType getZoneType() {
        return zoneType;
    }
    
    /**
     * Set admin id
     * @param adminId int admin id
     */
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    /**
     * Get admin id
     * @return int admin id
     */
    public int getAdminId() {
        return adminId;
    }
    
    /**
     * Zone type
     */
    public enum ZoneType {
        VIP, MIDDLE, MARGINAL
    }
    
}
