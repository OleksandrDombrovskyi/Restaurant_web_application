/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.getactions.Basket;
import controller.action.postactions.Login;
import controller.action.getactions.HomePage;
import controller.action.getactions.ChangeLanguage;
import controller.action.getactions.Contacts;
import controller.action.getactions.Info;
import controller.action.postactions.ChangePassword;
import controller.action.postactions.CreateAccount;
import controller.action.getactions.LogOut;
import controller.action.getactions.LoginRequest;
import controller.action.getactions.MainMenu;
import controller.action.postactions.MakeOrder;
import controller.action.getactions.Order;
import controller.action.getactions.Orders;
import controller.action.getactions.Profile;
import controller.action.postactions.SaveChanges;
import controller.action.getactions.Settings;
import controller.action.getactions.SignUp;
import controller.action.getactions.UserAccount;
import controller.action.postactions.AddToBasket;
import controller.action.postactions.BasketConfirmation;
import controller.action.postactions.ClearBasket;
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
    public synchronized void init(ServletConfig config) throws ServletException {
        getActions.put("home", new HomePage());
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
        getActions.put("basket", new Basket());
        getActions.put("info", new Info());
        getActions.put("contacts", new Contacts());
        postActions.put("login", new Login());
        postActions.put("createAccount", new CreateAccount());
        postActions.put("makeOrder", new MakeOrder());
//        postActions.put("confirm", new OrderConfirmation()); // delete together with classes!!!
//        postActions.put("remove", new RemoveOrder()); // delete together with classes!!!
        postActions.put("saveChanges", new SaveChanges());
        postActions.put("changePassword", new ChangePassword());
        postActions.put("addToBasket", new AddToBasket());
        postActions.put("basketConfirm", new BasketConfirmation());
        postActions.put("clearBasket", new ClearBasket());
    }
    
    @Override
    protected synchronized void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType ("text/html;charset=utf-8");
        request.setCharacterEncoding("windows-1251");
        response.setCharacterEncoding("windows-1251");
        response.setContentType("text/html");
        String actionKey = request.getParameter("getAction");
        if (actionKey == null) {
            actionKey = "home";
        }
        saveActionForRedirect(actionKey, request);
        Action action = getActions.get(actionKey);
        action.execute(request, response);
    }
    
    @Override
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
//        response.setContentType("text/html");
//        response.setCharacterEncoding("UTF-8");
//        request.setCharacterEncoding("Cp1251");
        request.setCharacterEncoding("windows-1251");
        response.setCharacterEncoding("windows-1251");
        response.setContentType("text/html");
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
