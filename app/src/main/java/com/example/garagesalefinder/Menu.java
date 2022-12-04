package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class contains all functions and buttons for main menu page
 *
 * @author  Maria Bedford, Jack Buczak, Ethan Sutton, Noah Halonen, Hongtao Wang, Keiley Maahs, Kristiana Anderson
 * @since   2022-10-04
 */
public class Menu extends AppCompatActivity {

    public TextView UserText;
    Button ViewAccountBtn;
    Button LogoutBtn;
    Button ViewAPBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    /**
     * all functions and buttons will be loaded here
     * @param savedInstanceState in a Bundle structure that saves instance state
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        LogoutBtn = findViewById(R.id.btnLogout);
        ViewAPBtn = findViewById(R.id.viewAPBtn);
        DataBaseHelperClass dbhc = new DataBaseHelperClass(Menu.this);

        String username = getIntent().getStringExtra("username");
        //List<Post> allP = dbhc.viewAllOwnPosts(username);
        List<Post> savedP = dbhc.viewSavedPosts(username);
        LocalDate date = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = java.time.LocalDate.now();
        }

        List<String> dates = new ArrayList<String>();
        for (int i = 1; i < 10; i++){
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                String l = String.valueOf(date.plusDays(i));
                dates.add(l);
            }
        }

        List<String> allDates = new ArrayList<String>();
        List<Post> corrPosts = new ArrayList<Post>();
        for (Post p: savedP){
            String[] split = (dbhc.getDates(p.getTitle())).split(", ");
            for (String s: split){
                allDates.add((s));
                corrPosts.add(p);
            }
        }

        int i = -1;
        List<Integer> notifs = new ArrayList<Integer>();
        for (String s: allDates){
            System.out.println(s);
            i = i + 1;
            if (s.length() == 10){
                for (String d: dates){
                    if (s.equals(d)) {
                        notifs.add(i);
                    }
                }
            }
        }

        for (int d: notifs){
            Post p = corrPosts.get(d);
            notifyPost(String.valueOf(d), p);
        }

        System.out.println(notifs);

        LogoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Menu.this,Home.class));
                finish();
            }

        });

        ViewAPBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(Menu.this,ViewAllPosts.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * create the page for user to view their own posts
     * @param view a View structure that saves page view
     */
//    public void viewAllPosts(View view){
//        String username = getIntent().getStringExtra("username");
//        String password = getIntent().getStringExtra("password");
//        Intent intent = new Intent(Menu.this,ViewAllPosts.class);
//        intent.putExtra("username",username);
//        intent.putExtra("password",password);
//        startActivity(intent);
//        finish();
//    }

    /**
     * create the page for user to creating post
     * @param view a View structure that saves page view
     */
    public void createPosts(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,Createpost.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }

    /**
     * create the page for user to search post by location
     * @param view a View structure that saves page view
     */
    public void searchByLocation(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,SearchByLocation.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        System.out.println("7969697697"+ username +"7698798798");
        //startActivity(new Intent(getApplicationContext(), SearchByLocation.class));
        startActivity(intent);
        finish();
    }

    /**
     * create the page for user to view their own posts
     * @param view a View structure that saves page view
     */
    public void viewMyPosts(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,ViewMyPosts.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }

    /**
     * create the page for user to view their account page
     * @param view a View structure that saves page view
     */
    public void viewAccount(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,ViewAccount.class);

        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }

    public void notifyPost(String id, Post p) {
        NotificationManager manager = null;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            manager = getSystemService(NotificationManager.class);
            //System.out.println("HELLOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My Notification", "My Notification", NotificationManager.IMPORTANCE_DEFAULT);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(Menu.this, "My Notification");
        builder.setContentTitle("Upcoming Garage Sale!");
        builder.setContentText("Less than 10 days until " + p.getTitle());
        builder.setSmallIcon(R.drawable.bell);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(Menu.this);
        managerCompat.notify(1, builder.build());
    }

}