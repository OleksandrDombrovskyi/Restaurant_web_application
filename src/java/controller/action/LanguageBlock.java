/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.ConfigManager;
import controller.action.getactions.ConcreteLink;
import controller.action.getactions.GetAction;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;

/**
 * Language block
 * @author Sasha
 */
public class LanguageBlock extends GetAction {

    public LanguageBlock(String title) {
        super(title);
    }

    /**
     * Set language block
     * @return property key value 
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public String doExecute() throws ServletException, IOException {
        return ConfigManager.getProperty("path.page.language");
    }

    @Override
    public List<ConcreteLink> getLink() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}