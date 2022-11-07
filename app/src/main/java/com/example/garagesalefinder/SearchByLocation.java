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
    EditText date;
    EditText category;
    Button searchButton;
    Button returnBtn;
    ArrayList<Post> results = new ArrayList<Post>(0);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_by_location);
        location = findViewById(R.id.inputLocation);
        date = findViewById(R.id.inputDate);
        category = findViewById(R.id.inputCategory);
        searchButton = findViewById(R.id.btnSearchByLocation);
        returnBtn = findViewById(R.id.btnReturn);

        searchButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String inputLocation = location.getText().toString().trim();
                String inputDate = date.getText().toString().trim();
                String inputCategory = category.getText().toString().trim();

                if(TextUtils.isEmpty(inputLocation)){
                    inputLocation = "";
                }
                if(TextUtils.isEmpty(inputDate)){
                    inputDate = "";
                }
                if(TextUtils.isEmpty(inputCategory)){
                    inputCategory = "";
                }

                results = dbhc.searchPosts(inputLocation, inputDate, inputCategory);
                boolean works = true;
                try{
                    results.size();
                }
                catch(Exception e){
                    works = false;
                    location.setError("No results matching criteria");
                    date.setError("No results matching criteria");
                    category.setError("No results matching criteria");
                }

                if(works) {
                    Intent intent = new Intent(SearchByLocation.this, ViewSearchResults.class);
                    intent.putExtra("results", results);
                    startActivity(intent);
                    finish();
                }
            }

        });

        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), Menu.class));
                finish();
            }
        });
    }

//    public void goMenu(View view){
//        startActivity(new Intent(getApplicationContext(), Menu.class));
//        finish();
//    }
}