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
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class MealBuilder extends EntityBuilder {
    
    /** sql query for all meals */
    private static final String SQL_FOR_ALL_MEALS = "SELECT * FROM main_menu";
    
    /** sql query to get entity by id */
    private static final String SQL_FOR_ENTITY_BY_ID = "SELECT * FROM main_menu WHERE meal_id = ?";

    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ENTITY = "INSERT INTO main_menu (meal_type, meal_name, meal_description, meal_price) VALUES (?, ?, ?, ?)";
    
    /** sql query for deleting entity from the data base table */
    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM main_menu WHERE meal_id = ?";
    
    /**
     * Get sql query for all meals 
     * @param wrapperConnection wrapper connection from the connection pool
     * @return list of meals
     * @throws SQLException
     */
    @Override
    protected List<DBEntity> getAllEntities(
            WrapperConnectionProxy wrapperConnection) throws SQLException {
        List<DBEntity> meals = new ArrayList<>();
        Statement st = wrapperConnection.createStatement();
        ResultSet rs = st.executeQuery(SQL_FOR_ALL_MEALS);
        while (rs.next()) {
            meals.add(getMeal(rs));
        }
        return meals;
    }

    /**
     * Get one meal by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    protected DBEntity getMeal(ResultSet rs) throws SQLException {
        int id = rs.getInt("meal_id");
        String type = rs.getString("meal_type");
        String name = rs.getString("meal_name");
        String description = rs.getString("meal_description");
        BigDecimal price = rs.getBigDecimal("meal_price");
        return new Meal(id, price, type, name, description, 10); // hard code
    }

    /**
     * Get meal by id
     * @param wrapperConnection wrapper connection regarding to which prepared 
     *  statement might be created
     * @param id meal id
     * @return DBEntity meal or null if it won't be found
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntityById(
            WrapperConnectionProxy wrapperConnection, int id) 
            throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ENTITY_BY_ID);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return getMeal(rs);
        }
        return null;
    }

    /**
     * Insert meal into the data base from the object oriented entity
     * @param wrapperConnection wrapper connection from the connection pool
     * @param entity entity might be inserting into the data base
     * @throws SQLException 
     */
    @Override
    protected void insertEntity(
            WrapperConnectionProxy wrapperConnection, DBEntity entity) throws SQLException {
        Meal meal = (Meal) entity;
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ENTITY);
        ps.setString(1, meal.getTypeString());
        ps.setString(2, meal.getName());
        ps.setString(3, meal.getDescription());
        ps.setBigDecimal(4, meal.getPrice());
        ps.executeUpdate();
    }

    /**
     * Delete meal from the data base by id
     * @param wrapperConnection wrapper conection 
     * @param id meal id 
     * @throws SQLException 
     */
    @Override
    protected void deleteById(
            WrapperConnectionProxy wrapperConnection, int id) throws SQLException {
        PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_DELETING_BY_ID);
        ps.setInt(1, id);
        ps.executeUpdate();
    }
    
}
