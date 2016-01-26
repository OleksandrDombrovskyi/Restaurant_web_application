/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.getactions.ChangeLanguage;
import controller.action.getactions.Contacts;
import controller.action.getactions.HomePage;
import controller.action.getactions.Info;
import controller.action.getactions.LoginRequest;
import controller.action.getactions.MainMenu;
import controller.action.getactions.SignUp;
import controller.action.getactions.personal.LogOut;
import controller.action.getactions.personal.Profile;
import controller.action.getactions.personal.Settings;
import controller.action.getactions.personal.admin.Administration;
import controller.action.getactions.personal.admin.GetAllOrders;
import controller.action.getactions.personal.admin.GetOrderAdmin;
import controller.action.getactions.personal.admin.GetOrderByStatus;
import controller.action.getactions.personal.admin.GetUser;
import controller.action.getactions.personal.admin.GetUserOrders;
import controller.action.getactions.personal.admin.GetUsers;
import controller.action.getactions.personal.kitchen.GetAcceptedOrders;
import controller.action.getactions.personal.kitchen.GetOrderKitchen;
import controller.action.getactions.personal.user.Basket;
import controller.action.getactions.personal.user.Order;
import controller.action.getactions.personal.user.Orders;
import controller.action.getactions.personal.user.UserAccount;

/**
 *
 * @author Sasha
 */
public enum GetActionEnum {
    
    
    HOME { 
        { 
            this.action =  new HomePage();
        } 
    }, 
    LOGOUT { 
        { 
            this.action =  new LogOut();
        } 
    }, 
    CHANGE_LANGUAGE { 
        { 
            this.action =  new ChangeLanguage();
        } 
    }, 
    LOGIN_REQUEST { 
        { 
            this.action =  new LoginRequest();
        } 
    }, 
    SIGN_UP { 
        { 
            this.action =  new SignUp();
        } 
    }, 
    MAIN_MENU { 
        { 
            this.action =  new MainMenu();
        } 
    }, 
    PROFILE { 
        { 
            this.action =  new Profile();
        } 
    }, 
    ORDERS { 
        { 
            this.action =  new Orders();
        } 
    }, 
    GET_ORDER { 
        { 
            this.action =  new Order();
        } 
    }, 
    ACCOUNT { 
        { 
            this.action =  new UserAccount();
        } 
    }, 
    SETTINGS { 
        { 
            this.action =  new Settings();
        } 
    }, 
    BASKET { 
        { 
            this.action =  new Basket();
        } 
    }, 
    INFO { 
        { 
            this.action =  new Info();
        } 
    }, 
    CONTACTS { 
        { 
            this.action =  new Contacts();
        } 
    }, 
    ADMINISTRATION { 
        { 
            this.action =  new Administration();
        } 
    }, 
    GET_USERS { 
        { 
            this.action =  new GetUsers();
        } 
    }, 
    GET_ALL_ORDERS { 
        { 
            this.action =  new GetAllOrders();
        } 
    }, 
    GET_USER { 
        { 
            this.action =  new GetUser();
        } 
    }, 
    GET_USER_ORDERS { 
        { 
            this.action =  new GetUserOrders();
        } 
    }, 
    GET_ORDER_ADMIN { 
        { 
            this.action =  new GetOrderAdmin();
        } 
    }, 
    SHOW_ACCEPTED_ORDERS { 
        { 
            this.action =  new GetAcceptedOrders();
        } 
    }, 
    GET_ORDER_KITCHEN { 
        { 
            this.action =  new GetOrderKitchen();
        } 
    }, 
    GET_ORDER_BY_STATUS { 
        { 
            this.action =  new GetOrderByStatus();
        } 
    };
    
    /** get action object */
    Action action;
    
    /**
     * Get get action
     * @return get action
     */
    public Action getAction() {
        return action;
    }
    
}
