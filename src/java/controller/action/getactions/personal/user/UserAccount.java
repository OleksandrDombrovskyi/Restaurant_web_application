/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions.personal.user;

import controller.ConfigManager;
import controller.action.getactions.personal.Profile;
import controller.action.ConcreteLink;
import controller.action.getactions.GetAction;
import controller.action.getactions.MainPartAction;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserAccount extends MainPartAction {

    /**
     * Show user accaunt balance
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected String doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
//            sendRedirect(null, "login.errormessage.loginplease", "home");
            setMessages(null, "login.errormessage.loginplease");
            return ConfigManager.getProperty("path.page.home");
        }
        BigDecimal account = user.getAccount();
        request.setAttribute("account", account);
//        goToPage("account.text.title", "/view/person/user/account.jsp");
        return ConfigManager.getProperty("path.page.user.account");
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
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Profile().getLink());
        String linkValue = ConfigManager.getProperty("link.account");
        String linkName = "account.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
