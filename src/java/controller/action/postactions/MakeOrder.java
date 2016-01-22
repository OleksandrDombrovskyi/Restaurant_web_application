/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.MealCreator;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Meal;
import model.entity.Order;
import model.entity.Order.OrderStatus;
import model.entity.OrderItem;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class MakeOrder extends PostAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        Timestamp date = new Timestamp(new Date().getTime());
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, "login.errormessage.loginplease", "home");
            return;
        }
        model.entity.Order newOrder = new model.entity.Order(user.getId(), OrderStatus.NOT_CONFIRMED, 
                BigDecimal.valueOf(0), date);
        insertMealsIntoOrder(newOrder);
        int orderId = insertNewOrder(newOrder);
        sendRedirect(null, null, "getOrder&orderId=" + orderId);
        
    }
    
    private List<Meal> getAllMeals() throws ServletException, IOException {
        MealCreator mealCreator = new MealCreator();
        try {
            return (List<Meal>) mealCreator.getAllEntities();
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "mainMenu");
        }
        return null;
    }

    /**
     * Get all existing meals from main manu (from data base) and add each of 
     * one was chosen by user
     * 
     * @param newOrder input all choden meal into this order
     * @throws ServletException
     * @throws IOException 
     */
    private void insertMealsIntoOrder(Order newOrder) throws ServletException, 
            IOException {
        List<Meal> allMeals = getAllMeals();
        if (allMeals == null) {
            return;
        }
        for (Meal meal : allMeals) {
            String mealIdName = String.valueOf(meal.getId());
            int mealAmount = Integer.parseInt(request.getParameter(mealIdName));
            if (mealAmount > 0) {
                BigDecimal totalPrice = meal.getPrice().multiply(BigDecimal.valueOf(mealAmount));
                OrderItem orderItem = new OrderItem(meal, mealAmount, totalPrice);
                newOrder.addOrderItem(orderItem);
                newOrder.setTotalPrice(newOrder.getTotalPrice().add(totalPrice));
            }
        }
    }

    /**
     * Insert new created order into data base basis by users' chose
     * @param newOrder
     * @return
     * @throws ServletException
     * @throws IOException 
     */
    private int insertNewOrder(Order newOrder) throws ServletException, 
            IOException {
        int orderId = 0;
        OrderCreator orderCreator = new OrderCreator();
        try {
            orderId = orderCreator.insertOrder(newOrder);
            if (orderId == 0) {
                sendRedirect(null, "order.errormessage.nosuchorder", "mainMenu");
            }
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "mainMenu");
        }
        return orderId;
    }
    
}
