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
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class MealCreator extends EntityCreator {
    
//    /** sql query for all meals */
//    private static final String SQL_FOR_ALL_MEALS = "SELECT * FROM main_menu";
//    
//    /** sql query to get entity by id */
//    private static final String SQL_FOR_ENTITY_BY_ID = "SELECT * FROM main_menu WHERE meal_id = ?";

    /** sql value of meal table name */
    private final static String MEAL_TABLE = "main_menu";
    
    /** sql value of meal id name */
    private final static String MEAL_ID = "meal_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    private static final String SQL_FOR_INSERTING_ENTITY = "INSERT INTO main_menu (meal_type, meal_name, meal_description, meal_price) VALUES (?, ?, ?, ?)";

    /**
     * Constructor
     */
    public MealCreator() {
        super(MEAL_TABLE, MEAL_ID);
    }
    
//    /** sql query for deleting entity from the data base table */
//    private static final String SQL_FOR_DELETING_BY_ID = "DELETE FROM main_menu WHERE meal_id = ?";
    
//    /**
//     * Get all meals from the db
//     * 
//     * @return list of meals
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public List<DBEntity> getAllEntities() throws SQLException, ServerOverloadedException {
//        List<DBEntity> meals = new ArrayList<>();
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            Statement st = wrapperConnection.createStatement();
//            ResultSet rs = st.executeQuery(SQL_FOR_ALL_MEALS);
//            while (rs.next()) {
//                meals.add(getMeal(rs));
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return meals;
//    }

//    /**
//     * Get meal by id
//     * 
//     * @param id id of meal
//     * @return data base meal
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public DBEntity getAdminById(int id) throws SQLException, ServerOverloadedException {
//        WrapperConnectionProxy wrapperConnection = null;
//        try {
//            wrapperConnection = CONNECTION_POOL.getConnection();
//            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_ENTITY_BY_ID);
//            ps.setInt(1, id);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return getMeal(rs);
//            }
//        } finally {
//            if (wrapperConnection != null) {
//                wrapperConnection.close();
//            }
//        }
//        return null;
//    }

    /**
     * Create new meal
     * @param meal meal migth be updated into the data base
     * @return true if updating is successfull and false otherwise
     * @throws java.sql.SQLException
     * @throws model.dao.ServerOverloadedException
     */
    public boolean insertMeal(Meal meal) throws SQLException, ServerOverloadedException {
        boolean flag = false;
        WrapperConnectionProxy wrapperConnection = null;
        try {
            wrapperConnection = CONNECTION_POOL.getConnection();
            PreparedStatement ps = wrapperConnection.prepareStatement(SQL_FOR_INSERTING_ENTITY);
            ps.setString(1, meal.getTypeString());
            ps.setString(2, meal.getName());
            ps.setString(3, meal.getDescription());
            ps.setBigDecimal(4, meal.getPrice());
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
//     * Delete meal from the data base by id
//     * 
//     * @param id meal id
//     * @return true if deleting is successfull or false otherwise
//     * @throws java.sql.SQLException
//     * @throws model.dao.ServerOverloadedException
//     */
//    public boolean deleteById(int id) throws SQLException, ServerOverloadedException {
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
     * Get one meal by result set
     * @param rs result set of sql query
     * @return DBEntity object
     * @throws SQLException
     */
    @Override
    protected DBEntity getEntity(ResultSet rs) throws SQLException {
        int id = rs.getInt("meal_id");
        String type = rs.getString("meal_type");
        String name = rs.getString("meal_name");
        String description = rs.getString("meal_description");
        BigDecimal price = rs.getBigDecimal("meal_price");
        Meal newMeal = new Meal(price, type, name, description, 10); // hard code
        newMeal.setId(id);
        return newMeal;
    }
    
}
