package com.example.garagesalefinder;
import java.util.*;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.garagesalefinder.controllers.AccountController;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;
import com.example.garagesalefinder.people.*;

/**
 * Class allows user to login to the application. It then ensures that the user
 * has the correct username and password combinaiton. If not it will send a message to the user.
 * Once a user is logged in, it passes the user data to the other classes.
 */


/** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *  This file has been merged into Home.java and is no longer used!
 *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 */

public class Login extends AppCompatActivity {
    EditText Email,Password;
    Button LoginBtn;
    ProgressBar progressBar;
    Button ReturnBtn;
    Boolean success = false;

    AccountController ac = new AccountController();

    //DataBaseHelperClass dbhc = new DataBaseHelperClass(Login.this);

    @SuppressLint("MissingInflatedId")

    /**
     * Method runs when class is first created
     * @param savedInstanceState a bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        LoginBtn = findViewById(R.id.btnLogin);
        ReturnBtn = findViewById(R.id.btnReturn);

        /** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         *  This file has been merged into Home.java and is no longer used!
         *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */

        /**
         * When button is clicked the user is returned to the Home Page
         * @param view v
         */
        ReturnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Home.class));
                finish();
            }
        });

        /** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         *  This file has been merged into Home.java and is no longer used!
         *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */

        /**
         * When button is clicked the user attempts to login.
         * If the user logs in with incorrect credentials, the system will inform them
         * If the user logs in with correct credentials, the system will move them to the Menu page
         * And pass the intent to the Menu classes
         * @param view v
         */
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelperClass dbhc = new DataBaseHelperClass(Login.this);
                String email = Email.getText().toString().trim();//converts user input to string
                String password = Password.getText().toString().trim();//converts user input to string
                boolean done = true;

                if(TextUtils.isEmpty(email)){//verifies a username was entered
                    Email.setError("Username is Required.");
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){//verifies a password was entered
                    Password.setError("Password is Required.");
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.println("Username/email: " + email + "Password: " + password);
                if(dbhc.login(email, password)){
                    //System.out.println("Success! Inside if statement! Username "+ email + "Password: "+password);
                    //startActivity(new Intent(getApplicationContext(), Menu.class));
                    //finish();
                    Intent intent = new Intent(Login.this,Menu.class);
                    intent.putExtra("username",email);
                    intent.putExtra("password", password);
                    startActivity(intent);
                    finish();
                }
                else{//not the best way to display an error message for a non-existent user, but it suffices for now
                    Toast.makeText(Login.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);//displays login progress bar
            }
        });

        /** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         *  This file has been merged into Home.java and is no longer used!
         *  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
         */

    }
}