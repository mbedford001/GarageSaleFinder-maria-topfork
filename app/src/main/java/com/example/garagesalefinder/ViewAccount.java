package com.example.garagesalefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.controllers.DataBaseHelperClass;
import com.example.garagesalefinder.people.User;


public class ViewAccount extends AppCompatActivity {

    public TextView UserText, PasswordText, FirstText, LastText;
    Button viewProfile;
    Button ViewSPBtn;
    Button EditBtn;
    User user;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewAccount.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        String username = getIntent().getStringExtra("username");
        System.out.println(username);
        UserText = findViewById(R.id.username);
        UserText.setText(username);
        ViewSPBtn = findViewById(R.id.viewSPBtn);
        EditBtn = findViewById(R.id.editBtn);

        String password = getIntent().getStringExtra("password");
        PasswordText = findViewById(R.id.password);
        PasswordText.setText(dbhc.getPassword(username));

        user = dbhc.getUser(username);
        String first = user.getFirstName();
        FirstText = findViewById(R.id.first);
        FirstText.setText(first);

        String last = user.getLastName();
        LastText = findViewById(R.id.last);
        LastText.setText(last);

        ViewSPBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewAccount.this,ViewSavedPosts.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });

        EditBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewAccount.this,EditAccount.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                startActivity(intent);
                finish();
            }
        });
    }
    public void go(View view){
        TextView output = findViewById(R.id.textView);
        output.setText("You tapped the button!");
    }

    public void deleteAccount(View view){
        String username = getIntent().getStringExtra("username");
        dbhc.deleteUser(username);
        startActivity(new Intent(getApplicationContext(), Home.class));
        finish();
    }

    public void returnHomeFromView(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(ViewAccount.this,Menu.class);
        intent.putExtra("username",username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

}