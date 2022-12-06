package com.example.garagesalefinder;

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


public class Home extends AppCompatActivity {
    EditText Email,Password;
    Button LoginBtn;
    ProgressBar progressBar;
    Button ReturnBtn;
    Boolean success = false;

    AccountController ac = new AccountController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        //progressBar = findViewById(R.id.progressBar);
        LoginBtn = findViewById(R.id.btnLogin);
        //ReturnBtn = findViewById(R.id.btnReturn);


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
                DataBaseHelperClass dbhc = new DataBaseHelperClass(Home.this);
                String email = Email.getText().toString().trim();//converts user input to string
                String password = Password.getText().toString().trim();//converts user input to string
                boolean done = true;

                if(TextUtils.isEmpty(email)){//verifies a username was entered
                    Email.setError("Username is Required.");
                    Toast.makeText(Home.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){//verifies a password was entered
                    Password.setError("Password is Required.");
                    Toast.makeText(Home.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
                System.out.println("Username/email: " + email + "Password: " + password);
                if(dbhc.login(email, password)){
                    //System.out.println("Success! Inside if statement! Username "+ email + "Password: "+password);
                    //startActivity(new Intent(getApplicationContext(), Menu.class));
                    //finish();
                    DataBaseHelperClass dbhc2 = new DataBaseHelperClass(Home.this);
                    dbhc2.getUser(email);
                    Intent intent = new Intent(Home.this,Menu.class);
                    intent.putExtra("username",email);
                    intent.putExtra("password", password);
                    dbhc2.close();
                    startActivity(intent);
                    finish();
                }
                else{//not the best way to display an error message for a non-existent user, but it suffices for now
                    Toast.makeText(Home.this, "Login Failed", Toast.LENGTH_LONG).show();
                    return;
                }
//                progressBar.setVisibility(View.VISIBLE);//displays login progress bar
            }
        });
    }



    public void register(View view){
        startActivity(new Intent(getApplicationContext(), Register.class));
        finish();
    }

}