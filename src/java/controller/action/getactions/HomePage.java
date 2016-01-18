/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.LanguageBlock;
import controller.action.SetAuthorizationBlock;
import controller.action.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class HomePage extends GetAction {

    @Override
    public void doExecute() throws ServletException, IOException {
        request.setAttribute("title", "home.text.title");
        new LanguageBlock().execute(request, response);
        new SetAuthorizationBlock().execute(request, response);
        setNavigationBlock();
        request.getRequestDispatcher("/view/home.jsp").include(request, response);
//        try (PrintWriter out = response.getWriter()) {
//            out.println("request.getParameter(language): " + request.getParameter("language")); // input this expression to the jsp file
//            out.println("request.getSession().getAttribute(\"action\"): " + request.getSession().getAttribute("action"));
//        }
    }

    /**
     * Get link to home page
     * @return list with one link only
     */
    @Override
    protected List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList();
        String linkValue = "/";
        String linkName = "home.text.title";
        links.add(new ConcreteLink(linkValue, linkName));
        return links;
    }
    
}
