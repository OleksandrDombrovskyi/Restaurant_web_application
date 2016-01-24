/*
 * To change this license header: return choose License Headers in Project Properties.
 * To change this template file: return choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.getactions.*;
import controller.action.getactions.personal.*;
import controller.action.getactions.personal.admin.*;
import controller.action.getactions.personal.kitchen.*;
import controller.action.getactions.personal.user.*;
import controller.action.postactions.*;
import controller.action.postactions.personal.admin.*;
import controller.action.postactions.personal.user.*;

/**
 * Action factory
 * @author Sasha
 */
public class ActionFactory {
    
    /**
     * Get required getAction
     * @param getAction string value of get action
     * @return required new object according to action
     */
    public Action getGetAction(String getAction) {
        switch (getAction) {
            case "home": 
                return new HomePage(); 
            case "logout": 
                return new LogOut(); 
            case "changeLanguage": 
                return new ChangeLanguage(); 
            case "loginRequest": 
                return new LoginRequest(); 
            case "signUp": 
                return new SignUp(); 
            case "mainMenu": 
                return new MainMenu(); 
            case "profile": 
                return new Profile(); 
            case "orders": 
                return new Orders(); 
            case "getOrder": 
                return new Order(); 
            case "account": 
                return new UserAccount(); 
            case "settings": 
                return new Settings(); 
            case "basket": 
                return new Basket(); 
            case "info": 
                return new Info(); 
            case "contacts": 
                return new Contacts(); 
            case "administration": 
                return new Administration(); 
            case "getUsers": 
                return new GetUsers(); 
            case "getAllOrders": 
                return new GetAllOrders(); 
            case "getUser": 
                return new GetUser(); 
            case "getUserOrders": 
                return new GetUserOrders(); 
            case "getOrderAdmin": 
                return new GetOrderAdmin(); 
            case "showAcceptedOrders": 
                return new GetAcceptedOrders(); 
            case "getOrderKitchen": 
                return new GetOrderKitchen(); 
            case "getOrderByStatus": 
                return new GetOrderByStatus(); 
            default:
                return null;
        }
    }
    
    /**
     * Get post action
     * @param postAction string value of post action
     * @return required post action object
     */
    public Action getPostAction(String postAction) {
        switch (postAction) {
            case "login": 
                return new Login();
            case "createAccount": 
                return new CreateAccount();
            case "userSaveChanges": 
                return new UserSaveChanges();
            case "adminSaveChanges": 
                return new AdminSaveChanges();
            case "userChangePassword": 
                return new UserChangePassword();
            case "adminChangePassword": 
                return new AdminChangePassword();
            case "addToBasket": 
                return new AddToBasket();
            case "basketConfirm": 
                return new BasketConfirmation();
            case "clearBasket": 
                return new ClearBasket();
            case "sendToKitchen": 
                return new SendToKitchen();
            case "setPreparedStatus": 
                return new SetPreparedStatus();
            case "confirmPreparedOrder": 
                return new ConfirmPreparedOrder();
            case "payOrder": 
                return new PayOrder();
            default:
                return null;
        }
    }
    
}
