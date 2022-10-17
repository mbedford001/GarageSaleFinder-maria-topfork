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

public class Login extends AppCompatActivity {
    EditText Email,Password;
    Button LoginBtn;
    ProgressBar progressBar;

    AccountController ac = new AccountController();
    DataBaseHelperClass dbhc = new DataBaseHelperClass(Login.this);

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        LoginBtn = findViewById(R.id.btnLogin);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Email.getText().toString().trim();//converts user input to string
                String password = Password.getText().toString().trim();//converts user input to string

                if(TextUtils.isEmpty(email)){//verifies a username was entered
                    Email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){//verifies a password was entered
                    Password.setError("Password is Required.");
                    return;
                }
                System.out.println("Username/email: " + email + "Password: " + password);
                if(dbhc.login(email, password)){
                    System.out.println("inside if statement! Username "+ email + "Password: "+password);
                    startActivity(new Intent(getApplicationContext(), Menu.class));
                    finish();
                }
                else{//not the best way to display an error message for a non-existent user, but it suffices for now
                    Email.setError("Email could be incorrect");
                    Password.setError("Password could be incorrect");
                }
                progressBar.setVisibility(View.VISIBLE);//displays login progress bar

            }
        });
    }
}