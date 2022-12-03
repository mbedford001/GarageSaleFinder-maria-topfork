package com.example.garagesalefinder.controllers;

import android.content.ContentValues;

import com.example.garagesalefinder.Login;
import com.example.garagesalefinder.Menu;
import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.content.Intent;

import java.text.SimpleDateFormat;
import java.util.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.garagesalefinder.controllers.AccountController;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import com.example.garagesalefinder.ViewPost;
import com.example.garagesalefinder.people.Account;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.garagesalefinder.PostStuff.Post;
/**
 * This class contain all core functions that are on database interaction included get data and add data to the database
 *
 * @author  Maria Bedford, Jack Buczak, Ethan Sutton, Noah Halonen, Hongtao Wang, Keiley Maahs, Kristiana Anderson
 * @since   2022-09-27
 */
public class DataBaseHelperClass extends SQLiteOpenHelper {
    public Context context;
    static SQLiteDatabase sqliteDataBase;
    //the Android's default system path of the application database /data/data/garagesalefinder/databases/
    private final static String DB_PATH = "/data/data/com.example.garagesalefinder/databases/";
    //the database name
    private static final String DATABASE_NAME = "TheCorrectDatabase.db";
    //database version
    private static final int DATABASE_VERSION = 1;
    // table names of database
    static final String TABLE_1 = "admin";
    //static final String TABLE_2 = "dates";
    //static final String TABLE_3 = "items";
    //static final String TABLE_4 = "manages";
    //static final String TABLE_5 = "regular_user";
    //static final String TABLE_6 = "sale_posts";
    //static final String TABLE_7 = "save_posts";

    private Account a;


    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access
     * to the application assets and resources
     * @param context
     * parameters of super() are 1. Context
     *                           2. Database Name
     *                           3. Cursor Factory
     *                           4. Database Version
     */
    public DataBaseHelperClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        System.out.println("1");
        try {
            createDatabase();

        }
        catch (IOException ioe){
            System.out.println("IOException was thrown");
        }
        openDataBase();

    }

    /**
     * Creates an empty database on the system and then rewrites it with our own database
     * @throws IOException
     */
    public void createDatabase() throws IOException {
        //check if the database exists
        boolean databaseExist = checkDataBase();
        if (databaseExist){
            //do nothing
        }
        else {
            sqliteDataBase = this.getWritableDatabase();
            copyDataBase();
            sqliteDataBase.close();//delete if things break more
        }
    }

    /**
     * checks if the database already exists in order to avoid re-copying the file each time
     * @return true if it exists, false if it doesn't
     */
    public boolean checkDataBase(){
        File databaseFile = new File("/data/data/com.example.garagesalefinder/databases/TheCorrectDatabase.db");
        return databaseFile.exists();
    }

    /**
     * copy the database to a new database
     * @throws IOException
     */
    private void copyDataBase() throws IOException{
        //open your local db as the input stream
        InputStream myInput = context.getAssets().open(DATABASE_NAME);
        //path to the just created empty db
        String outFileName = DB_PATH +DATABASE_NAME;
        //open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        //transfer bytes from the input file to the output file
        byte[] buffer = new byte[1024];
        int length;
        while((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }
        //close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /**
     * this method opens the database connection
     * first it creates the path up til database of the device
     * then creates connection with database
     */
    public void openDataBase() throws SQLException{
        //open the database
        String myPath = DB_PATH + DATABASE_NAME;
        sqliteDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        System.out.println("HELLO "+myPath);
    }

    /**
     * this method is used to close the database connection
     */
    @Override
    public synchronized void close(){
        if(sqliteDataBase != null){
            sqliteDataBase.close();
        }
        super.close();
    }

    /**
     * This method asks the database to grab the account associated with
     * the username and calls validatePassword() to check if the password
     * passed matches the password in the account
     *
     * @param username username passed by client
     * @param password password passed by client
     * @return dummy account or null account associated with parameters
     */
    public boolean login(String username, String password) {
        boolean access = false;
        String[] args = {username, password};

        String queryString = "SELECT fname from admin" +
                " WHERE (admin.username = ? and admin.password = ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        //System.out.println("cursor string: "+ cursor.moveToFirst());
        String queryString2 = "SELECT fname from regular_user" +
                " WHERE (regular_user.username=? and regular_user.password=?)";
        Cursor cursor2 = sqliteDataBase.rawQuery(queryString2, args);

        if (cursor.getCount()>=1) {
            access = true;
        }
        else if (cursor2.getCount()>=1) {
            access = true;
        }
        else{
           System.out.println("-----------FAILED LOGIN-----------");
        }
        cursor.close();
        sqliteDataBase.close();

        return access;
    }


    /**
     * this method allows a user to view their own post so they can edit it if needed
     * @param username a String that is the name of a user
     * @param title a String that is the title of a post
     * @return boolean whether this method works or not
     * @author Maria Bedford
     */
    public boolean viewOwnPost(String username, String title){
        //String[] args = {post.getOwner()};

        String name = username;
        String[] args = {name, title};
        String queryString = "SELECT * from sale_posts" +
                " WHERE (sale_posts.post_username = ? and sale_posts.post_name =?)";
        sqliteDataBase = this.getWritableDatabase();
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        System.out.println("---------------------");
       // System.out.println("Cursor for viewPost: "+ cursor.moveToFirst());
        System.out.println("Here is your Post: ");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println("get postname(): " + cursor.getString(1));
            System.out.println("get location(): " + cursor.getString(2));
            System.out.println("get description(): " + cursor.getString(3));
            System.out.println("get time(): " + cursor.getString(4));
            System.out.println("get range(): " + cursor.getString(5));
            System.out.println("get image(): " + cursor.getString(6));
            cursor.moveToNext();
        }
        System.out.println("---------------------");
        cursor.close();
        sqliteDataBase.close();
        return true;
    }

    /**
     * This method allows a user to view all of their own post names
     * this method is not in use yet but DON'T DELETE
     * @param username a string of the logged in user's username
     * @return a list of the names of the posts that user has created
     */
    public List<String> viewAllPostNames(String username){
        sqliteDataBase = this.getWritableDatabase();
        String name = username;
        String[] args = {name};
        String queryString = "SELECT post_name from sale_posts" +
                " WHERE (sale_posts.post_username = ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        List<String> terms = new ArrayList<String>();
        while (!cursor.isAfterLast()) {
            String saleName = cursor.getString(1);
            terms.add(saleName);
            cursor.moveToNext();
        }
        cursor.close();
        sqliteDataBase.close();
        return terms;
    }
    
    //gsfdfg

    /**
     * This method allows a user to view all of their own posts
     * this method is not in use yet but DON'T DELETE
     * @param username a string of the logged in user's username
     * @return a list of the Posts that user has created
     */
    public List<Post> viewAllOwnPosts(String username){
        sqliteDataBase = this.getWritableDatabase();
        String name = username;
        String[] args = {name};
        String queryString = "SELECT * FROM sale_posts" +
                " WHERE (sale_posts.post_username = ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        List<Post> terms = new ArrayList<Post>();
        while (!cursor.isAfterLast()) {
            String u = cursor.getString(0);
            String postName = cursor.getString(1);
            String location = cursor.getString(2);
            String description = cursor.getString(3);
            String time = cursor.getString(4);
            String price = cursor.getString(5);
            String image = cursor.getString(6);
            terms.add(new Post(u, location, postName, description, time, price, image));
            cursor.moveToNext();
        }
        cursor.close();
        sqliteDataBase.close();
        return terms;
    }

    /**
     * This method allows a user to view all of their saved posts
     *
     * @param username a string of the logged in user's username
     * @return a list of the Posts that user has created
     */
    public List<Post> viewSavedPosts(String username){
        sqliteDataBase = this.getWritableDatabase();
        String name = username;
        String[] args = {name};
        ArrayList<String> posts = new ArrayList<>();
        int count = 0;
        String queryString = "SELECT post_name FROM save_posts" +
                " WHERE (save_post_username = ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            posts.add(cursor.getString(0));
            count++;
            cursor.moveToNext();
        }
        ArrayList<Post> results= new ArrayList<Post>(0);
        queryString = "SELECT * from sale_posts" +
                " WHERE post_name =?";
        for(int i = 0; i<posts.size(); i++) {
            String[] args2 = {posts.get(i)};
            cursor = sqliteDataBase.rawQuery(queryString, args2);
            cursor.moveToFirst();
            Post post = new Post(cursor.getString(0), cursor.getString(2), cursor.getString(1),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            results.add(post);
        }
        cursor.close();
        sqliteDataBase.close();
        return results;
    }

    /**
     * This method allows a user to view all of the posts in database
     *
     * @return a list of the Posts that user has created
     */
    public List<Post> viewAllPosts(){
        sqliteDataBase = this.getWritableDatabase();
        String[] args = {};
        String queryString = "SELECT * FROM sale_posts";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        List<Post> terms = new ArrayList<Post>();
        while (!cursor.isAfterLast()) {
            String u = cursor.getString(0);
            String postName = cursor.getString(1);
            String location = cursor.getString(2);
            String description = cursor.getString(3);
            String time = cursor.getString(4);
            String price = cursor.getString(5);
            String image = cursor.getString(6);
            terms.add(new Post(u, location, postName, description, time, price, image));
            cursor.moveToNext();
        }
        cursor.close();
        sqliteDataBase.close();
        return terms;
    }

    public List<Items> viewItems(String username, String postName){
        sqliteDataBase = this.getWritableDatabase();
        String name = username;
        String[] args = {name, postName};
        String queryString = "SELECT * from items" +
                " WHERE (items.sale_post_username = ?) AND (items.post_title = ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        List<Items> terms = new ArrayList<Items>();
        while (!cursor.isAfterLast()) {
            String pTitle = cursor.getString(0);
            String iTitle = cursor.getString(1);
            String uname = cursor.getString(2);
            String category = cursor.getString(3);
            String image = cursor.getString(4);
            String description = cursor.getString(5);
            String price = cursor.getString(6);
            String quantity = cursor.getString(7);
            terms.add(new Items(pTitle, iTitle, uname, category, image, description, price, quantity));
            cursor.moveToNext();
        }
        cursor.close();
        sqliteDataBase.close();
        return terms;
    }

    /**
     * get Posts' data from database from a specific user included post name, post username, etc (for details, please check the database)
     * @param username the name of a user
     * @return String all information of the posts from the specific user
     */
    public ArrayList<Post> getPostData(String username) {
        sqliteDataBase = this.getWritableDatabase();
        String[] args ={username};
        System.out.println("The logged in username: "+ username);

        ArrayList<Post> results= new ArrayList<Post>();
        String queryStringPost = "SELECT * from sale_posts" +
                " WHERE (sale_posts.post_username=?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryStringPost, args);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Post post = new Post(cursor.getString(0), cursor.getString(2), cursor.getString(1),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            results.add(post);
            cursor.moveToNext();
        }
        cursor.close();
        sqliteDataBase.close();
        return results;
    }
    /**
     * This method adds an item to the database that corresponds with a certain post
     * @param item the inputted item from user
     * @return boolean whether the method works or not
     */
    public boolean addItem(Items item){
        sqliteDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("post_title", item.getPost_title());
        values.put("item_title", item.getItem_title());
        values.put("sale_post_username", item.getSale_post_username());
        values.put("item_category", item.getCategory());
        values.put("item_image", item.getImage());
        values.put("item_description", item.getDescription());
        values.put("item_price", item.getPrice());
        values.put("item_quantity", item.getQuantity());
        sqliteDataBase.insert("items", null, values);
        sqliteDataBase.close();
        return true;
    }

    public String getDates(String postTitle){
        sqliteDataBase = this.getWritableDatabase();
        String[] args ={postTitle};

        String results = "";
        String queryStringPost = "SELECT * from dates" +
                " WHERE (dates.post_title=?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryStringPost, args);
        cursor.moveToFirst();
        int count = 0;
        while(!cursor.isAfterLast()){
            results = results + cursor.getString(0);
            cursor.moveToNext();
            results = results + ", ";
            if (count % 2 == 1){
                results = results + "\n";
            }
            count +=1;
        }
        return results;
    }


    public String getPassword(String username){
        sqliteDataBase = this.getWritableDatabase();
        String[] args ={username};

        String results = "";
        String queryStringPost = "SELECT * from regular_user" +
                " WHERE (regular_user.username=?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryStringPost, args);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            results =  cursor.getString(3);
            cursor.moveToNext();
        }
        String queryStringPost2 = "SELECT * from admin" +
                " WHERE (admin.username=?)";
        Cursor cursor2 = sqliteDataBase.rawQuery(queryStringPost2, args);
        cursor2.moveToFirst();
        while(!cursor2.isAfterLast()){
            results =  results + cursor2.getString(3);
            cursor.moveToNext();
        }
        System.out.println("THE PASSWORD IS "+results);
        return results;
    }

    /**
     * method to split apart the location string into its separate components.
     * @param location the input location string that needs to be split up
     * @return an array of 5 strings in order: address, city, state, zip, country
     */
    public String[] splitLocation(String location){
        String split[] = new String[5];
        split = location.split(":");
        return split;
    }

    /**
     * method to check if the username entered already exists in the database (not case sensitive)
     * @param username the username being searched in the database
     * @return boolean if the username exists or not
     */
    public boolean usernameExists(String username) {
        String[] args = {username};
        ArrayList<String> results = new ArrayList<String>(0);
        String queryString2 = "SELECT username from regular_user" +
                " WHERE (regular_user.username like ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
        try {
            results.add(cursor.getString(0));
        } catch (Exception e) {
            if (results.size() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        }
        return true;
    }

    /**
     * method to check if the post title entered already exists in the database (not case sensitive)
     * @param postName the title of the post being searched in the database
     * @return boolean if the username exists or not
     */
    public boolean postExists(String postName){
        String[] args ={postName};
        ArrayList<String> results= new ArrayList<String>(0);
        String queryString2 = "SELECT post_name from sale_posts" +
                " WHERE (sale_posts.post_name like ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
        try {
            results.add(cursor.getString(0));
        } catch (Exception e) {
            if (results.size() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        }
        return true;
    }

    /**
     * method to check if the item title entered already exists in the database for the sale (not case sensitive)
     * @param postName the title of the post the user is adding the item to
     * @param itemName the title of the item the user is attempting to add
     * @return boolean if the item title exists in the database.
     */
    public boolean itemExists(String postName, String itemName){
        String[] args ={postName, itemName};
        ArrayList<String> results= new ArrayList<String>(0);
        String queryString2 = "SELECT item_title from items WHERE " +
                "((items.post_title = ?) AND (items.item_title like ?))";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
        try {
            results.add(cursor.getString(0));
        } catch (Exception e) {
            if (results.size() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
            return false;
        }
        return true;
    }


    /**
     * Right now this method adds a post to the database
     * but it doesn't add items or dates yet maybe add items should be it's own separate method
     * since it has a lot of its own attributes
     * @param post
     * @return boolean whether this method works or not
     */
    public boolean addPost(Post post) {
        sqliteDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //getOwner() might be the wrong thing to call since we should just know the
        //the right user based on who is loggedIn
        values.put("post_username", post.getOwner());
        values.put("post_name", post.getTitle());
        values.put("sale_location", post.getLocation());
        values.put("sale_description", post.getDescription());
        values.put("sale_time", post.getTime());
        values.put("price_range", post.getPriceRange());
        values.put("image", post.getImage());
        sqliteDataBase.insert("sale_posts", null, values);
        //viewOwnPost(post.getOwner(), post.getTitle());
        sqliteDataBase.close();
        return true;
    }

    /**
     * Method to delete a user from the database
     * @param username the username of the account to delete
     */
    public void deleteUser(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("save_posts", "save_post_username" + "=\"" + username + "\"", null);
        db.delete("save_posts", "sale_post_username" + "=\"" + username + "\"", null);
        db.delete("items", "sale_post_username" + "=\"" + username + "\"", null);
        db.delete("dates", "sale_post_username" + "=\"" + username + "\"", null);
        db.delete("manages", "regular_user_username" + "=\"" + username + "\"", null);
        db.delete("sale_posts", "post_username" + "=\"" + username + "\"", null);
        db.delete("regular_user", "username" + "=\"" + username + "\"", null);
        db.close();
    }

    /**
     * Method to delete a post from a user's account
     * @param username the name of a user
     * @param postName  the name of a post
     */
    public void deletePost(String username, String postName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items", "sale_post_username = \"" + username + "\" AND post_title = \"" + postName + "\"", null);
        db.delete("dates", "sale_post_username = \"" + username + "\" AND post_title = \"" + postName + "\"", null);
        db.delete("sale_posts", "post_username = \"" + username + "\" AND post_name = \"" + postName + "\"", null);
        db.delete("save_posts", "sale_post_username = \"" + username + "\" AND post_name = \"" + postName + "\"", null);
        db.close();
    }

    public void deleteItem(String username, String postName, String itemName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("items", "sale_post_username = \"" + username + "\" AND post_title = \"" + postName + "\"" + "AND item_title = \"" + itemName + "\"", null);
        db.close();
    }


    /**
     * add new account to the database
     * @param student an structure of Account class
     * @return boolean whether this method works or not
     */
    public boolean addAccount(Account student) {
        ContentValues values = new ContentValues();
        values.put("fname", student.getFirstName());
        values.put("lname", student.getLastName());
        values.put("username", student.getUsername());
        values.put("password", student.getPassword());
        values.put("activate", "Y");
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("regular_user", null, values);
        db.close();
        return true;}

    /**
     * This method adds a date to the database that corresponds with a certain post
     * @param date a String that saves the date
     * @param title a String that is the title of a post
     * @param username a String that is the name of a user
     * @return boolean whether this method works or not
     */
    public boolean addDate(String date, String title, String username){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        //fix date
        //values.put("sale_date", dateFormat.format(date));
        values.put("sale_date", date);
        values.put("post_title", title);
        values.put("sale_post_username", username);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("dates", null, values);
        db.close();
        return true;
    }

    /**
     * print out the info of a user
     * @param student an structure of Account class
     */
    public void viewAccount(Account student){
        System.out.println("-------View Account-------");
        System.out.println("First Name: "+a.getFirstName());
        System.out.println("Last Name: "+a.getLastName());
        System.out.println("Username: "+a.getUsername());
        System.out.println("Password: "+a.getPassword());
        System.out.println("Type(A for Admin/U for User): "+a.getType());
        System.out.println("Status(Y for active/N for suspended): "+a.getStatus());
        System.out.println("-------End of View Account-------");
    }

    /**
     * method to search for sales within a given location(city)
     * no longer requires capital letters.
     * @param searchedLocation the location the user is searching for sales in
     * @return an arraylist of all post objects that have a matching location
     */
    public ArrayList<Post> searchByLocation(String searchedLocation){
        String[] args ={"%:"+searchedLocation+":%"};
        ArrayList<Post> results= new ArrayList<Post>(0);
        String queryString2 = "SELECT * from sale_posts" +
                " WHERE ((sale_posts.sale_location) like ?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Post post = new Post(cursor.getString(0), cursor.getString(2), cursor.getString(1),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            results.add(post);
           cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    /**
     * Search the title of posts by date
     * @param date a String that is the date of a post
     * @return an array list of post objects that match search criteria
     */
    public ArrayList<Post> searchByDate(String date){
        String[] args = {date};
        ArrayList<String> posts = new ArrayList<>();
        int count = 0;
        String queryString = "SELECT post_title FROM dates" +
                " WHERE sale_date = ?";
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            posts.add(cursor.getString(0));
            count++;
            cursor.moveToNext();
        }
        ArrayList<Post> results= new ArrayList<Post>(0);
        queryString = "SELECT * from sale_posts" +
                " WHERE post_name =?";
        for(int i = 0; i<posts.size(); i++) {
            String[] args2 = {posts.get(i)};
            cursor = sqliteDataBase.rawQuery(queryString, args2);
            cursor.moveToFirst();
            Post post = new Post(cursor.getString(0), cursor.getString(2), cursor.getString(1),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            results.add(post);
        }
        cursor.close();
        return results;
    }



    /**
     * method to search the database for sales with items in a given category
     * @param category the category that the user wants to search for
     * @return results an arraylist of post objects that have an item in the category.
     */
    public ArrayList<Post> searchByCategory(String category){
        String[] args = {category};
        String query = "SELECT post_title from items WHERE (items.item_category=?)";
        Cursor cursor = sqliteDataBase.rawQuery(query,args);
        cursor.moveToFirst();
        ArrayList<String> garageSale = new ArrayList<String>();
        ArrayList<Post> results = new ArrayList<Post>();
        while(!cursor.isAfterLast()){
            if (!garageSale.contains(cursor.getString(0))) {
                garageSale.add(cursor.getString(0));
                String[] args2 = {cursor.getString(0)};
                String query2 = "SELECT * from sale_posts WHERE (sale_posts.post_name=?)";
                Cursor cursor2 = sqliteDataBase.rawQuery(query2,args2);
                cursor2.moveToFirst();
                while (!cursor2.isAfterLast()){
                    Post post = new Post(cursor2.getString(0), cursor2.getString(2), cursor2.getString(1), cursor2.getString(3),
                            cursor2.getString(4), cursor2.getString(5), cursor2.getString(6));
                    results.add(post);
                    cursor2.moveToNext();
                }
            }
            cursor.moveToNext();
        }
        cursor.close();
        return results;
    }

    /**
     * method to combine all search methods and return results that match all criteria
     * @param location the location the user wants to search for
     * @param date the date the user wants to search for
     * @param category the category the user wants to search for
     * @return results an array list of post objects that match all search criteria
     */

    public ArrayList<Post> searchPosts(String location, String date, String category) {
        ArrayList<Post> locationResults = searchByLocation(location);
        ArrayList<Post> dateResults = searchByDate(date);
        ArrayList<Post> categoryResults = searchByCategory(category);
        int lSize = locationResults.size();
        int dSize = dateResults.size();
        int cSize = categoryResults.size();

        if(location.equals("")){
            lSize = -1;
        }
        if(date.equals("")){
            dSize = -1;
        }
        if(category.equals("")){
            cSize = -1;
        }

        if((lSize<=0) && (dSize<=0) && (cSize<=0)){
            return null;
        }
        else if((lSize==-1) && (dSize==-1)){
            return categoryResults;
        }
        else if((lSize==-1) && (cSize==-1)){
            return dateResults;
        }
        else if((dSize==-1) && (cSize==-1)){
            return locationResults;
        }
        else if(lSize==-1){
            return commonElem(dateResults, categoryResults);
        }
        else if(dSize==-1){
            return commonElem(locationResults, categoryResults);
        }
        else if(cSize==-1){
            return commonElem(locationResults, dateResults);
        }
        else if((lSize!=-1) && (dSize!=-1) && (cSize!=-1)){
            return commonElem(categoryResults, commonElem(locationResults, dateResults));
        }
        return null;
    }

    /**
     * helper method to find common posts in both arrays (used in searchPosts)
     * @param a1 the first array
     * @param a2 the second array
     * @return results an array with all common post objects between a1 and a2
     */
    public ArrayList<Post> commonElem(ArrayList<Post> a1, ArrayList<Post> a2){
        ArrayList<Post> results = new ArrayList<Post>(0);
        for(int i=0; i<a1.size(); i++){
            for(int x =0; x<a2.size(); x++){
                if(a1.get(i).getTitle().equals(a2.get(x).getTitle())){
                    results.add(a1.get(i));
                    a1.remove(i);
                    a2.remove(x);
                }
            }
        }
        return results;
    }

    /**
     * This method adds a post to save_post tables
     * @param title a String that is the title of a post
     * @param username a String that is the name of a user who wishes to save post
     */
    public void savePost(String postOwner,String username, String title){
        ContentValues values = new ContentValues();
        sqliteDataBase = this.getWritableDatabase();
        values.put("sale_post_username",  postOwner);
        values.put("save_post_username", username);
        values.put("post_name", title);
        sqliteDataBase.insert("save_posts", null, values);
        sqliteDataBase.close();
    }

    /**
     * Method to remove a post from a user's saved Post list
     * @param username the name of a user
     * @param postName  the name of a post
     */
    public void removeFromSaved(String post_username, String username, String postName){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("save_posts", "sale_post_username = \"" + post_username + "\" AND post_name = \"" + postName + "\"", null);
    }

    /**
     * This method adds a post to save_post tables
     * @param title a String that is the title of a post
     * @param username a String that is the name of a user who wishes to save post
     */
    public boolean returnListSavedPosts(String username,String title){
        String[] args = {title};
        boolean result = false;
        String queryString = "SELECT * from save_posts" +
                " WHERE (save_posts.post_name = ?)";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, args);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println("LOOK HERE: " + cursor.getString(1));
            System.out.println("LOOK HERE: " + username);
            if(username.equals(cursor.getString(1))) {
                System.out.println("LOOK HERE: " + cursor.getString(1));
                result = true;
            }
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return result;
    }


    /**
     * This method can update the user's password, last name and first name
     * @param password a String that saved user's new password
     * @param lastname a String that saved user's new last name
     * @param firstname a String that saved user's new first name
     * @param username a String that saved user's unique username
     * @return boolean if success, update the information, else, there will be a error popped up
     */
    public boolean editAccount(String password, String lastname, String firstname, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        if (!password.isEmpty()){
            String queryString = "UPDATE regular_user" + " SET password = '" + password + "' WHERE username = '" + username + "'";
            db.execSQL(queryString);
        }

        if (!lastname.isEmpty()){
            String queryString = "UPDATE regular_user" + " SET lname = '" + lastname + "' WHERE username = '" + username + "'";
            db.execSQL(queryString);
        }

        if (!firstname.isEmpty()){
            String queryString = "UPDATE regular_user" + " SET fname = '" + firstname + "' WHERE username = '" + username + "'";
            db.execSQL(queryString);
        }
        db.close();
        return true;
    }

    /**
     * This method will apply the changes of the post to database
     * @param location a String that saves the location of a post
     * @param desc a String that saves the description of a post
     * @param time a String that saves the time of a post
     * @param img a String that saves the image of a post
     * @param price a String that saves the price of a post
     * @param postname a String that saves the post name of a post
     * @param username a String that saves the user name of a post
     * @return a Boolean whether it works or not
     */
    public boolean editPost(String location, String desc, String time, String img, String price, String postname, String username){
        SQLiteDatabase db = this.getWritableDatabase();
        if (!location.equals("::::")){
            String queryString = "UPDATE sale_posts" + " SET sale_location = '" + location+ "' WHERE post_username = '" + username + "' AND post_name = \"" + postname + "\"";
            db.execSQL(queryString);
        }

        if (!desc.isEmpty()){
            String queryString = "UPDATE sale_posts" + " SET sale_description = '" + desc + "' WHERE post_username = '" + username + "' AND post_name = \"" + postname + "\"";
            db.execSQL(queryString);
        }

        if (!time.isEmpty()){
            String queryString = "UPDATE sale_posts" + " SET sale_time = '" + time + "' WHERE post_username = '" + username + "' AND post_name = \"" + postname + "\"";
            db.execSQL(queryString);
        }

        if (!price.isEmpty()){
            String queryString = "UPDATE sale_posts" + " SET price_range = '" + price + "' WHERE post_username = '" + username + "' AND post_name = \"" + postname + "\"";
            db.execSQL(queryString);
        }

        if (!img.isEmpty()){
            String queryString = "UPDATE sale_posts" + " SET image = '" + img + "' WHERE post_username = '" + username + "' AND post_name = \"" + postname + "\"";
            db.execSQL(queryString);
        }
        System.out.println(location);
        db.close();
        return true;
    }

    /**
     * This method will delete all the dates of the specific post from a user
     * @param username a String that saves user name
     * @param postname a String tha saves post name
     * @return
     */
    public String deleteDate(String username, String postname){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM dates WHERE post_title = \""+ postname + "\" AND sale_post_username = \""+ username + "\"";
        db.execSQL(queryString);
        return "All dates are deleted! Please re-enter the date";
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //no need to write the create table query since we are using pre-made database
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Account getAccount(String username) {
        return null;
    }

}
