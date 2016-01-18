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
        actions.put("mainMenu", new MainMenu());
        actions.put("profile", new Profile());
        actions.put("orders", new Orders());
        actions.put("getOrder", new Order());
        actions.put("account", new UserAccount());
        actions.put("settings", new Settings());
        buttons.put("login", new Login());
        buttons.put("createAccount", new CreateAccount());
        buttons.put("makeOrder", new MakeOrder());
        buttons.put("confirm", new OrderConfirmation());
        buttons.put("remove", new RemoveOrder());
        buttons.put("saveChanges", new SaveChanges());
        buttons.put("changePassword", new ChangePassword());
    }
    
    @Override
    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        String actionKey = request.getParameter("action");
        if (actionKey == null) {
            actionKey = "home";
        }
        saveActionForRedirect(actionKey, request);
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
    
    private void saveActionForRedirect(String actionKey, HttpServletRequest request) {
        if (!actionKey.equals("loginRequest") && !actionKey.equals("signUp")) {
            request.getSession().setAttribute("lastAction", actionKey);
        }
    }
    
}
