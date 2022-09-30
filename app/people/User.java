
package people;

/**
 * Class extending Account to create a User account
 * @author ........
 * @version 9/29/2022
 */
public class User extends Account{

    /**
     * Constructor to create a User account
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param username the username of the user
     * @param password the password of the user
     * @param type the type of account ('U')
     * @param status the status of the account ('Y' or 'N')
     */
    public User(String firstName, String lastName, String username, String password, char type, char status){
        super(firstName, lastName, username, password, type, status);
    }
}
