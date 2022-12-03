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
import com.example.garagesalefinder.people.Account;

/**
 * This class takes the user inputs from the create post page and makes sure all criteria is inserted
 * and then directly sends user to add dates page
 */
public class Createpost extends AppCompatActivity {

    EditText city;
    EditText address;
    EditText zip;
    EditText country;
    EditText state;
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
        address = findViewById(R.id.inputLocation);
        city = findViewById(R.id.inputCity);
        zip = findViewById(R.id.inputZipcode);
        country = findViewById(R.id.inputCountry);
        state = findViewById(R.id.inputState);
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

                String pAddress = address.getText().toString().trim();
                String pCity = city.getText().toString().trim();
                String pZip = zip.getText().toString().trim();
                String pCountry = country.getText().toString().trim();
                String pState = state.getText().toString().trim();
                String pTitle = title.getText().toString().trim();
                String pDescription = description.getText().toString().trim();
                String pTime = time.getText().toString().trim();
                String pImage = image.getText().toString().trim();
                String pPrice = priceRange.getText().toString().trim();

                if(TextUtils.isEmpty(pAddress)){//verifies a location was entered
                    address.setError("Address is required.");
                    return;
                }

                if(TextUtils.isEmpty(pCity)){//verifies a location was entered
                    city.setError("City is required.");
                    return;
                }

                if(TextUtils.isEmpty(pState)){//verifies a location was entered
                    state.setError("State is required.");
                    return;
                }

                if(TextUtils.isEmpty(pZip)){//verifies a location was entered
                    zip.setError("Zip code is required.");
                    return;
                }

                if(TextUtils.isEmpty(pCountry)){//verifies a location was entered
                    country.setError("Country is required.");
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

                if (dbhc.postExists(pTitle)) {
                    title.setError("Post title is taken, please enter a different title");
                    return;
                } else {
                    String location = pAddress + ":" + pCity + ":" + pState + ":" + pZip + ":" + pCountry;

                    Post p = new Post(username, location, pTitle, pDescription, pTime, pPrice, pImage);


                    dbhc.addPost(p);
                    Toast.makeText(Createpost.this, "Post was added", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(), ViewPost.class));
                    //finish();
                    moveToAddDates(v);
                }
            }

        });

    }

    /**
     * This method sends the user back to the main menu page
     * Note: that intents, username and password, are passed back to the main page
     * so we don't lose who is logged in
     * @param view this is passing the create post information
     */
    public void returnHomeFromPost(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(Createpost.this,Menu.class);
        intent.putExtra("username",username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    /**
     * This method sends the user to the add dates page
     * Note: the intents, username and post title, are being passed to the add dates page
     * so we don't lose who is logged in
     * @param view this is passing the information from the above onCreate and onClick to the add dates page
     */
    public void moveToAddDates(View view){
        Intent intent = new Intent(getApplicationContext(), AddDates.class);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("title", title.getText().toString().trim());
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(), AddDates.class));
        //Toast.makeText(this, "Date has been added", Toast.LENGTH_SHORT).show();
        finish();
    }
}