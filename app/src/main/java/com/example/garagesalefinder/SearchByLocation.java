package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;

public class SearchByLocation extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    DataBaseHelperClass dbhc = new DataBaseHelperClass(SearchByLocation.this);
    EditText location;
    EditText date;
    //EditText category;
    String category;
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
        //category = findViewById(R.id.inputCategory);
        searchButton = findViewById(R.id.btnSearchByLocation);
        returnBtn = findViewById(R.id.btnReturn);

        searchButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String inputLocation = location.getText().toString().trim();
                String inputDate = date.getText().toString().trim();
                String inputCategory = category;

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
                    //category.setError("No results matching criteria");
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

    public void showCategoryChoices(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_category);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.category1:
                Toast.makeText(this, "Toys was selected", Toast.LENGTH_SHORT).show();
                category = "Toys";
                return true;
            case R.id.category2:
                Toast.makeText(this, "Clothing was selected", Toast.LENGTH_SHORT).show();
                category = "Clothing";
                return true;
            case R.id.category3:
                Toast.makeText(this, "Furniture was selected", Toast.LENGTH_SHORT).show();
                category = "Furniture";
                return true;
            case R.id.category4:
                Toast.makeText(this, "Jewelry was selected", Toast.LENGTH_SHORT).show();
                category = "Jewelry";
                return true;
            case R.id.category5:
                Toast.makeText(this, "Antique was selected", Toast.LENGTH_SHORT).show();
                category = "Antique";
                return true;
            case R.id.category6:
                Toast.makeText(this, "Arts and Crafts was selected", Toast.LENGTH_SHORT).show();
                category = "Arts and Crafts";
                return true;
            case R.id.category7:
                Toast.makeText(this, "Sport was selected", Toast.LENGTH_SHORT).show();
                category = "Sport";
                return true;
            case R.id.category8:
                Toast.makeText(this, "Books was selected", Toast.LENGTH_SHORT).show();
                category = "Books";
                return true;
            case R.id.category9:
                Toast.makeText(this, "Electronics was selected", Toast.LENGTH_SHORT).show();
                category = "Electronics";
                return true;
            case R.id.category10:
                Toast.makeText(this, "Miscellaneous was selected", Toast.LENGTH_SHORT).show();
                category = "Miscellaneous";
                return true;
            default:
                return false;
        }

    }

//    public void goMenu(View view){
//        startActivity(new Intent(getApplicationContext(), Menu.class));
//        finish();
//    }
}