/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport;

import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.jsp.tagext.TagSupport;

/**
 *
 * @author Sasha
 */
public abstract class AbstractTagSupport extends TagSupport {
    
    /**
     * Get required bundle according to the choosen language
     * @param uri uri of property file
     * @return resource bundle
     */
    protected ResourceBundle getBundle(String uri) {
        Object object = pageContext.getSession().getAttribute("language");
        Locale locale = null;
        if (object instanceof Locale) {
            locale = (Locale) object;
        } else if (object instanceof String) {
            locale = new Locale((String) object);
        }
        return ResourceBundle.getBundle(uri, locale);
    }
    
}
