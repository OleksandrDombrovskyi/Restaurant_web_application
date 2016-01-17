/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;

/**
 *
 * @author Sasha
 */
public class Tester {
    public static void main(String[] args) throws SQLException {
//        Timestamp date = new Timestamp(new Date().getTime());
//        Order order = new Order(1, OrderStatus.NOT_CONFIRMED, BigDecimal.TEN, date);
//        Meal meal1 = new Meal(BigDecimal.ONE, "Express Lunch", "mealName", "veryLongDescription", 10);
//        meal1.setId(3);
//        Meal meal2 = new Meal(BigDecimal.ONE, "Dessert", "mealName2", "veryLongDescription2", 102);
//        meal2.setId(2);
//        order.addOrderItem(
//                new OrderItem(meal1, 5, BigDecimal.ONE));
//        order.addOrderItem(
//                new OrderItem(meal2, 3, BigDecimal.ONE));
//        OrderCreator creator = new OrderCreator();
//        try {
//            creator.insertOrder(order);
//        } catch (SQLException e) {
//            System.out.println("SQL exception: " + e.getMessage());
//        } catch (ServerOverloadedException e) {
//            System.out.println("Server overloaded: " + e.getMessage());
//        }
//        Timestamp date = new Timestamp(new Date().getTime());
//        System.out.println("Time now: " + date);
//        OrderCreator creator = new OrderCreator();
//        MealCreator creator2 = new MealCreator();
//        try {
//            creator.getAllEntities();
//        } catch (SQLException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        OrderCreator orderCreator = new OrderCreator();
//        Order order = null;
//        try {
//            order = (Order) orderCreator.getEntityById(12);
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println("order items: " + order.getOrderItems());
//        MealCreator mealCreator = new MealCreator();
//        try {
//            System.out.println("Meals: " + (List<Meal>)mealCreator.getAllEntities());
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        }
        OrderCreator orderCreator = new OrderCreator();
//        try {
//            orderCreator.setStatus(20, Order.OrderStatus.CRETATED);
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        System.out.println(Order.OrderStatus.CRETATED.name());
    }
}
