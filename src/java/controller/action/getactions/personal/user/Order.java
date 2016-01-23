/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.action.ConcreteLink;
import controller.action.getactions.personal.AbstractOrders;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.Admin;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Order extends AbstractOrders {

    /**
     * Show selected order
     * 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        Admin admin = (Admin) session.getAttribute("admin");
        if (user == null) {
            if (admin == null) {
                sendRedirect(null, "login.errormessage.loginplease", "home");
                return;
            }
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, null, "home");
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        model.entity.Order order = getOrderById(orderId);
        if (order == null) {
            sendRedirect(null, "order.errormessage.nosuchorder", "orders");
            return;
        }
        if (admin != null || checkUserValidation(user, order)) {
            request.setAttribute("order", order);
            request.setAttribute("items", order.getOrderItems());
            goToPage("order.text.title", "/view/person/user/order.jsp");
        } else {
            sendRedirect(null, "login.errormessage.loginplease", "home");
        }
    }

//    /**
//     * Get order by order id
//     * @param orderId order id
//     * @return order if it exists in the date base and false otherwise
//     * @throws ServletException
//     * @throws IOException 
//     */
//    private model.entity.Order getOrderById(int orderId) 
//            throws ServletException, IOException {
//        model.entity.Order order = null;
//        OrderCreator orderCreator = new OrderCreator();
//        try {
//            order = (model.entity.Order) orderCreator.getEntityById(orderId);
//            if (order == null) {
//                sendRedirect(null, "order.errormessage.nosuchorder", "orders");
//                return null;
//            }
//            return order;
//        } catch (SQLException e) {
//            sendRedirect(null, "exception.errormessage.sqlexception", "orders");
//            return null;
//        } catch (ServerOverloadedException ex) {
//            sendRedirect(null, "exception.errormessage.serveroverloaded", "orders");
//            return null;
//        }
//    }

    /**
     * Check validation of user according to this order
     * @param user user object
     * @param order order object
     * @return true is this order really belongs to this user and false otherwise
     */
    private boolean checkUserValidation(User user, model.entity.Order order) {
        int userId = user.getId();
        int orderUserId = order.getUserId();
        return userId == orderUserId;
    }
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    @Override
    public List<ConcreteLink> getLink() {
        return new Orders().getLink();
    }
    
}
