package com.example.garagesalefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.garagesalefinder.controllers.AccountController;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;
import com.example.garagesalefinder.people.*;


import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.controllers.DataBaseHelperClass;

public class Register extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Username;
    EditText Password;
    Button ReturnBtn;
    Button ReturnHomeBtn;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(Register.this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirstName = findViewById(R.id.inputFName);
        LastName = findViewById(R.id.inputLName);
        Username = findViewById(R.id.inputUsername);
        Password = findViewById(R.id.inputPassword);
        ReturnBtn = findViewById(R.id.btnReturn);
        ReturnHomeBtn = findViewById(R.id.btnReturnHome);

        ReturnHomeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        });

        ReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = FirstName.getText().toString().trim();//converts user input to string
                String lname = LastName.getText().toString().trim();//converts user input to string
                String uname = Username.getText().toString().trim();//converts user input to string
                String pname = Password.getText().toString().trim();//converts user input to string

                if(TextUtils.isEmpty(fname)){//verifies a username was entered
                    FirstName.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(pname)){//verifies a password was entered
                    Password.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(lname)){//verifies a username was entered
                    LastName.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(uname)){//verifies a password was entered
                    Username.setError("Password is Required.");
                    return;
                }

                //System.out.println("Username/email: " + email + "Password: " + password);
                if(dbhc.addAccount(new User(fname, lname,uname,pname,'U','Y'))){
                    //System.out.println("inside if statement! Username "+ email + "Password: "+password);
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }

            }
        });
    }


}