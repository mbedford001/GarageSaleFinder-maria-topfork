package com.example.garagesalefinder.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.*;
import com.example.garagesalefinder.people.Account;
import com.example.garagesalefinder.people.User;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.ResultSet;

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
        System.out.println("cursor string: "+ cursor.moveToFirst());
        String queryString2 = "SELECT fname from regular_user" +
                " WHERE (regular_user.username=? and regular_user.password=?)";
        Cursor cursor2 = sqliteDataBase.rawQuery(queryString2, args);
        System.out.println("cursor string2: "+ cursor2.moveToFirst());

        if (cursor.getCount()>=1 || cursor2.getCount()>=1) {
            System.out.println("We found him. We logged in!"+cursor.getCount());
            System.out.println("cursor2"+cursor2.getCount());
            access = true;
        }
        else{
            System.out.println("Sad face. No log in.");
        }
        sqliteDataBase.close();
        return access;
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
/**
    public boolean addPost(Post student, int d, int i) {
        ContentValues values = new ContentValues();
        values.put("post_username", post.getFirstName());
        values.put("post_name", post.getLastName());
        values.put("sale_location", post.getUsername());
        values.put("sale_description", post.getPassword());
        values.put("sale_time", "Y");
        values.put("price_range", student.getPassword());
        values.put("image", "Y");
        while()
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("regular_user", null, values);
        db.close();
        return true;
    }
 */

    public void viewAccount(Account student){
        String queryString = "SELECT * from regular_user" +
                " WHERE (regular_user.username = "+ "\""+student.getUsername()+"\""+")";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        System.out.print("Cursor for viewAccount: "+ cursor.moveToFirst());
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
