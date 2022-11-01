package com.example.garagesalefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.controllers.DataBaseHelperClass;


public class ViewAccount extends AppCompatActivity {

    public TextView UserText, PasswordText;
    Button viewProfile;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewAccount.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);

        String username = getIntent().getStringExtra("username");
        UserText = findViewById(R.id.username);
        UserText.setText(username);

        String password = getIntent().getStringExtra("password");
        PasswordText = findViewById(R.id.password);
        PasswordText.setText(password);
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

}