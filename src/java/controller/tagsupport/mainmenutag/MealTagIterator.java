/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.tagsupport.mainmenutag;

import java.util.Iterator;
import java.util.List;
import model.entity.Meal;

/**
 *
 * @author Sasha
 */
public class MealTagIterator {
    
    /** meal list */
    private List<Meal> meals;
    
    /** meal iterator */
    private Iterator it;
    
    /**
     * Construcor
     * @param meals list of meals
     */
    public MealTagIterator(List<Meal> meals) {
        this.meals = meals;
        it = this.meals.iterator();
    }
    
    /**
     * Get size
     * @return int size
     */
    public int getSize() {
        if (meals != null) {
            return meals.size();
        }
        return 0;
    }
    
    /**
     * Get next meal by iterator
     * @return meal object
     */
    public Object getMeal() {
        if (it.hasNext()) {
            return it.next();
        } else {
            return null;
        }
    }
}
