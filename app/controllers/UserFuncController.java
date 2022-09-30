package controllers;
import people.*;

/**
 * Class with methods related to a user's functionality
 * @author .......
 * @version 9/29/2022
 */
public class UserFuncController {

    DatabaseController dbc = new DatabaseController();

    /**
     * Method for a user to create an account. Username must not be null
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param type the type of account ('U')
     * @param status the status of the user ('Y' or 'N')
     */
    public void makeAccount(String firstName, String lastName, String username, String password, char type, char status){
        if (!(username.equals(null))){
            Account a = new User(firstName, lastName, username, password, type, status);
            dbc.addAccount(a); //not yet functional
        }
    }
}
