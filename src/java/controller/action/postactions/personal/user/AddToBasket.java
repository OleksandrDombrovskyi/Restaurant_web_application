/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.ConfigManager;
import controller.action.postactions.PostAction;
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
 * Adding new items to basket
 * @author Sasha
 */
public class AddToBasket extends PostAction {

    /**
     * Put meals into the basket. If basket has been created before, just put
     * new meal has been chosen by user. If basket has not been created yet, 
     * create basket order (with NOT_CONFIRMED status) basis on the user choise
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null || user.getId() == 0) {
            sendRedirect(null, "login.errormessage.loginplease", "link.home");
            return;
//            setMessages(null, "login.errormessage.loginplease");
//            return ConfigManager.getProperty("path.home");
        }
        int userId = user.getId();
        Order newBasketOrder = new Order(userId, 
                Order.OrderStatus.NOT_CONFIRMED, BigDecimal.valueOf(0), 
                new Timestamp(new Date().getTime()));
        if (!insertOrderItems(newBasketOrder)) {
            sendRedirect(null, "mainmenu.errormessage.nomeals", "link.mainmenu");
            return;
        }
        if (newBasketOrder.getOrderItems().size() < 1) {
            sendRedirect("mainmenu.message.noselectedmeals", null, "link.mainmenu");
            return;
        }
        Order basketOrder = getBasketOrder(userId);
        if (basketOrder == null) {
            createNewBasket(newBasketOrder);
        } else {
            if (addItemsToBasket(basketOrder, newBasketOrder)) {
                sendRedirect(null, null, "link.basket");
//                return ConfigManager.getProperty("path.page.user.basket");
            }
        }
//        return null;
    }
    
    /**
     * Get all meal from the data base in the main manu
     * 
     * @return array list of the menu meal
     * @throws ServletException
     * @throws IOException 
     */
    private List<Meal> getAllMeals() throws ServletException, IOException {
        MealCreator mealCreator = new MealCreator();
        try {
            return (List<Meal>) mealCreator.getAllEntities();
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.mainmenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.mainmenu");
        }
        return null;
    }
    
    /**
     * Insert chose order items by user into the new basket order has been 
     * created in the previous method
     * 
     * @param newBasketOrder
     * @return
     * @throws ServletException
     * @throws IOException 
     */
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
    
    /**
     * Create new basket order (with NOT_CONFIRMED status) basis on the current 
     * user choise from main manu
     * 
     * @param newBasketOrder order with items user has been chosen
     * @throws ServletException
     * @throws IOException
     * @throws SQLException
     * @throws ServerOverloadedException 
     */
    private void createNewBasket(Order newBasketOrder) throws ServletException, 
            IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            int orderId = orderCreator.insertOrder(newBasketOrder);
            if (orderId == 0) {
                sendRedirect(null, "order.errormessage.nosuchorder", "link.mainmenu");
            } else {
                sendRedirect(null, null, "link.basket");
            }
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.mainmenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.mainmenu");
        }
    }

    /**
     * Get basket order (order with status NOT_CONFIRMED)
     * @param userId user id
     * @return basket order
     * @throws ServletException
     * @throws IOException 
     */
    private Order getBasketOrder(int userId) throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return orderCreator.getNotConfirmedOrder(userId);
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.mainmenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.mainmenu");
        }
        return null;
    }

    /**
     * Add items to basket
     * @param basketOrder basket order was created by the current user before
     * @param newBasketOrder new order with items are creating by user now
     * @return true if items added successfuly or false otherwise
     * @throws ServletException
     * @throws IOException 
     */
    private boolean addItemsToBasket(Order basketOrder, Order newBasketOrder) 
            throws ServletException, IOException {
        OrderCreator orderCreator = new OrderCreator();
        try {
            return orderCreator.addItemsToBasket(basketOrder, newBasketOrder);
        } catch (SQLException ex) {
            sendRedirect(null, "exception.errormessage.sqlexception", "link.mainmenu");
        } catch (ServerOverloadedException ex) {
            sendRedirect(null, "exception.errormessage.serveroverloaded", "link.mainmenu");
        }
        return false;
    }
    
}
