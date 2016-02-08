/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.ActionFactory;
import controller.action.getactions.GetAction;
import controller.action.postactions.PostAction;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 * Servlet class
 * @author Sasha
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    
    /** log4j logger */
    private final Logger logger = Logger.getLogger(Servlet.class);
    
    /**
     * Do get servlet method
     * @param request http servlet request
     * @param response http servlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected synchronized void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        ConfigManager configManager = new ConfigManager();
        GetAction action = (GetAction) new ActionFactory().getGetAction(request);
        logger.info("Get action was called: " + action.toString());
        String page = action.execute(request, response);
        if (page == null) {
            page = configManager.getProperty("path.home"); 
        }
        goToPage(page, request, response);
        logger.info("Get action was performed: " + action.toString());
    }
    
    /**
     * Do post servlet method
     * @param request http servlet request
     * @param response http servlet response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected synchronized void doPost(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        PostAction action = (PostAction) new ActionFactory().getPostAction(request);
        if (action == null) {
            doGet(request, response);
        }
        logger.info("Post action was called: " + action.toString());
        action.execute(request, response);
        logger.info("Post action was performed: " + action.toString());
    }
    
    /**
     *  Foward to page
     * @param pageProp page property value
     * @param request http servlet request
     */
    private void goToPage(String page, HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        ConfigManager configManager = new ConfigManager();
        request.setAttribute("relativeURI", page);
        String createPage = configManager.getProperty("path.page.createpage");
        request.getRequestDispatcher(createPage).forward(request, response);
    }
    
}
