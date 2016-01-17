/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
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
public class MakeOrder extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToLogin();
            return;
        }
        List<Meal> allMeals = getAllMeals();
        if (allMeals == null) {
            return;
        }
        Order newOrder = new Order(user.getId(), OrderStatus.NOT_CONFIRMED, 
                BigDecimal.valueOf(0));
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
        try {
            orderCreator.insertOrder(newOrder);
        } catch (SQLException ex) {
            startOver("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            startOver("exception.errormessage.serveroverloaded");
            return;
        } 
        
        // order outout to the screen
        createPage(newOrder);
        
    }
    
    private List<Meal> getAllMeals() throws ServletException, IOException {
        MealCreator mealCreator = new MealCreator();
        try {
            return (List<Meal>) mealCreator.getAllEntities();
        } catch (SQLException ex) {
            startOver("exception.errormessage.sqlexception");
        } catch (ServerOverloadedException ex) {
            startOver("exception.errormessage.serveroverloaded");
        }
        return null;
    }
    
    private void createPage(Order order) throws ServletException, IOException {
        request.setAttribute("title", "order.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.setAttribute("order", order);
        request.setAttribute("items", order.getOrderItems());
        request.getRequestDispatcher("/view/user/order.jsp").
                include(request, response);
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param errorMessage text value of text property file which corresponds 
     * to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        new MainMenu().execute(request, response);
//        response.sendRedirect(request.getContextPath() 
//                + "/servlet?action=mainMenu");
    }
    
    private void goToLogin() throws IOException {
        request.setAttribute("errorMessage", "login.errormessage.loginplease");
        response.sendRedirect(request.getContextPath() + "?action=loginRequest");
    }
    
}
