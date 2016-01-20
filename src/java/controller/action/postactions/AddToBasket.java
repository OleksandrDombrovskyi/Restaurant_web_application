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
import model.entity.OrderItem;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class AddToBasket extends PostAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == 0) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        int userId = user.getId();
        Order newBasketOrder = new Order(userId, 
                Order.OrderStatus.NOT_CONFIRMED, BigDecimal.valueOf(0), 
                new Timestamp(new Date().getTime()));
        if (!insertOrderItems(newBasketOrder)) {
            sendRedirect(null, "mainmenu.errormessage.nomeals", "mainMenu");
            return;
        }
        if (newBasketOrder.getOrderItems().size() < 1) {
            sendRedirect("mainmenu.message.noselectedmeals", null, "mainMenu");
            return;
        }
        OrderCreator orderCreator = new OrderCreator();
        Order basketOrder = null;
        try {
            basketOrder = orderCreator.getNotConfirmedOrder(userId);
            if (basketOrder == null) {
                createNewBasket(newBasketOrder);
                return;
            }
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
            return;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "mainMenu");
            return;
        }
        try {
            if (!orderCreator.addItemsToBasket(basketOrder, newBasketOrder)) {
                sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
            }
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "mainMenu");
            return;
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "mainMenu");
            return;
        }
        sendRedirect(null, null, "basket");
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
    
    private boolean insertOrderItems(Order newBasketOrder) 
            throws ServletException, IOException {
        List<Meal> allMeals = getAllMeals();
        if (allMeals == null) {
            return false;
        }
        for (Meal meal : allMeals) {
            String mealIdName = String.valueOf(meal.getId());
            int mealAmount = Integer.parseInt(request.getParameter(mealIdName));
            if (mealAmount > 0) {
                BigDecimal totalPrice = meal.getPrice().
                        multiply(BigDecimal.valueOf(mealAmount));
                OrderItem orderItem = 
                        new OrderItem(meal, mealAmount, totalPrice);
                newBasketOrder.addOrderItem(orderItem);
                newBasketOrder.setTotalPrice(newBasketOrder.
                        getTotalPrice().add(totalPrice));
            }
        }
        return true;
    } 
    
    private void createNewBasket(Order newBasketOrder) throws ServletException, 
            IOException, SQLException, ServerOverloadedException {
        OrderCreator orderCreator = new OrderCreator();
        int orderId = orderCreator.insertOrder(newBasketOrder);
        if (orderId == 0) {
            sendRedirect(null, "order.errormessage.nosuchorder", "mainMenu");
        } else {
            sendRedirect(null, null, "basket");
        }
    }
    
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
//        response.sendRedirect(request.getContextPath() 
//                + "/servlet?getAction=mainMenu");
//        sendRedirect(null, null, "mainMenu");
//    }
    
//    private void makeRedirect() throws ServletException, IOException {
//        response.sendRedirect(request.getContextPath() + "/servlet?getAction=basket");
//    }
    
}
