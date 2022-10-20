package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    public void createPosts(View view){
        startActivity(new Intent(getApplicationContext(), Createpost.class));
        finish();
    }

    public void searchByLocation(View view){
        startActivity(new Intent(getApplicationContext(), SearchByLocation.class));
        finish();
    }



}