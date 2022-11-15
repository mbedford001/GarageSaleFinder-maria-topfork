package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Menu extends AppCompatActivity {

    public TextView UserText;
    Button ViewAccountBtn;
    Button LogoutBtn;

    @SuppressLint("MissingInflatedId")
    @Override
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

    public void createPosts(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,Createpost.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }

    public void searchByLocation(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,SearchByLocation.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        //startActivity(new Intent(getApplicationContext(), SearchByLocation.class));
        startActivity(intent);
        finish();
    }

    public void viewMyPosts(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Menu.this,ViewMyPosts.class);
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        startActivity(intent);
        finish();
    }

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