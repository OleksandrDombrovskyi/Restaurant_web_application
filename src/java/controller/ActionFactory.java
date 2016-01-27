/*
 * To change this license header: return choose License Headers in Project Properties.
 * To change this template file: return choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.action.Action;
import controller.action.getactions.*;
import controller.action.getactions.personal.kitchen.*;
import controller.action.postactions.*;
import controller.action.postactions.personal.admin.*;
import controller.action.postactions.personal.user.*;
import javax.servlet.http.HttpServletRequest;

/**
 * Action factory
 * @author Sasha
 */
public class ActionFactory {
    
    /**
     * Get required getAction
     * @param request http servlet request
     * @return required new object according to action
     */
    public Action getGetAction(HttpServletRequest request) {
        Action action = new HomePage();
        String getAction = request.getParameter("getAction");
        if (getAction == null) {
            return action;
        }
        try {
            GetActionEnum actionEnum = GetActionEnum.valueOf(getAction.toUpperCase());
            action = actionEnum.getAction();
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("errormessage", "exception.errormessage.illegalargumentexception");
        }
        return action;
    }
    
    /**
     * Get post action
     * @param request http servlet request
     * @return required post action object
     */
    public Action getPostAction(HttpServletRequest request) {
        Action action = null;
        String postAction = request.getParameter("postAction");
        if (postAction == null) {
            return null;
        }
        try {
            PostActionEnum actionEnum = PostActionEnum.valueOf(postAction.toUpperCase());
            action = actionEnum.getAction();
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("errormessage", "exception.errormessage.illegalargumentexception");
        }
        return action;
    }
    
}
