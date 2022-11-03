package com.example.garagesalefinder.controllers;

import android.content.ContentValues;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.*;
import com.example.garagesalefinder.people.Account;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.garagesalefinder.PostStuff.Post;

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

        if (cursor.getCount()>=1 || cursor2.getCount()>=1) {

            //System.out.println("We found him. We logged in!"+cursor.getCount());

            access = true;
            String queryString3 = "SELECT fname from regular_user" +
                    " WHERE (regular_user.username = ? and regular_user.password = ?)";
            String queryString4 = "SELECT fname from admin" +
                    " WHERE (admin.username = ? and admin.password = ?)";
            Cursor cursor3 = sqliteDataBase.rawQuery(queryString3, args);
            Cursor cursor4 = sqliteDataBase.rawQuery(queryString4, args);
            if (cursor4.getCount() >= 1){

                String queryString5 = "SELECT * from admin" +
                        " WHERE (admin.username = ? and admin.password = ?)";

                Cursor cursor5 = sqliteDataBase.rawQuery(queryString5, args);

                //c1.moveToFirst();
                cursor5.moveToFirst();
                while(!cursor5.isAfterLast()){
                    a = new com.example.garagesalefinder.people.Admin(cursor5.getString(0),cursor5.getString(1),cursor5.getString(2),cursor5.getString(3),'A','Y');
                    cursor5.moveToNext();
                }

                cursor4.close();
                cursor5.close();
                cursor.close();
                viewAccount(a);
            }
            else{
                String queryString6 = "SELECT * from regular_user" +
                        " WHERE (regular_user.username = ? and regular_user.password = ?)";
                Cursor cursor6 = sqliteDataBase.rawQuery(queryString6, args);

                //c1.moveToFirst();
                cursor6.moveToFirst();
                while(!cursor6.isAfterLast()){
                    a = new com.example.garagesalefinder.people.User(cursor6.getString(0),cursor6.getString(1),cursor6.getString(2),cursor6.getString(3),'U','Y');
                    cursor6.moveToNext();
                }
                cursor6.close();
                viewAccount(a);
            }
            //System.out.println("cursor string2: "+ cursor2.moveToFirst());

        }
        else{
           System.out.println("-----------FAILED LOGIN-----------");
        }

        System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");
        System.out.println("Results for Sartell, 2023-05-21, Towels:");
        System.out.println(searchPosts("Sartell", "2023-05-21", "Towels").size());
        System.out.println("Results for Sartell, null, Towels:");
        System.out.println(searchPosts("Sartell", "", "Towels").size());
        System.out.println("Results for null, 2023-05-21, null");
        System.out.println(searchPosts("", "2023-05-21", "").size());
        System.out.println("*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&*&");

        sqliteDataBase.close();


        return access;
    }


    /**
     * this method allows a user to view their own post so they can edit it if needed
     * @param
     * @return
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
        sqliteDataBase.close();
        return true;
    }
    /**
     * This method adds an item to the database that corresponds with a certain post
     * @param item the inputted item from user
     * @return
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



    /**
     * Right now this method adds a post to the database
     * but it doesn't add items or dates yet maybe add items should be it's own separate method
     * since it has a lot of its own attributes
     * @param post
     * @return
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
    }



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
     * @param date
     * @return
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
     * @param searchedLocation the location the user is searching for sales in
     * @return an arraylist of all post objects that have a matching location
     */
    public ArrayList<Post> searchByLocation(String searchedLocation){
        String[] args ={searchedLocation};
        ArrayList<Post> results= new ArrayList<Post>(0);
        String queryString2 = "SELECT * from sale_posts" +
                " WHERE (sale_posts.sale_location=?)";
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
        while(!cursor.isAfterLast()) {
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
