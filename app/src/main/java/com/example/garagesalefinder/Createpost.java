package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

public class Createpost extends AppCompatActivity {

    EditText location;
    EditText title;
    EditText description;
    EditText time;
    EditText image;
    EditText priceRange;
    Button createButton;

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

                Post p = new Post("mShort", pLocation, pTitle, pDescription, pTime, pPrice, pImage);
                dbhc.addPost(p);
            }
        });
    }
}