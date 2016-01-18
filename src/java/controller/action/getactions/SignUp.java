/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class SignUp extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        request.setAttribute("title", "signup.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        request.getRequestDispatcher("/view/signup.jsp").
                include(request, response);
    }
    
}
