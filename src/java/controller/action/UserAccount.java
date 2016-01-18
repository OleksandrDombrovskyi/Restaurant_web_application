/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import model.entity.User;

/**
 *
 * @author Sasha
 */
public class UserAccount extends Action {

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
        request.getRequestDispatcher("/view/user/account.jsp").
                include(request, response);
    }
    
}
