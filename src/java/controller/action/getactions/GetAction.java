/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.ConcreteLink;
import controller.action.LanguageBlock;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;

/**
 * Get action abstract class
 * @author Sasha
 */
public abstract class GetAction extends Action {
    
    /**
     * 
     * @param title
     * @param relativeURI
     * @throws ServletException
     * @throws IOException 
     */
    protected void goToPage(String title, String relativeURI) throws ServletException, IOException {
        request.setAttribute("title", title); // set title
        request.setAttribute("links", getLink()); // set navigation block links
        request.setAttribute("relativeURI", relativeURI); // set relative URI of new page
        request.getRequestDispatcher("/createpage.jsp").
                include(request, response); // call to page that includes all required parts of page
    }
    
    /**
     * Show error message only
     * 
     * @param message error message
     * @throws IOException
     * @throws ServletException 
     */
    protected void showMessageTest(String message) throws IOException, 
            ServletException {
        request.setAttribute("title", message);
        new LanguageBlock().execute(request, response);
        request.setAttribute("errorMessage", message);
        request.getRequestDispatcher("/view/error.jsp").
                include(request, response);
    }
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    public abstract List<ConcreteLink> getLink();
    
}
