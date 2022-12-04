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
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;

public class EditPost extends AppCompatActivity {
    EditText city;
    EditText address;
    EditText zip;
    EditText country;
    EditText state;
    EditText description;
    EditText time;
    EditText image;
    EditText priceRange;
    Button SaveButton;
    Button ReturnBtn;
    Button EditDateBtn;
    Button AddItemBtn;
    public TextView UserText;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(EditPost.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_post);
        address = findViewById(R.id.inputLocation);
        city = findViewById(R.id.inputCity);
        zip = findViewById(R.id.inputZipcode);
        country = findViewById(R.id.inputCountry);
        state = findViewById(R.id.inputState);
        description = findViewById(R.id.inputDescription);
        time = findViewById(R.id.inputTime);
        image = findViewById(R.id.inputImage);
        priceRange = findViewById(R.id.inputPrice);
        SaveButton = findViewById(R.id.btnSave);
        ReturnBtn = findViewById(R.id.button);
        EditDateBtn = findViewById(R.id.btnEditDate);
        AddItemBtn = findViewById(R.id.btnAddItem);

        String username = getIntent().getStringExtra("username");
        String postname = getIntent().getStringExtra("title");

        /**
         * Button returns to View-Account page
         * @param view v
         */
        EditDateBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                //ArrayList<Post> results = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                ArrayList<Post> results = (ArrayList<Post>)dbhc.viewAllOwnPosts(username);
                int position = getIntent().getIntExtra("position", -1);
                Intent intent = new Intent(EditPost.this, EditDate.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                intent.putExtra("title", postname);
                intent.putExtra("source", "myPosts");
                intent.putExtra("results", results);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });
        AddItemBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                //ArrayList<Post> results = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                ArrayList<Post> results = (ArrayList<Post>)dbhc.viewAllOwnPosts(username);
                int position = getIntent().getIntExtra("position", -1);
                Intent intent = new Intent(EditPost.this, AddItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                intent.putExtra("title", postname);
                intent.putExtra("source", "myPosts");
                intent.putExtra("results", results);
                intent.putExtra("position", position);
                String direction = "came";
                intent.putExtra("direction", direction);
                startActivity(intent);
                finish();
            }
        });

        SaveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String pAddress = address.getText().toString().trim();
                String pCity = city.getText().toString().trim();
                String pZip = zip.getText().toString().trim();
                String pCountry = country.getText().toString().trim();
                String pState = state.getText().toString().trim();
                String pDescription = description.getText().toString().trim();
                String pTime = time.getText().toString().trim();
                String pImage = image.getText().toString().trim();
                String pPrice = priceRange.getText().toString().trim();

                String location = pAddress+":"+pCity+":"+pState+":"+pZip+":"+pCountry;


                if(TextUtils.isEmpty(pAddress) && TextUtils.isEmpty(pCity) && TextUtils.isEmpty(pZip) && TextUtils.isEmpty(pCountry)&& TextUtils.isEmpty(pState) && TextUtils.isEmpty(pDescription) && TextUtils.isEmpty(pTime) && TextUtils.isEmpty(pImage) && TextUtils.isEmpty(pPrice)){//verifies a username was entered
                    city.setError("Please fill in the information you want to change");
                    address.setError("Please fill in the information you want to change");
                    zip.setError("Please fill in the information you want to change");
                    country.setError("Please fill in the information you want to change");
                    state.setError("Please fill in the information you want to change");
                    description.setError("Please fill in the information you want to change");
                    time.setError("Please fill in the information you want to change");
                    image.setError("Please fill in the information you want to change");
                    priceRange.setError("Please fill in the information you want to change");
                    return;
                }

                if(dbhc.editPost(location, pDescription, pTime, pImage, pPrice, postname, username)){
                    Toast.makeText(EditPost.this, "Post was edited", Toast.LENGTH_SHORT).show();
                }
                //startActivity(new Intent(getApplicationContext(), ViewPost.class));
                //finish();
            }

        });

        /**
         * Button returns to View-Account page
         * @param view v
         */
        ReturnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                //ArrayList<Post> results = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                ArrayList<Post> results = (ArrayList<Post>)dbhc.viewAllOwnPosts(username);
                int position = getIntent().getIntExtra("position", -1);
                Intent intent = new Intent(EditPost.this, ViewPost.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                //intent.putExtra("title", postname);
                intent.putExtra("source", "myPosts");
                intent.putExtra("results", results);
                intent.putExtra("position", position);


                startActivity(intent);
                finish();
            }
        });

    }
}