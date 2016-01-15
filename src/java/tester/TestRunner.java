/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import model.dao.DAODirector;
import model.dao.ENUMEntity;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class TestRunner {
    public static void main(String[] args) {
        Meal meal = getMeal();
        System.out.println(meal.getId() + ", " + meal.getName());
    }
    
    public static Meal getMeal() {
        DAODirector director = new DAODirector(ENUMEntity.MEAL);
        Meal meal = (Meal) director.getEntityById(5);
        return meal;
    }
}
