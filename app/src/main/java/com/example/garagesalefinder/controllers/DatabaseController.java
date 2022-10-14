package com.example.garagesalefinder.controllers;
import com.example.garagesalefinder.people.*;

/**
 * Class with methods connecting to the database
 * @author .......
 * @version 9/29/2022
 */
public class DatabaseController {

    /**
     * Method to add an account to the database........INCOMPLETE!!
     * @param a the account to add to the database
     */
    public void addAccount(Account a){
        //add account to database
    }

    /**
     * Method to get the account corresponding to the given username from the database......INCOMPLETE!!
     * @param username the username of the account to grab
     * @return the account
     */
    public Account getAccount(String username){
        return new User("This", "is", "temporary", "dummy", 'U', 'Y');
        //grab real account from database based on username
    }
    
}
