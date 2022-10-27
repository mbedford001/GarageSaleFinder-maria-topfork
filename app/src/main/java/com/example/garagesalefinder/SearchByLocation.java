package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchByLocation extends AppCompatActivity {

    EditText location;
    Button searchButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);
        location = findViewById(R.id.inputLocation);
        searchButton = findViewById(R.id.btnSearchByLocation);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String inputLocation = location.getText().toString().trim();

                if(TextUtils.isEmpty(inputLocation)){
                    location.setError("Location is required");
                }
                //this is where search method would be called
            }


        });
    }
}