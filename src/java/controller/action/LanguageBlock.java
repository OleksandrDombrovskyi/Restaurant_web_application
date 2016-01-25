/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action;

import java.io.IOException;
import javax.servlet.ServletException;

/**
 * Language block
 * @author Sasha
 */
public class LanguageBlock extends Action {

    /**
     * Set language block
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    public void doExecute() throws ServletException, IOException {
        request.getRequestDispatcher("/view/language.jsp").include(request, response);
    }
    
}