/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class ChangeLanguage extends Action {

    @Override
    public void doExecute() throws ServletException, IOException {
        session.setAttribute("language", request.getParameter("language"));
//        String lastAction = (String) session.getAttribute("lastAction");
//        if (lastAction != null) {
//            response.sendRedirect(request.getContextPath() + "/servlet?getAction=" + lastAction);
//            return;
//        }
        String path = request.getHeader("Referer");
        if (path != null) {
            response.sendRedirect(path);
            return;
        }
//        String lastPath = (String) session.getAttribute("lastPath");
//        if (lastPath != null) {
//            response.sendRedirect(lastPath);
//            return;
//        }
        response.sendRedirect(request.getContextPath() + "/");
    }
    
}
