/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Sasha
 */
public class HitCounter {
    
    /** allowed requests amount per one second */
    private static final int REQUESTS_PER_SECOND = 2;
    
    /** array with time values of requests */
    private long[] array = new long[REQUESTS_PER_SECOND];
    
    /** current index + 1 */
    private int flag;
    
    /**
     * Constructor
     */
    public HitCounter() {
        flag = 0;
    }
    
    /**
     * Write down time value into the array
     * @param timeInMillisecinds current request time
     */
    public void setTime(long timeInMillisecinds) {
        array[flag] = timeInMillisecinds;
        flag++;
        if (flag >= array.length) {
            flag = 0;
        }      
    }
    
    /**
     * Get time was wrote down 5 times ago (flag is the current index + 1)
     * @return long value of time in milliseconds
     */
    public long getFirstTime() {
        return array[flag];
    }
    
}
