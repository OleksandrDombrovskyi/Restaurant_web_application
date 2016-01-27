/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

/**
 *
 * @author Sasha
 */
public interface DAO {
    
    /** connection pool object */
    ConnectionPool CONNECTION_POOL = 
            new ConnectionPool();
    
    /** sql query for admin account updating */
    String SQL_FOR_ADMIN_ACCOUNT_APDATING = "UPDATE restaurantdatabase.admin "
            + "SET account = ? WHERE admin_id = ?";
    
    /** sql query for user account updating */
    String SQL_FOR_USER_ACCOUNT_UPDATING = "UPDATE restaurantdatabase.user "
            + "SET account = ? WHERE user_id = ?";
    
    /** sql query for getting the user from data base by his email */ 
    String SQL_FOR_USER_BY_EMAIL = "SELECT * FROM restaurantdatabase.user "
            + "WHERE email = ?";
    
    /** sql query for getting the admin from data base by his email */ 
    String SQL_FOR_ADMIN_BY_EMAIL = "SELECT * FROM restaurantdatabase.admin "
            + "WHERE email = ?";
    
    /** sql for selecting order by id */
    String SQL_FOR_ORDER_BY_ID = "SELECT * FROM restaurantdatabase.order "
            + "WHERE order_id = ?";
    
    /** set required order status */
    String SQL_FOR_ORDER_STATUS_SETTING = "UPDATE restaurantdatabase.order "
            + "SET status = ? WHERE order_id = ?";
    
    /** sql value of admin table name */
    String ADMIN_TABLE = "admin";
    
    /** sql value of admin id name */
    String ADMIN_ID = "admin_id";
    
    /** sql query for inserting admin into the admin table in the data base */
    String SQL_FOR_ADMIN_INSERTING = "INSERT INTO admin (first_name, last_name, "
            + "email, password, admin_type) VALUES (?, ?, ?, ?, ?)";
    
    /** sql query for updating admin in the data base */
    String SQL_FOR_ADMIN_UPDATING = "UPDATE restaurantdatabase.admin "
            + "SET first_name = ?, last_name = ?, email = ? WHERE admin_id = ?";
    
    /** sql query for updating user in the data base */
    String SQL_TO_CHANGE_ADMIN_PASSWORD = "UPDATE restaurantdatabase.admin "
            + "SET password = ? WHERE admin_id = ?";
    
    /** sql value of order table name */
   String KITCHEN_TABLE = "kitchen";
    
    /** sql value of order id name */
    String KITCHEN_ID = "kitchen_id";
    
    /** sql to get kitchen by email */
    String SQL_FOR_KITCHEN_BY_EMAIL = "SELECT * FROM kitchen "
            + "WHERE email = ?";
    
    /** sql value of meal table name */
    String MEAL_TABLE = "main_menu";
    
    /** sql value of meal id name */
    String MEAL_ID = "meal_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    String SQL_FOR_INSERTING_ENTITY = "INSERT INTO main_menu "
            + "(meal_type, meal_name, meal_description, meal_price) "
            + "VALUES (?, ?, ?, ?)";
    
    /** sql value of order table name */
    String ORDER_TABLE = "order";
    
    /** sql value of order id name */
    String ORDER_ID = "order_id";
    
    /** sql query for inserting meal into the main menu table in the data base */
    String SQL_FOR_INSERTING_ORDER =  "INSERT INTO restaurantdatabase.order "
            + "(user_id, total_price, status, date) VALUES (?, ?, ?, ?)";
    
    /** sql query for order items inserting */
    String SQL_FOR_ITEMS_INSERTING = "INSERT INTO order_items "
            + "(order_id, meal_id, number, price) VALUES (?, ?, ?, ?)";
    
    /** sql query for order items updating */
    String SQL_FOR_ITEMS_UPDATING = "UPDATE restaurantdatabase.order_items "
            + "SET number = ?, price = ? WHERE order_id = ? AND meal_id = ?";
    
    /** sql query to get order item by id */
    String SQL_GET_ITEM_BY_ID = "SELECT * FROM restaurantdatabase.order_items "
            + "WHERE order_id = ?";
    
    /** sql query to set order status */
    String SQL_SET_ORDER_STATUS =  "UPDATE restaurantdatabase.order "
            + "SET status = ? WHERE order_id = ?";
    
    /** sql query to remove order from data base */ 
    String SQL_FOR_ORDER_DELETING = "DELETE FROM restaurantdatabase.order "
            + "WHERE order_id = ?";
    
    /** sql query to remove items from data base by order id */
    String SQL_FOR_ITEMS_DELETING = "DELETE FROM restaurantdatabase.order_items "
            + "WHERE order_id = ?";
    
    /** sql query for all users' orders except that has status NOT_CONFIRMED */
    String SQL_FOR_ORDERS_BY_USER_ID = "SELECT * FROM restaurantdatabase.order "
            + "WHERE user_id = ? AND status <> 'NOT_CONFIRMED'";
    
    /** sql query for getting not confirmed order that is uders' basket */
    String SQL_FOR_GETTING_BASKET_ORDER = "SELECT * FROM restaurantdatabase.order "
            + "WHERE user_id = ? AND status = ?";
    
    /** sql query to set status CREATED and set current time to new order */
    String SQL_FOR_BASKET_CONFIRMATION = "UPDATE restaurantdatabase.order "
            + "SET status = ?, date = ? WHERE user_id = ? AND status = ?";
    
    /** sql query for order price updating according to all its' items */
    String SQL_FOR_PRICE_UPDATING = "UPDATE restaurantdatabase.order "
            + "SET total_price = "
            + "(SELECT SUM(price) FROM restaurantdatabase.order_items WHERE order_id = ?) "
            + "WHERE order_id = ?";
    
    /** sql for getting orders by status */
    String SQL_FOR_ORDERS_BY_STATUS = "SELECT * FROM restaurantdatabase.order "
            + "WHERE status = ?";
    
    /** sql value of user table name */
    String USER_TABLE = "user";
    
    /** sql value of user id name */
    String USER_ID = "user_id";
    
    /** sql query for inserting user into the user table in the data base */
    String SQL_FOR_USER_INSERTING = "INSERT INTO user "
            + "(first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

    /** sql query for user updating */
    String SQL_FOR_USER_UPDATING = "UPDATE restaurantdatabase.user "
            + "SET first_name = ?, last_name = ?, email = ? WHERE user_id = ?";
    
    /** sql query for updating user in the data base */
    String SQL_TO_CHANGE_PASSWORD = "UPDATE restaurantdatabase.user "
            + "SET password = ? WHERE user_id = ?";
    
}
