package com.example.garagesalefinder.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.database.sqlite.SQLiteOpenHelper;

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
        sqliteDataBase.close();
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
        String outFileName = DB_PATH + DATABASE_NAME;
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
        System.out.println("myPath: "+myPath);
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
        String[] args = {username, password, username, password};
        String queryString = "SELECT count(*) from admin, regular_user" +
                " WHERE (admin.username = ? and admin.password = ?) or (regular_user.username=? and regular_user.password=?)";
        sqliteDataBase = this.getWritableDatabase();
        Cursor cursor = sqliteDataBase.rawQuery(queryString, args);
        System.out.println("cursor string: "+ cursor.moveToFirst());
        if (cursor != null && cursor.moveToFirst()){
            access = true;
        }
        sqliteDataBase.close();
        return access;
    }

    /**
     *
     *
     */
    public boolean addAccount(Account a) {
        sqliteDataBase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("fname", regular_user.getFirstName());


        String queryString = "INSERT INTO regular_user (fname, lname, username, password, activate) VALUES " +
                "(" + "\""+a.getFirstName() +"\""+ ", " +"\""+ a.getLastName() +"\""+ ", " +"\""+ a.getUsername() +"\""+ ", "+"\""+ a.getPassword() +"\""+ ", " +"\'"+ 'Y'+"\')";
        String queryString2 = "INSERT INTO regular_user" +" (" + a.getFirstName() + ", " + a.getLastName() + ", " + a.getUsername() + ", "+ a.getPassword() + ", " + 'Y'+")" +
                " VALUES (fname, lname, username, password, activate)";
        System.out.println(queryString);
        Cursor cursor = sqliteDataBase.rawQuery(queryString, null);
        sqliteDataBase.close();
        return true;
    }

        /**
         * This method is called the first time the database is accessed.
         * It generates the tables for the database
         * @param db
         */
        @Override
    public void onCreate(SQLiteDatabase db) {
        /**
       String createTable1 = "CREATE TABLE admin (fname TEXT NOT NULL, lname TEXT NOT NULL," +
               "username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, PRIMARY KEY(username))";
       String createTable2 ="CREATE TABLE dates (sale_date DATE NOT NULL, post_title TEXT NOT NULL," +
               "sale_post_username TEXT NOT NULL, FOREIGN KEY(sale_post_username, post_title) REFERENCES sale_posts (post_username,post_name), " +
               "PRIMARY KEY(sale_date,post_title,sale_post_username))";
       String createTable3 ="CREATE TABLE items ( post_title TEXT NOT NULL,item_title TEXT NOT NULL UNIQUE," +
               "sale_post_username TEXT NOT NULL, item_category TEXT, item_image TEXT," +
               "item_description TEXT, item_price TEXT, item_quantity TEXT, " +
               "FOREIGN KEY(sale_post_username,post_title) REFERENCES sale_posts(post_username, post_name)," +
               "PRIMARY KEY(post_title,item_title,sale_post_username))";
       String createTable4 ="CREATE TABLE manages (admin_username TEXT NOT NULL, regular_user_username TEXT NOT NULL," +
               "FOREIGN KEY(regular_user_username) REFERENCES regular_user(username)," +
               "FOREIGN KEY(admin_username) REFERENCES admin(username)," +
               "PRIMARY KEY(admin_username,regular_user_username))";
       String createTable5 ="CREATE TABLE regular_user (fname TEXT NOT NULL, lname TEXT NOT NULL," +
               "username TEXT NOT NULL UNIQUE, password TEXT NOT NULL, activate CHAR NOT NULL," +
               "PRIMARY KEY(username))";
       String createTable6 ="CREATE TABLE sale_posts (post_username TEXT NOT NULL," +
               "post_name TEXT NOT NULL UNIQUE, sale_location TEXT NOT NULL, sale_description TEXT," +
               "sale_time TEXT NOT NULL, price_range TEXT, image TEXT," +
               "PRIMARY KEY(post_username,post_name)," +
               "FOREIGN KEY(post_username) REFERENCES regular_user(username))";
       String createTable7 ="CREATE TABLE save_posts (sale_post_username TEXT NOT NULL," +
               "save_post_username TEXT NOT NULL, post_name TEXT NOT NULL," +
               "FOREIGN KEY(save_post_username) REFERENCES regular_user(username)," +
               "FOREIGN KEY(sale_post_username, post_name) REFERENCES sale_posts(post_username, post_name)," +
               "PRIMARY KEY(sale_post_username,post_name,save_post_username))";
       db.execSQL(createTable1);
       db.execSQL(createTable2);
       db.execSQL(createTable3);
       db.execSQL(createTable4);
       db.execSQL(createTable5);
       db.execSQL(createTable6);
       db.execSQL(createTable7);
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Account getAccount(String username) {
        return null;
    }
}

