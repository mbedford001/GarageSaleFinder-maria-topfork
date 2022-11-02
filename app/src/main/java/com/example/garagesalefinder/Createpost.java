package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;
import com.example.garagesalefinder.people.Account;

public class Createpost extends AppCompatActivity {

    EditText location;
    EditText title;
    EditText description;
    EditText time;
    EditText image;
    EditText priceRange;
    Button createButton;
    public TextView UserText;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(Createpost.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpost);
        location = findViewById(R.id.inputLocation);
        title = findViewById(R.id.inputPostName);
        description = findViewById(R.id.inputDescription);
        time = findViewById(R.id.inputTime);
        image = findViewById(R.id.inputImage);
        priceRange = findViewById(R.id.inputPrice);
        createButton = findViewById(R.id.btnCreate);

        String username = getIntent().getStringExtra("username");
        //UserText = findViewById(R.id.username);
        //UserText.setText(username);


        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String pLocation = location.getText().toString().trim();
                String pTitle = title.getText().toString().trim();
                String pDescription = description.getText().toString().trim();
                String pTime = time.getText().toString().trim();
                String pImage = image.getText().toString().trim();
                String pPrice = priceRange.getText().toString().trim();

                if(TextUtils.isEmpty(pLocation)){//verifies a location was entered
                    location.setError("Location is required.");
                    return;
                }

                if(TextUtils.isEmpty(pTitle)){
                    title.setError("Title is required.");
                    return;
                }

                if(TextUtils.isEmpty(pDescription)){
                    description.setError("Description is required.");
                    return;
                }


                if (TextUtils.isEmpty(pTime)) {
                    time.setError("Time is required.");
                    return;
                }
                Post p = new Post(username, pLocation, pTitle, pDescription, pTime, pPrice, pImage);

                dbhc.addPost(p);
                //startActivity(new Intent(getApplicationContext(), ViewPost.class));
                //finish();
                moveToAddDates(v);
            }

        });

    }

    public void returnHomeFromPost(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Createpost.this,Menu.class);
        intent.putExtra("username",username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }
    public void moveToAddDates(View view){
        Intent intent = new Intent(getApplicationContext(), AddDates.class);
        String username = getIntent().getStringExtra("username");
        intent.putExtra("username",username);
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(), AddDates.class));
        finish();
    }
}