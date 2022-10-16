package com.example.garagesalefinder.controllers;
import com.example.garagesalefinder.people.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.ViewModelProvider;

/**
 * Class with methods related to account functions
 * @author .......
 * @version 9/29/2022
 */
public class AccountController {
   // DataBaseHelperClass dbc = new DataBaseHelperClass(AccountController.this);
    DatabaseController dbc = new DatabaseController();
    /**
     * This method asks the database to grab the account associated with
     * the username and calls validatePassword() to check if the password
     * passed matches the password in the account
     *
     * @param username username passed by client
     * @param password password passed by client
     * @return dummy account or null account associated with parameters
     */
    public Account login(String username, String password) {
        Account dummy = dbc.getAccount(username);
        if (dummy != null) {
            if (isActive(dummy)){
                boolean valid = validPassword(password, dummy.getPassword());
                if (valid) {
                    return dummy;
                }
                else {
                    return null; //If password is invalid
                }
            }
            return null;//If account is inactive
        }
        return null;//If account does not exist
    }

    /**
     * This method validates the password passed with the password of the account
     *
     * @param password password passed by client
     * @param pw password associated with the account
     * @return true if passwords are equivalent
     */
    public boolean validPassword(String password, String pw) {
        return pw.equals(password);
    }

    /**
     * This method checks if the account is active or inactive
     *
     * @param dummy account found in database
     * @return true if status of account is active
     */
    public boolean isActive(Account dummy) {
        return dummy.getStatus() == 'Y';
    }


}
