/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

/**
 *
 * @author Sasha
 */
public class DaoFactory {
    
    /**
     * Get concrete dao object (data base entity creator)
     * @param daoEnum type of DAO object
     * @return DAO object
     */
    public Dao getCreator(DaoEnum daoEnum) {
        switch (daoEnum) {
            case ADMIN_CREATOR:
                return new AdminCreator();
            case KITCHEN_CREATOR:
                return new KitchenCreator();
            case MEAL_CREATOR:
                return new MealCreator();
            case ORDER_CREATOR:
                return new OrderCreator();
            case USER_CREATOR:
                return new UserCreator();
            case PAYMENT_TRANSACTION:
                return SingletonPaymentTransaction.getInstance();
            default:
                return null;
        }
    }
    
}
