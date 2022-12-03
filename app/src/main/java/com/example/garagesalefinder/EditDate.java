package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;

public class EditDate extends AppCompatActivity {

    Button ReturnBtn;
    EditText date;
    Button createButton;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(EditDate.this);

    @SuppressLint("MissingInflatedId")
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_date);
        ReturnBtn = findViewById(R.id.button);
        date = findViewById(R.id.inputDate);
        createButton = findViewById(R.id.btnCreate);

        String username = getIntent().getStringExtra("username");
        String title = getIntent().getStringExtra("title");

        /**
         * This button will add the date of the post to database
         */
        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String pDate = date.getText().toString().trim();
                //String pPostName = postName.getText().toString().trim();

                if(TextUtils.isEmpty(pDate)){//verifies a location was entered
                    date.setError("Date is required.");
                    return;
                }
                Boolean goodInput = false;
                if (pDate.length() == 10) {
                    System.out.println("length of date is 10");
                    if(pDate.charAt(0)=='2' && pDate.charAt(1) == '0'){
                        System.out.println("first 2 digits of the year is 20");
                        if(Character.isDigit(pDate.charAt(2)) && Character.isDigit(pDate.charAt(3))){
                            System.out.println("digits 3&4 of the year are any digit");
                            if(pDate.charAt(4)=='-' && pDate.charAt(7)=='-'){
                                System.out.println("two dashes are present");
                                if((pDate.charAt(5)=='0' || pDate.charAt(5)=='1') && (Character.isDigit(pDate.charAt(6)))){
                                    System.out.println("month is inputted correctly");
                                    if((pDate.charAt(8)=='0' || pDate.charAt(8)=='1' || pDate.charAt(8)=='2' || pDate.charAt(8)=='3') && (Character.isDigit(pDate.charAt(9)))){
                                        System.out.println("day is inputted correctly");
                                        goodInput = true;
                                        System.out.println("goodInput: "+ goodInput);
                                    }
                                }
                            }
                        }
                    }
                }
                if (goodInput == false){//verifies a correctly formatted input was given
                    date.setError("Date is formatted incorrectly use: yyyy-mm-dd");
                    return;
                }
                /*if(TextUtils.isEmpty(pPostName)){
                    postName.setError("Post Name is required.");
                    return;
                }
                */

                dbhc.addDate(pDate, title, username);
                Toast.makeText(EditDate.this, "Date was added", Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(EditDate.this, ViewPost.class);
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