/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author Sasha
 */
public class Tester {
    public static void main(String[] args) {
//        Order order = new Order(1, OrderStatus.NOT_CONFIRMED, BigDecimal.TEN);
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
        Timestamp date = new Timestamp(new Date().getTime());
        System.out.println("Time now: " + date);
    }
}
