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
public class UserConfirmationButton extends AbstractTagSupport {
    
    /** order */
    private Order order;
    
    /**
     * Setter method for order object
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
        String confirmationButton = resourceBundle.getString("order.button.pay");
        String confirmationDialogBox = resourceBundle.getString("order.dialogbox.confirmation");
        if (order.getStatus() == OrderStatus.READY) {
            try {
                JspWriter out = pageContext.getOut();
                out.write("<input type=\"submit\" value=\"" + confirmationButton + "\"");
                out.write("onclick=\"return confirm('" + confirmationDialogBox + "')\" />");
                out.write("<input type=\"hidden\" name=\"postAction\" value=\"pay_order\" />");
                out.write("<input type=\"hidden\" name=\"orderId\" value=\"" + order.getId() + "\" />");
            }catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }
    
}
