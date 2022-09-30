package people;

/**
 * Class extending Account to create an Admin account
 * @author .......
 * @version 9/29/2022
 */
public class Admin extends Account{

    /**
     * Constructor to create an Admin account
     * @param firstName the admin's first name
     * @param lastName the last name
     * @param username the username
     * @param password the password
     * @param type the type of account ('A')
     * @param status the status of the account ('Y' or 'N')
     */
    public Admin(String firstName, String lastName, String username, String password, char type, char status){
        super(firstName, lastName, username, password, type, status);
    }
}
