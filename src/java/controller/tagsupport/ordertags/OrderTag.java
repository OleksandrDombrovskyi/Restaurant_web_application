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
import model.entity.OrderItem;

/**
 *
 * @author Sasha
 */
public class OrderTag extends AbstractTagSupport {
    
    /** order */
    private Order order;
    
    /**
     * Order
     * @param order order
     */
    public void setOrder(Object order) {
        this.order = (Order) order;
    }
    
    /**
     * Method executes in the time when tag was reached 
     * @return int value means some specific action
     * @throws JspException 
     */
    @Override
    public int doStartTag() throws JspException {
        ResourceBundle resourceBundle = getBundle("controller.properties.text");
        String mealName = resourceBundle.getString("order.table.mealname");
        String description = resourceBundle.getString("order.table.mealdescription");
        String mealPrice = resourceBundle.getString("order.table.mealprice");
        String mealamount = resourceBundle.getString("order.table.mealnumber");
        String itemPrice = resourceBundle.getString("order.table.itemprice");
        String totalPtice = resourceBundle.getString("order.table.totalprice");
        try {
            JspWriter out = pageContext.getOut();
            out.write("<table>");
            out.write("<tr>");
            out.write("<td><h3>" + mealName + "</h3></td>");
            out.write("<td><h3>" + description + "</h3></td>");
            out.write("<td><h3>" + mealPrice + "</h3></td>");
            out.write("<td><h3>" + mealamount + "</h3></td>");
            out.write("<td><h3>" + itemPrice + "</h3></td>");
            out.write("</tr>");
            for (OrderItem item : order.getOrderItems()) {
                out.write("<tr>");
                out.write("<td>" + item.getMeal().getName() + "</td>");
                out.write("<td>" + item.getMeal().getDescription() + "</td>");
                out.write("<td>" + item.getMeal().getPrice() + " USD" + "</td>");
                out.write("<td>" + item.getMealAmount() + "</td>");
                out.write("<td>" + item.getTotalPrice() + " USD" + "</td>");
                out.write("</tr>");
            }
            out.write("<tr>");
            out.write("<td></td>");
            out.write("<td></td>");
            out.write("<td></td>");
            out.write("<td><h3>" + totalPtice + "</h3></td>");
            out.write("<td><h3>" + order.getTotalPrice() + " USD" + "</h3></td>");
            out.write("</tr>");
            out.write("</table>");
        } catch (IOException e) {
            throw new JspException(e);
        }
        return SKIP_BODY;
    }
}
