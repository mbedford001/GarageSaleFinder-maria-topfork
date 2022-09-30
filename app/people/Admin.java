package people;

/**
 * Class extending Account to create an Admin account
 * @author Keiley Maahs
 * @version 9/29/2022
 */
public class Admin extends Account{

    /**
     * Constructor to create an Admin account
     * @param firstName
     * @param lastName
     * @param username
     * @param password
     * @param type
     * @param status
     */
    public Admin(String firstName, String lastName, String username, String password, char type, char status){
        super(firstName, lastName, username, password, type, status);
    }
}
