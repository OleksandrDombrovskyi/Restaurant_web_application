/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.postactions;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;

/**
 * Change language
 * @author Sasha
 */
public class ChangeLanguage extends PostAction {
    
    /** context path */
    private final static String CONTEXT_PATH = 
            "http://localhost:8080/Project4_v2_5/";

    /**
     * Set the language settings
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        Locale locale = new Locale(request.getParameter("language"));
        session.setAttribute("language", locale);
        String path = request.getHeader("Referer");
        if (path == null || path.equals(CONTEXT_PATH)) {
            sendRedirect(null, null, HOME_PAGE_LINK);
            return;
        }
        response.sendRedirect(path);
    }
    
}
