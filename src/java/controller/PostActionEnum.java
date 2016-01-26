/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.postactions.ChangeLanguage;
import controller.action.postactions.personal.kitchen.SetPreparedStatus;
import controller.action.postactions.CreateAccount;
import controller.action.postactions.LogOut;
import controller.action.postactions.Login;
import controller.action.postactions.personal.admin.AdminChangePassword;
import controller.action.postactions.personal.admin.AdminSaveChanges;
import controller.action.postactions.personal.admin.ConfirmPreparedOrder;
import controller.action.postactions.personal.admin.SendToKitchen;
import controller.action.postactions.personal.user.AddToBasket;
import controller.action.postactions.personal.user.BasketConfirmation;
import controller.action.postactions.personal.user.ClearBasket;
import controller.action.postactions.personal.user.PayOrder;
import controller.action.postactions.personal.user.UserChangePassword;
import controller.action.postactions.personal.user.UserSaveChanges;

/**
 *
 * @author Sasha
 */
public enum PostActionEnum {
    
    LOGIN { 
        { 
            this.action = new Login(); 
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
    CREATE_ACCOUNT { 
        { 
            this.action = new CreateAccount(); 
        } 
    },
    USER_SAVE_CHANGES { 
        { 
            this.action = new UserSaveChanges(); 
        } 
    },
    ADMIN_SAVE_CHANGES { 
        { 
            this.action = new AdminSaveChanges(); 
        } 
    },
    USER_CHANGE_PASSWORD { 
        { 
            this.action = new UserChangePassword(); 
        } 
    },
    ADMIN_CHANGE_PASSWORD { 
        { 
            this.action = new AdminChangePassword(); 
        } 
    },
    ADD_TO_BASKET { 
        { 
            this.action = new AddToBasket(); 
        } 
    },
    BASKET_CONFIRM { 
        { 
            this.action = new BasketConfirmation(); 
        } 
    },
    CLEAR_BASKET { 
        { 
            this.action = new ClearBasket(); 
        } 
    },
    SEND_TO_KITCHEN { 
        { 
            this.action = new SendToKitchen(); 
        } 
    },
    SET_PREPARED_STATUS { 
        { 
            this.action = new SetPreparedStatus(); 
        } 
    },
    CONFIRM_PREPARED_STATUS { 
        { 
            this.action = new ConfirmPreparedOrder(); 
        } 
    },
    PAY_ORDER { 
        { 
            this.action = new PayOrder(); 
        } 
    };
    
    /** post action */
    Action action;
    
    /** 
     * Get post action
     * @return post action
     */
    public Action getAction() {
        return action;
    }
}
