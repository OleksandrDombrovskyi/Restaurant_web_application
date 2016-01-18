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
import controller.action.ChangePassword;
import controller.action.CreateAccount;
import controller.action.LogOut;
import controller.action.LoginRequest;
import controller.action.MainMenu;
import controller.action.MakeOrder;
import controller.action.Order;
import controller.action.OrderConfirmation;
import controller.action.Orders;
import controller.action.Profile;
import controller.action.RemoveOrder;
import controller.action.SaveChanges;
import controller.action.Settings;
import controller.action.SignUp;
import controller.action.UserAccount;
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
    
    private final Map<String, Action> getActions = new HashMap<>();
    
    private final Map<String, Action> postActions = new HashMap<>();
    
    @Override
    public void init(ServletConfig config) throws ServletException {
        getActions.put("home", new HomePage());
        getActions.put("login", new Login());
        getActions.put("language", new ChangeLanguage());
        getActions.put("logout", new LogOut());
        getActions.put("changeLanguage", new ChangeLanguage());
        getActions.put("loginRequest", new LoginRequest());
        getActions.put("signUp", new SignUp());
        getActions.put("mainMenu", new MainMenu());
        getActions.put("profile", new Profile());
        getActions.put("orders", new Orders());
        getActions.put("getOrder", new Order());
        getActions.put("account", new UserAccount());
        getActions.put("settings", new Settings());
        postActions.put("login", new Login());
        postActions.put("createAccount", new CreateAccount());
        postActions.put("makeOrder", new MakeOrder());
        postActions.put("confirm", new OrderConfirmation());
        postActions.put("remove", new RemoveOrder());
        postActions.put("saveChanges", new SaveChanges());
        postActions.put("changePassword", new ChangePassword());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String actionKey = request.getParameter("getAction");
        if (actionKey == null) {
            actionKey = "home";
        }
        saveActionForRedirect(actionKey, request);
        Action action = getActions.get(actionKey);
        action.execute(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String actionKey = request.getParameter("postAction");
        Action action = postActions.get(actionKey);
        action.execute(request, response);
    }
    
    private void saveActionForRedirect(String actionKey, HttpServletRequest request) {
        if (!actionKey.equals("loginRequest") && !actionKey.equals("signUp")) {
            request.getSession().setAttribute("lastAction", actionKey);
        }
    }
    
}
