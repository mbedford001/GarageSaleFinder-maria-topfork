package com.example.garagesalefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

public class AddItems extends AppCompatActivity {

    EditText postName;
    EditText itemTitle;
    //not sure if I'll need username, they shouldn't have to type their username in
    // EditText username;
    EditText category;
    EditText image;
    EditText description;
    EditText price;
    EditText quantity;
    Button createButton;


    DataBaseHelperClass dbhc = new DataBaseHelperClass(AddItems.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);
        itemTitle = findViewById(R.id.inputItemName);
        postName = findViewById(R.id.inputPostName);
        description = findViewById(R.id.inputDescription);
        category = findViewById(R.id.inputCategory);
        price = findViewById(R.id.inputPrice);
        image = findViewById(R.id.inputImage);
        quantity = findViewById(R.id.inputQuantity);
        createButton = findViewById(R.id.btnCreate);
        String username = getIntent().getStringExtra("username");

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String pItemTitle = itemTitle.getText().toString().trim();
                String pPostName = postName.getText().toString().trim();
                String pDescription = description.getText().toString().trim();
                String pCategory = category.getText().toString().trim();
                String pImage = image.getText().toString().trim();
                String pPrice = price.getText().toString().trim();
                String pQuantity = quantity.getText().toString().trim();

                if(TextUtils.isEmpty(pItemTitle)){//verifies a location was entered
                    itemTitle.setError("Item Title is required.");
                    return;
                }

                if(TextUtils.isEmpty(pPostName)){
                    postName.setError("Post Name is required.");
                    return;
                }
                //hardcoded username!!!! FIX
                Items i = new Items(pPostName, pItemTitle, username, pCategory, pImage,
                        pDescription, pPrice, pQuantity);
                dbhc.addItem(i);
            }

            public void addItems(View view){
                /*String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(Createpost.this, AddItems.class);
                intent.putExtra("username",username);
                startActivity(intent);
                finish();*/
            }
        });
    }
}


