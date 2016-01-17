/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.Order;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class Orders extends Action {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            new Redirection().goToLogin(request, response);
            return;
        }
        int userId = user.getId();
        OrderCreator orderCreator = new OrderCreator();
        List<Order> orders = null;
        try {
            orders = (List<Order>) orderCreator.getOrdersByUserId(userId);
        } catch (SQLException e) {
            startOver("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException e) {
            startOver("exception.errormessage.serveroverloaded");
            return;
        }
        if (orders == null || orders.size() < 1) {
            request.setAttribute("message", "orders.text.noorders");
        }
        createPage(orders);
    }
    
    private void createPage(List<Order> orders) throws ServletException, IOException {
        request.setAttribute("title", "orders.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/view/user/orders.jsp").
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
        new Profile().execute(request, response);
    }
    
}
