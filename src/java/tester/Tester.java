/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.dao.SingletonPaymentTransaction;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Sasha
 */
public class Tester {
    public static void main(String[] args) {
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
//        Order order;
//        KitchenCreator kitchenCreator = new KitchenCreator();
//        Kitchen kitchen = null;
//        try {
//            kitchen = (Kitchen) kitchenCreator.getKitchenByEmail("kit@gmail.com");
//            System.out.println("password: " + kitchen.getPassword());
////            orderCreator.setOrderStatus(12, OrderStatus.ACCEPTED);
//            
////            System.out.println("order itdem1: " + order.getOrderItems().get(0));
////            System.out.println("order itdem2: " + order.getOrderItems().get(1));
////            System.out.println("basket confirmation, no basket: " + orderCreator.confirmBasket(2));
////            System.out.println("basket confirmation, yes basket: " + orderCreator.confirmBasket(1));
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SQLException e) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, e);
//        }

//        User user = new User("Vasia", "Vasylenko", "vasvas10@gmail.com", "VasVas10");
//        Order order = new Order(1, OrderStatus.READY, BigDecimal.valueOf(129.25), new Timestamp(new Date().getDate()));
//        order.setId(48);
//        SingletonPaymentTransaction engine = 
//                SingletonPaymentTransaction.getInstance();
//        try {
//            engine.makePayment(user, order);
//        } catch (SQLException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (ServerOverloadedException ex) {
//            Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
//        }

//        BigDecimal userAccount = BigDecimal.ZERO;
//        BigDecimal adminAccount = BigDecimal.ZERO;
//        BigDecimal orderPrice = BigDecimal.ZERO;
//        BigDecimal userAccountResult = BigDecimal.ZERO;
//        BigDecimal adminAccountResult = BigDecimal.ZERO;
//        if (userAccount.compareTo(orderPrice) > 0) {
//            userAccountResult = userAccount.subtract(orderPrice);
//            adminAccountResult = adminAccount.add(orderPrice);
//        }
//        String password = "1234560";
//
//        System.out.println(DigestUtils.shaHex("123456Аб"));
//        System.out.println(DigestUtils.shaHex(DigestUtils.shaHex(password)));
    }
}
