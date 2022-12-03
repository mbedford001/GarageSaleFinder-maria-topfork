package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garagesalefinder.controllers.DataBaseHelperClass;
import com.example.garagesalefinder.people.User;

public class EditAccount extends AppCompatActivity {

    EditText FirstName;
    EditText LastName;
    EditText Password;
    Button SaveBtn;
    Button ReturnBtn;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(EditAccount.this);
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);
        FirstName = findViewById(R.id.inputFName);
        LastName = findViewById(R.id.inputLName);
        Password = findViewById(R.id.inputPassword);
        ReturnBtn = findViewById(R.id.btnReturn);
        SaveBtn = findViewById(R.id.btnSave);
        String username = getIntent().getStringExtra("username");
        //FirstName.setText(username);

        /**
         * Button returns to View-Account page
         * @param view v
         */
        ReturnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(EditAccount.this, ViewAccount.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Button that saves the update to database
         * @param view v
         */
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fname = FirstName.getText().toString().trim();//converts user input to string
                String lname = LastName.getText().toString().trim();//converts user input to string
                String pass = Password.getText().toString().trim();//converts user input to string

                if(TextUtils.isEmpty(fname) && TextUtils.isEmpty(lname) && TextUtils.isEmpty(pass)){//verifies a username was entered
                    FirstName.setError("Please fill in the information you want to change");
                    LastName.setError("Please fill in the information you want to change");
                    Password.setError("Please fill in the information you want to change");
                    return;
                }

                if(dbhc.editAccount(pass, lname,fname, username)){
                    //System.out.println("inside if statement! Username "+ email + "Password: "+password);
                    Intent intent = new Intent(EditAccount.this, Home.class);
                    startActivity(intent);
                    //startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                }
            }
        });
    }


}