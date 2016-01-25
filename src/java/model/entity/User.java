/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sasha
 */
public class User extends Person {
    
    /** user account */
    private BigDecimal account;
    
    /** list of user's orders */
    private List<Order> orders;

    /**
     * Constructor
     * @param firstName user first name
     * @param lastName user last name
     * @param email user email
     * @param password users' password
     */
    public User(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

    /**
     * Get user account
     * @return 
     */
    public BigDecimal getAccount() {
        return account;
    }

    /**
     * Set user account
     * @param account user account
     */
    public void setAccount(BigDecimal account) {
        this.account = account;
    }
    
    /**
     * Set all user's orders
     * @param orders 
     */
    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    
    /**
     * Get orders
     * @return list of orders
     */
    public List<Order> getOrders() {
        return orders;
    }
    
    /**
     * Add order to the user's order list
     * @param order 
     */
    public void addOrder(Order order) {
        if (orders == null) {
            orders = new ArrayList<>();
        }
        orders.add(order);
    }
    
    /**
     * Remove order from user's order list by id
     * @param orderId id of order might be removed
     */
    public void removeOrderById(int orderId) {
        if (orders != null) {
            for (Order order : orders) {
                if (order.id == orderId) {
                    orders.remove(order);
                    break;
                }
            }
        }
    }
    
}
