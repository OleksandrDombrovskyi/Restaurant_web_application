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
            goToHome("login.errormessage.loginplease");
            return;
        }
        model.entity.Order newOrder = new model.entity.Order(user.getId(), OrderStatus.NOT_CONFIRMED, 
                BigDecimal.valueOf(0), date);
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
        OrderCreator orderCreator = new OrderCreator();
        int orderId = 0;
        try {
            orderId = orderCreator.insertOrder(newOrder);
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
            return;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "mainMenu");
            return;
        } 
        if (orderId == 0) {
            sendRedirect(null, "order.errormessage.nosuchorder", "mainMenu");
            return;
        }
        sendRedirect(null, null, "getOrder&orderId=" + orderId);
//        makeRedirect(orderId);
        
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
    
//    private void makeRedirect(int orderId) throws ServletException, IOException {
//        response.sendRedirect(request.getContextPath() + 
//                "/servlet?getAction=getOrder&orderId=" + orderId);
//    } 
    
//    /**
//     * Back to filling the form couse of uncorrect field filling and sending 
//     * correspond error message
//     * 
//     * @param errorMessage text value of text property file which corresponds 
//     * to the error message
//     * @throws ServletException
//     * @throws IOException 
//     */
//    private void startOver(String errorMessage) throws ServletException, 
//            IOException {
//        session.setAttribute("errorMessage", errorMessage);
////        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=mainMenu");
////        new MainMenu().execute(request, response);
//        response.sendRedirect(request.getContextPath() + 
//                "/servlet?getAction=mainMenu");
//        
//    }
    
}
