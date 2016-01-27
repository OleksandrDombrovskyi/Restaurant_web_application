/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author ToSan
 */
public class MessageTag extends TagSupport {
    
    @Override
    public int doStartTag() throws JspException {
        String message = (String) pageContext.getSession().getAttribute("message");
        try {
            JspWriter out = pageContext.getOut();
            if (message != null && !message.isEmpty()) {
                out.write("<fmt:message key=\"" + message + "\" />");
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
