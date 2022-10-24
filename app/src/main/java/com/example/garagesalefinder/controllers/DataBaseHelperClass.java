package com.example.garagesalefinder.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
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
     * EXAMPLE METHOD for getting data using queries
     * can be deleted eventually
     */
    public String getUsernameFromDB(){
        String query = "SELECT username FROM" + TABLE_1;
        Cursor cursor = sqliteDataBase.rawQuery(query, null);
        String username = null;
        if (cursor.getCount() > 0) {
            if(cursor.moveToFirst()){
                do{
                    username = cursor.getString(0);
                }
                while (cursor.moveToNext());
            }
        }
        return username;
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
        searchByLocation("Sartell");
        viewOwnPost("mShort","Monster Sale");
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
     *
     *
     */
    /**
     public boolean addAccount(Account a) {
     sqliteDataBase = this.getWritableDatabase();
     String queryString = "INSERT INTO regular_user (fname, lname, username, password, activate) VALUES " +
     "(" + "\""+a.getFirstName() +"\""+ ", " +"\""+ a.getLastName() +"\""+ ", " +"\""+ a.getUsername() +"\""+ ", "+"\""+ a.getPassword() +"\""+ ", " +"\'"+ 'Y'+"\')";
     String queryString2 = "INSERT INTO regular_user" +" (" + a.getFirstName() + ", " + a.getLastName() + ", " + a.getUsername() + ", "+ a.getPassword() + ", " + 'Y'+")" +
     " VALUES (fname, lname, username, password, activate)";
     System.out.println(queryString);
     Cursor cursor = sqliteDataBase.rawQuery(queryString, null);
     sqliteDataBase.close();
     return true;
     }
     */




    /**
     * Right now this method adds a post to the database
     * but it doesn't add items or dates yet maybe add items should be it's own separate method
     * since it has a lot of its own attributes
     * @param post
     * @return
     */
    public boolean addPost(Post post) {
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
        //have to deal with items and dates
        //that what the int d and int i might help with
        //d and i should default to 0 in the UI
        //maybe have dates get inputted as 1 long string broken up with commas
        //and then i split on the comma and for a list of dates that can then be inserted
        //1 by 1??
        //int count=0;
        //while(count < d){
        //date.getDates();
        //  values.put("date", );
        //}
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("sale_posts", null, values);
        //viewOwnPost("mShort","Monster Sale");
        viewOwnPost(post.getOwner(), post.getTitle());
        db.close();
        return true;
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

    public void searchByLocation(String location){
        String[] args ={location};
        String queryString2 = "SELECT * from sale_posts" +
                " WHERE (sale_posts.sale_location=?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
        System.out.println("--------Start of Search Results--------");
        while(!cursor.isAfterLast()){
           System.out.println("Title of Sale: "+cursor.getString(1));
           System.out.println("Location of Sale: "+cursor.getString(2));
           System.out.println("Description of Sale: "+cursor.getString(3));
           System.out.println("Time of Sale: "+cursor.getString(4));
           System.out.println("Price Range of Sale: "+cursor.getString(5));
           System.out.println("------------------");
           cursor.moveToNext();
        }
        System.out.println("----------End of Search Result-------------");
        cursor.close();
    }

    public void searchByDate(Date date){
        String[] args ={};
        String queryString2 = "SELECT * from dates" +
                " WHERE (sale_posts.sale_location=?)";
        Cursor cursor = sqliteDataBase.rawQuery(queryString2, args);
        cursor.moveToFirst();
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