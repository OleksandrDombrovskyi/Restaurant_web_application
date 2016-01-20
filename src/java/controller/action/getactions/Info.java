/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.action.getactions;

import controller.action.ConcreteLink;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;

/**
 *
 * @author Sasha
 */
public class Info extends GetAction {

    @Override
    protected void doExecute() throws ServletException, IOException {
        setHead("info.text.title");
        request.getRequestDispatcher("/view/info.jsp").include(request, response);
    }
    
    @Override
    protected List<ConcreteLink> getLink() {
        List<ConcreteLink> links = new ArrayList<>();
        links.addAll(new HomePage().getLink());
        String linkValue = "/servlet?getAction=info";
        String linkName = "home.link.info";
        ConcreteLink concreteLink = new ConcreteLink(linkValue, linkName);
        links.add(concreteLink);
        return links;
    }
    
}
