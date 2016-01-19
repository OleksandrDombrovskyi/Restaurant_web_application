/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.ConcreteLink;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class Basket extends GetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        int userId = user.getId();
        model.entity.Order basketOrder = null;
        OrderCreator orderCreator = new OrderCreator();
        try {
            basketOrder = orderCreator.getNotConfirmedOrder(userId);
        } catch (SQLException ex) {
            startOver("exception.errormessage.sqlexception");
            return;
        } catch (ServerOverloadedException ex) {
            startOver("exception.errormessage.serveroverloaded");
            return;
        }
        if (basketOrder == null) {
            request.setAttribute("message", "basket.message.emptybasket");
        }
        createPage(basketOrder);
    }
    
    private void createPage(Order basketOrder) throws ServletException, 
            IOException{
        request.setAttribute("title", "basket.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
        request.setAttribute("basketOrder", basketOrder);
        request.getRequestDispatcher("/view/user/basket.jsp").
                include(request, response);
    }
    
    /**
     * Back to filling the form couse of uncorrect field filling and sending 
     * correspond error message
     * 
     * @param errorMessage text value of text property file which corresponds 
     * to the error message
     * @throws ServletException
     * @throws IOException 
     */
    private void startOver(String errorMessage) throws ServletException, 
            IOException {
        request.setAttribute("errorMessage", errorMessage);
        session.setAttribute("lastPath", request.getContextPath() + "/servlet?getAction=profile");
        new Profile().execute(request, response);
    }
    
    /**
     * Get all links before settings and aettings link inclusive
     * @return list of links objects
     */
    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Profile().getLink());
        String linkValue = "/servlet?getAction=basket";
        String linkName = "basket.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
