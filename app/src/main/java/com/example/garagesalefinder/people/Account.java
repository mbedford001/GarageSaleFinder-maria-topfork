package com.example.garagesalefinder.people;

/**
 * Abstract class for creating an Account
 * @author ......
 * @version 9/29/2022
 */
public abstract class Account {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private char type;
    private char status;
    //Add a list for posts

    /**
     * Constructor to initialize a new account
     * @param firstName the first name of the account holder
     * @param lastName the last name
     * @param username the username
     * @param password the password
     * @param type the type of account holder ('U' or 'A')
     * @param status the status of the account holder ('Y' or 'N')
     */
    public Account(String firstName, String lastName, String username, String password, char type, char status){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.type = type;
        this.status = status;
    }

    /**
     * Setter method to set the firstName
     * @param firstName the first name of the account holder
     */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Getter method to get the firstName
     * @return firstName
     */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Setter method to set the lastName
     * @param lastName the last name of the account holder
     */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Getter method to get the lastName
     * @return lastName the last name of the account holder
     */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Setter method to set the username
     * @param username the username of the account holder
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Getter method to get the username
     * @return username
     */
    public String getUsername(){
        return this.username;
    }

    /**
     * Setter method to set the password
     * @param password the password of the account holder
     */
    public void setPassword(String password){
        this.password = password;
    }

    /**
     * Getter method to get the password
     * @return password
     */
    public String getPassword(){
        return this.password;
    }

    /**
     * Setter method to set the type of account (Admin or User)
     * @param type the type of the user ("A" or "U")
     */
    public void setType(char type) {
        this.type = type;
    }

    /**
     * Getter method to get the type of account (Admin or User)
     * @return type the type of the user ("A" or "U")
     */
    public char getType() {
        return this.type;
    }

    /**
     * Setter method to set the status of the account ("Y" for active, "N" for inactive)
     * @param status the status of the account
     */
    public void setStatus(char status) {
        this.status = status;
    }

    /**
     * Getter method to get the status of the account
     * @return status
     */
    public char getStatus() {
        return this.status;
    }
}
