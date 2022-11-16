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
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        System.out.println("USSERNAME"+username);


        /*
        When the search button is clicked, the inputs are checked to make sure at least one field was filled out.
        If a field was entered, the code then uses a try catch to check if there are any search results matching the criteria
        If the user did not enter any criteria, or theres are no matching results, the user will get an error.
        If there are matching results, the user will be taken to the ViewSearchResults page.
        Username and password are passed to the ViewSearchResults page in order to keep track of the signed in user
         */
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
                    String username = getIntent().getStringExtra("username");
                    String password = getIntent().getStringExtra("password");
                    Intent intent = new Intent(SearchByLocation.this, ViewSearchResults.class);
                    intent.putExtra("results", results);
                    intent.putExtra("password", password);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    finish();
                }
            }

        });

        /*
        When the return button is clicked, the user is taken back to the menu.
        Username and password are passed to the menu page in order to keep track of the signed in user
         */
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(SearchByLocation.this,Menu.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });
    }

    /*
    When the drop down menu is clicked, the user can choose which option to select
     */
    public void showCategoryChoices(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_category);
        popup.show();
    }


    /*
    This gives the user a pop up message confirming their choice on the drop down menu
     */
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