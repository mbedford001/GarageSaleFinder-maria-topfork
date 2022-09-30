
package people;

/**
 * Class extending Account to create a User account
 * @author Keiley Maahs
 * @version 9/29/2022
 */
public class User extends Account{

    /**
     * Constructor to create a User account
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param type
     * @param status
     */
    public User(String firstName, String lastName, String username, String password, char type, char status){
        super(firstName, lastName, username, password, type, status);
    }
}
