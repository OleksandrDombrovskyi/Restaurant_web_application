/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import controller.action.ConcreteLink;
import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public abstract class GetAction extends Action {
    
    /**
     * Create band of navigation links
     * @throws ServletException
     * @throws IOException 
     */
    protected void setNavigationBlock() throws ServletException, IOException {
        List<ConcreteLink> links = getLink();
        request.setAttribute("links", links);
        request.getRequestDispatcher("/view/navigation.jsp").
                include(request, response);
    }
    
    /**
     * Get links chain
     * @return list of string links
     */
    protected abstract List<ConcreteLink> getLink();
    
    protected void setHead(String title) throws ServletException, IOException {
        request.setAttribute("title", title);
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
    }
    
}
