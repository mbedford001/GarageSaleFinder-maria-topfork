package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;

public class SearchByLocation extends AppCompatActivity {

    DataBaseHelperClass dbhc = new DataBaseHelperClass(SearchByLocation.this);
    EditText location;
    Button searchButton;
    ArrayList<Post> results = new ArrayList<Post>();

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
                    return;
                }
                results = dbhc.searchByLocation(inputLocation);
                Intent intent = new Intent(SearchByLocation.this,ViewSearchResults.class);
                intent.putExtra("results",results);
                startActivity(intent);
                finish();
            }
        });
    }

    public void goMenu(View view){
        startActivity(new Intent(getApplicationContext(), Menu.class));
        finish();
    }
}