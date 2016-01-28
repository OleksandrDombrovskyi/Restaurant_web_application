/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport.mainmenutag;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class MainMenuTag extends TagSupport {
    
    /** list of meals */
    private List<Meal> meals;
    
    /**
     * Set meal and define meal tag iterator
     * @param meals list of meals
     */
    public void setMeals(Object meals) {
        this.meals = (List<Meal>) meals;
    }
    
    /**
     * Do start tag method
     * @return int EVAL_BODY_INCLUDE
     * @throws JspTagException 
     */
    @Override
    public int doStartTag() throws JspTagException {
        Object object = pageContext.getSession().getAttribute("language");
        Locale locale = null;
        if (object instanceof Locale) {
            locale = (Locale) object;
        } else if (object instanceof String) {
            locale = new Locale((String) object);
        }
        ResourceBundle recourceBundle = ResourceBundle.getBundle("controller.properties.text", locale);
        String mealName = recourceBundle.getString("order.table.mealname");
        String description = recourceBundle.getString("order.table.mealdescription");
        String price = recourceBundle.getString("order.table.mealprice");
        try {
            JspWriter out = pageContext.getOut();
            out.write("<table>");
            out.write("<tr>");
            out.write("<td><h3>" + mealName + "</h3></td>");
            out.write("<td><h3>" + description + "</h3></td>");
            out.write("<td><h3>" + price + "</h3></td>");
            out.write("</tr>");
            for (Meal meal : meals) {
                out.write("<tr>");
                out.write("<td>" + meal.getName() + "</td>");
                out.write("<td>" + (meal.getDescription() != null ?  meal.getDescription() : "") + "</td>");
                out.write("<td>" + meal.getPrice().toString() + " USD" + "</td>");
                if (pageContext.getSession().getAttribute("user") != null) {
                    out.write("<td>");
                    out.write("<select name=\"" + meal.getId() + "\" >");
                    for (int i = 0; i <= 10; i++) {
                        out.write("<option value=\""+ i + "\" >" + i + "</option>");
                    }
                    out.write("</select>");
                    out.write("</td>");
                }
                out.write("</tr>");
            }
            out.write("</table>");
        } catch (IOException e) {
            throw new JspTagException();
        } 
        return SKIP_BODY;
    }
    
}
