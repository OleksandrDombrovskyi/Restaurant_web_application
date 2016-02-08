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
public class ClearBasket extends PostAction {

    /**
     * Clear basket (remove basket order, with status NOT_CONFIRMED)
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            sendRedirect(null, LOGIN_PLEASE, HOME_PAGE_LINK);
            return;
        }
        String orderIdString = request.getParameter("orderId");
        if (orderIdString == null) {
            sendRedirect(null, NO_SUCH_ORDER, BASKET);
            return;
        }
        int orderId = Integer.parseInt(orderIdString);
        if (isRemoved(orderId)) {
            sendRedirect(null, null, BASKET);
        } else {
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, BASKET);
        }
    }
    
    /**
     * Was basket order removed ot not
     * @param orderId basket order id
     * @return true is basket order was removed and false otherwise
     * @throws ServletException
     * @throws IOException 
     */
    private boolean isRemoved(int orderId) throws ServletException, IOException {
        OrderCreator orderCreator = 
                (OrderCreator) daoFactory.getCreator(DaoEnum.ORDER_CREATOR);
        try {
            if (orderCreator.removeOrder(orderId)) {
                return true;
            } else {
                sendRedirect(null, SQL_EXCEPTION, BASKET);
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SQL_EXCEPTION, BASKET);
        } catch (ServerOverloadedException e) {
            logger.info(e.getMessage());
            sendRedirect(null, SERVER_OVERLOADED_EXCEPTION, BASKET);
        }
        return false;
    }
    
}
