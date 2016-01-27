/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import static javax.servlet.jsp.tagext.Tag.EVAL_PAGE;
import static javax.servlet.jsp.tagext.Tag.SKIP_BODY;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author ToSan
 */
public class ErrorMessageTag extends TagSupport{
    
    @Override
    public int doStartTag() throws JspException {
        String errorMessage = (String) pageContext.getSession().getAttribute("errorMessage");
        try {
            JspWriter out = pageContext.getOut();
            if (errorMessage != null && !errorMessage.isEmpty()) {
                out.write("<fmt:message key=\"" + errorMessage + "\" />");
            }
        } catch (IOException e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
    
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
    
}
