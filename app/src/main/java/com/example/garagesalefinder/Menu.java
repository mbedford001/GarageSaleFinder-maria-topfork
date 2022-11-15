package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        LogoutBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Menu.this,Login.class));
                finish();
            }
        });
    }

    /**
     * create the page for user to creating post
     * @param view a View structure that saves page view
     */
    public void createPosts(View view){
        String username = getIntent().getStringExtra("username");
        Intent intent = new Intent(Menu.this,Createpost.class);
        intent.putExtra("username",username);
        startActivity(intent);
        finish();
    }

    /**
     * create the page for user to search post by location
     * @param view a View structure that saves page view
     */
    public void searchByLocation(View view){
        startActivity(new Intent(getApplicationContext(), SearchByLocation.class));
        finish();
    }

    /**
     * create the page for user to view their own posts
     * @param view a View structure that saves page view
     */
    public void viewMyPosts(View view){
        String username = getIntent().getStringExtra("username");
        Intent intent = new Intent(Menu.this,ViewMyPosts.class);
        intent.putExtra("username",username);
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

}