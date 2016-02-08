/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions.personal.user;

import controller.action.postactions.PostAction;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import model.dao.DaoEnum;
import model.dao.OrderCreator;
import model.dao.ServerOverloadedException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class BasketConfirmation extends PostAction {

    /**
     * Confirm order in the basket
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (!userValidation(user)) {
            sendRedirect(null, LOGIN_PLEASE, HOME_PAGE_LINK);
            return;
        }
        int userId = user.getId();
        if (!confirmBasket(userId)) {
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, NO_SUCH_ORDER, BASKET);
        } else {
            sendRedirectWithParam("link.getorder", "orderId", orderIdString);
        }
    }

    /**
     * Valid current user
     * @param user user object
     * @return true if user is valid and false if user is invalid
     */
    private boolean userValidation(User user) {
        if (user == null) {
            return false;
        }
        String userIdString = request.getParameter("userId");
        if (userIdString == null) {
            return false;
        }
        int userId = Integer.parseInt(userIdString);
        if (userId != user.getId()) {
            return false;
        }
        return true;
    }
    
    /**
     * Confirm order in the basket (set status CREATED instead NOT_CONFIRMED)
     * @param userId user id whose basket order is it
     * @return true if confirmation was performed 
     * @throws ServletException
     * @throws IOException 
     */
    private boolean confirmBasket(int userId) throws ServletException, 
            IOException {
        OrderCreator orderCreator = 
                (OrderCreator) daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            if (orderCreator.confirmBasket(userId) == 0) {
                sendRedirect("basket.message.notconfirmed", null, BASKET);
                return false;
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, BASKET);
            return false;
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, 
                    BASKET);
            return false;
        }
        return true;
    }
    
}
