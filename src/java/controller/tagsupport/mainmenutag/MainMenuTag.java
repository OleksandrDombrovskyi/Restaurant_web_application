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
    
    private String head;
    {
        head = "<td><h3><fmt:message key=\"order.table.mealname\" /></h3></td>\n" +
                "<td><h3><fmt:message key=\"order.table.mealdescription\" /></h3></td>\n" +
                "<td><h3><fmt:message key=\"order.table.mealprice\" /></h3></td>";
    }
    
    private int rows;
    
    public void setRows(Integer rows) {
        this.rows = rows;
    }
    
    @Override
    public int doStartTag() throws JspTagException {
        try {
            JspWriter out = pageContext.getOut();
            out.write("Hello");
            out.write("<table>");
            out.write("<tr>");
            out.write("<td><h3><fmt:message key=\"order.table.mealname\" /></h3></td>");
            out.write("<td><h3><fmt:message key=\"order.table.mealdescription\" /></h3></td>");
            out.write("<td><h3><fmt:message key=\"order.table.mealprice\" /></h3></td>");
            out.write("</tr>");
            out.write("<tr>");
        } catch (IOException e) {
            throw new JspTagException();
        }
        return EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doAfterBody() throws JspTagException {
        if (rows-- > 1) {
            try {
                JspWriter out = pageContext.getOut();
//                if (pageContext.getSession().getAttribute("user") != null) {
//                    out.write("<td>");
//                    out.write("<select name=\"" + meal.getId() + "\" >");
//                    for (int i = 0; i < 11; i++) {
//                        out.write("<option value=\"" + i + "\" >" + i + "</option>");
//                    }
//                    out.write("</select>");
//                    out.write("</td>");
//                }
                out.write("</tr>");
                out.write("<tr>");
//                out.write("<td><c:out value=\"" + meal.getName() + "\" /></td>");
//                out.write("<td><c:out value=\"" + meal.getDescription() + "\" /></td>");
//                out.write("<td><fmt:formatNumber value=\"" + meal.getPrice() + "\" type=\"currency\" currencyCode=\"USD\" /></td>");
//                if (pageContext.getSession().getAttribute("user") != null) {
//                    out.write("<td>");
//                    out.write("<select name=\"" + meal.getId() + "\" >");
//                    for (int i = 0; i < 11; i++) {
//                        out.write("<option value=\"" + i + "\" >" + i + "</option>");
//                    }
//                    out.write("</select>");
//                    out.write("</td>");
//                }
//                out.write("</tr>");
            } catch (IOException e) {
                throw new JspTagException();
            }
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
    
    @Override
    public int doEndTag() throws JspTagException {
        try {
            pageContext.getOut().write("</tr></table>");
        } catch (IOException e) {
            
        }
        return EVAL_PAGE;
    }
    
}
