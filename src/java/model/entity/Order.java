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
public class Order extends DBEntity {
    
    /** user id */
    private int userId;
    
    /** order status */
    private OrderStatus status;
    
    /** total price */
    private BigDecimal totalPrice;
    
    /** order items */
    private List<OrderItem> orderItems;

    /**
     * Constructor
     * @param status order status
     * @param id user id
     * @param userId user id
     * @param totalPrice total price
     */
    public Order(int id, int userId, OrderStatus status, BigDecimal totalPrice) {
        super(id);
        this.userId = userId;
        this.status = status;
        this.totalPrice = totalPrice;
    }
    
    /**
     * Set user id
     * @param userId user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    /**
     * Get user id
     * @return user id
     */
    public int getUserId() {
        return userId;
    }
    
    /**
     * Set order status
     * @param status order status 
     */
    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Get order status
     * @return order status
     */
    public OrderStatus getStatus() {
        return status;
    }
    
    /**
     * Set total price
     * @param totalPrice total price
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Get total price
     * @return total price
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }
    
    /**
     * Set all order items
     * @param orderItems order items
     */
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    
    /**
     * Get list of items of this order
     * @return list of order items
     */
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    
    /**
     * Add item to the order list
     * @param orderItem order item
     */
    public void addOrderItem(OrderItem orderItem) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(orderItem);
    }
    
    /**
     * Order status
     */
    public enum OrderStatus {
        CRETATED, VAITING, PREPARING, READY, PAYED
    }
    
}
