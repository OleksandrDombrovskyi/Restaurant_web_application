/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Calendar;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This filter does not permit more then 2 requests per second in one session
 * @author Sasha
 */
@WebFilter(filterName="hitCounter")
public class FilterHitCounter implements Filter {

    /**
     * Init method. Executes when class was created by servlet container
     * @param filterConfig filter config
     * @throws ServletException 
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * Dont allow meny requests (limit is 2 requests per second) in one session
     * @param request servlet request
     * @param response servlet request
     * @param chain filter chain
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public synchronized void doFilter(ServletRequest request, 
            ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpSession session = ((HttpServletRequest)request).getSession();
            HitCounter hitCounter = (HitCounter) session.getAttribute("hitCounter");
            if (hitCounter == null) {
                hitCounter = new HitCounter();
            }
            long firstTime = hitCounter.getFirstTime();
            long currentTime = Calendar.getInstance().getTimeInMillis();
            hitCounter.setTime(currentTime);
            session.setAttribute("hitCounter", hitCounter);
            if (currentTime - firstTime < 1000) {
                ((HttpServletRequest)request).getRequestDispatcher("/view/hitfilter.jsp").
                        forward(request, response);
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    /**
     * Destroy method which calls when before class will be removed
     */
    @Override
    public void destroy() {}
    
}
