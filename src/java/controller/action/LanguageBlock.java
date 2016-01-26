/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import controller.ConfigManager;
import controller.action.getactions.GetAction;
import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Language block
 * @author Sasha
 */
public class LanguageBlock extends GetAction {

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
    
}