/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.Login;
import controller.action.HomePage;
import controller.action.ChangeLanguage;
import controller.action.CreateAccount;
import controller.action.LogOut;
import controller.action.LoginRequest;
import controller.action.SignUp;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Sasha
 */
@WebServlet("/servlet")
public class Servlet extends HttpServlet {
    
    private final Map<String, Action> actions = new HashMap<>();
    
    private final Map<String, Action> buttons = new HashMap<>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        actions.put("home", new HomePage());
        actions.put("login", new Login());
        actions.put("language", new ChangeLanguage());
        actions.put("logout", new LogOut());
        actions.put("changeLanguage", new ChangeLanguage());
        actions.put("loginRequest", new LoginRequest());
        actions.put("signUp", new SignUp());
        buttons.put("login", new Login());
        buttons.put("createAccount", new CreateAccount());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String actionKey = request.getParameter("action");
        if (actionKey == null) {
            actionKey = "home";
        }
        Action action = actions.get(actionKey);
        action.execute(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String buttonKey = request.getParameter("button");
        Action button = buttons.get(buttonKey);
        button.execute(request, response);
    }
    
}
