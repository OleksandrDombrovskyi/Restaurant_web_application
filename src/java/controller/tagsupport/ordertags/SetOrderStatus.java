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
public class SetOrderStatus extends AbstractTagSupport {
    
    /** order object */
    private Order order;
    
    /**
     * Setter method for the order
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
        String created = resourceBundle.getString("order.status.created");
        String accepted = resourceBundle.getString("order.status.accepted");
        String prepared = resourceBundle.getString("order.status.prepared");
        String ready = resourceBundle.getString("order.status.ready");
        String payed = resourceBundle.getString("order.status.payed");
        OrderStatus orderStatus = order.getStatus();
        try {
            JspWriter out = pageContext.getOut();
            switch (orderStatus) {
                case CREATED:
                    out.write(created);
                    break;
                case ACCEPTED:
                    out.write(accepted);
                    break;
                case PREPARED:
                    out.write(prepared);
                    break;
                case READY:
                    out.write(ready);
                    break;
                case PAYED:
                    out.write(payed);
                    break;
                default:
                    out.write("not defined!");
            }
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
    
}
