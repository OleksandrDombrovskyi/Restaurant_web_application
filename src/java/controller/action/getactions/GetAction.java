/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.Action;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Get action abstract class
 * @author Sasha
 */
public abstract class GetAction extends Action {
    
    /** page title */
    protected String title;
    
    /**
     * Constructor
     * @param title page title
     */
    public GetAction (String title) {
        this.title = title;
    }
    
    /**
     * Initialization required variables and run doExecute method
     * 
     * @param request http servlet reuest
     * @param response http servlet response
     * @return string value of page property file
     * @throws ServletException
     * @throws IOException 
     */
    public String execute(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        this.request = request;
        this.response = response;
        session = request.getSession();
        request.setCharacterEncoding("windows-1251");
        response.setCharacterEncoding("windows-1251");
        response.setContentType("text/html");
        request.setAttribute("title", title);
        request.setAttribute("links", getLink());
        return doExecute();
    }
    
    /**
     * Execute required action
     * @return string value of page property file
     * @throws ServletException
     * @throws IOException 
     */
    protected abstract String doExecute() throws ServletException, IOException;
    
    /**
     * Get array list of link chain direct to current page (in fact this method 
     * gets link chain of its' previous page, add its' own link and return 
     * created array list)
     * 
     * @return array list of links
     */
    public abstract List<ConcreteLink> getLink();
    
}
