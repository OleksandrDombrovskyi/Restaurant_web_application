/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport.mainmenutag;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Sasha
 */
public class MainMenuTag extends TagSupport {
    
    /** count of rows */
    private int rows;
    
    /**
     * Set rows
     * @param rows count of rows
     */
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
    /**
     * Do start tag method
     * @return int EVAL_BODY_INCLUDE
     * @throws JspTagException 
     */
    @Override
    public int doStartTag() throws JspTagException {
        String mealName = "order.table.mealname";
        String description = "order.table.mealdescription";
        String price = "order.table.mealprice";
        try {
            JspWriter out = pageContext.getOut();
            out.write("<table>");
            out.write("<tr>");
            
            out.write("<td>");
            out.write("<fmt:message key=\"" + mealName + "\" />");
            out.write("</td>");
            
            out.write("<td>");
            out.write("<fmt:message key=\"" + description + "\" />");
            out.write("</td>");
            
            out.write("<td>");
            out.write("<fmt:message key=\"" + price + "\" />");
            out.write("</td>");
            
            out.write("</tr>");
            out.write("<tr>");
        } catch (IOException e) {
            throw new JspTagException();
        }
        return EVAL_BODY_INCLUDE;
    }
    
    /**
     * Executes after tag body
     * @return int value that means whether continue body execution or not
     * @throws JspTagException 
     */
    @Override
    public int doAfterBody() throws JspTagException {
        if (rows-- > 1) {
            try {
                JspWriter out = pageContext.getOut();
                out.write("</tr>");
                out.write("<tr>");
            } catch (IOException e) {
                throw new JspTagException();
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * Executes after body execution
     * @return int that means whether evaluate page or no
     * @throws JspTagException 
     */
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</tr></table>");
        } catch (IOException e) {
            
        }
        return EVAL_PAGE;
    }
    
}
