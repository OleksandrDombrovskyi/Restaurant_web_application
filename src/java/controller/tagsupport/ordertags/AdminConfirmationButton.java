/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport.ordertags;

import controller.tagsupport.AbstractTagSupport;
import java.io.IOException;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import model.entity.Order;
import model.entity.Order.OrderStatus;

/**
 *
 * @author Sasha
 */
public class AdminConfirmationButton extends AbstractTagSupport {
    
    /** order */
    private Order order;
    
    /**
     * Setter method for order
     * @param order order object
     */
    public void setOrder(Object order) {
        this.order = (Order) order;
    }
    
    /**
     * Do start method 
     * @return int
     * @throws JspException 
     */
    @Override
    public int doStartTag() throws JspException {
        ResourceBundle resourceBundle = getBundle("controller.properties.text");
        String sendToKitchen = resourceBundle.
                getString("administration.user.order.button.sendtokitchen");
        String confirmPreparedOrder = resourceBundle.
                getString("administration.user.order.button.confirmpreparedorder");
        OrderStatus orderStatus = order.getStatus();
        try {
            JspWriter out = pageContext.getOut();
            switch (orderStatus) {
                case CREATED:
                    out.write("<input type=\"submit\" value=\"" + sendToKitchen + "\" />");
                    out.write("<input type=\"hidden\" name=\"postAction\" value=\"send_to_kitchen\" />");
                    out.write("<input type=\"hidden\" name=\"orderId\" value=\"" + order.getId() + "\" />");
                    break;
                case PREPARED:
                    out.write("<input type=\"submit\" value=\"" + confirmPreparedOrder + "\" />");
                    out.write("<input type=\"hidden\" name=\"postAction\" value=\"confirm_prepared_order\" />");
                    out.write("<input type=\"hidden\" name=\"orderId\" value=\"" + order.getId() + "\" />");
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
    
}
