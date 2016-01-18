/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import controller.action.ConcreteLink;
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
public class UserAccount extends GetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            goToHome("login.errormessage.loginplease");
            return;
        }
        BigDecimal account = user.getAccount();
        createPage(account);
    }

    private void createPage(BigDecimal account) throws ServletException, 
            IOException {
        request.setAttribute("title", "account.text.title");
        request.setAttribute("account", account);
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
        request.getRequestDispatcher("/view/user/account.jsp").
                include(request, response);
    }
    
    /**
     * Get all links before settings and aettings link inclusive
     * @return list of links objects
     */
    @Override
    public List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new Profile().getLink());
        String linkValue = "/servlet?getAction=account";
        String linkName = "account.text.title";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
