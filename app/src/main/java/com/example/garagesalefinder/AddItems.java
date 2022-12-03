package com.example.garagesalefinder;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

public class AddItems extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    String postName;
    EditText itemTitle;
    //EditText category;
    String category;
    EditText image;
    EditText description;
    EditText price;
    EditText quantity;
    Button createButton;
    Button returnBtn;
    Button doneBtn;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(AddItems.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelperClass dbhc = new DataBaseHelperClass(AddItems.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additems);
        itemTitle = findViewById(R.id.inputItemName);
        postName = getIntent().getStringExtra("title");
        //postName = findViewById(R.id.inputPostName);
        description = findViewById(R.id.inputDescription);
        //category = findViewById(R.id.inputCategory);
        price = findViewById(R.id.inputPrice);
        image = findViewById(R.id.inputImage);
        quantity = findViewById(R.id.inputQuantity);
        createButton = findViewById(R.id.btnCreate);
        String username = getIntent().getStringExtra("username");
        returnBtn = findViewById(R.id.btnReturn);
        //doneBtn = findViewById(R.id.btnDone);



        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String pItemTitle = itemTitle.getText().toString().trim();
                //String pPostName = postName.getText().toString().trim();
                String pDescription = description.getText().toString().trim();
                //String pCategory = category.getText().toString().trim();
                String pCategory = category;
                String pImage = image.getText().toString().trim();
                String pPrice = price.getText().toString().trim();
                String pQuantity = quantity.getText().toString().trim();

                if(TextUtils.isEmpty(pItemTitle)){//verifies a location was entered
                    itemTitle.setError("Item Title is required.");
                    return;
                }

                if(dbhc.itemExists(postName ,pItemTitle)){
                    itemTitle.setError("You already have an item with this title.");
                }

                /*if(TextUtils.isEmpty(pPostName)) {
                    postName.setError("Post Name is required.");
                    return;
                }*/
                Items i = new Items(postName, pItemTitle, username, pCategory, pImage,
                        pDescription, pPrice, pQuantity);
                dbhc.addItem(i);
                Toast.makeText(AddItems.this, "Item was added", Toast.LENGTH_LONG).show();
            }
        });

/**
        doneBtn.setOnClickListener(new View.OnClickListener(){
=======

        returnBtn.setOnClickListener(new View.OnClickListener(){
>>>>>>> c233099cd5e7811553153c493db1b35918710b24
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(AddItems.this,Menu.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });
 */

        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(AddItems.this,AddDates.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
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
                Toast.makeText(this, "Toys was selected", Toast.LENGTH_LONG).show();
                category = "Toys";
                return true;
            case R.id.category2:
                Toast.makeText(this, "Clothing was selected", Toast.LENGTH_LONG).show();
                category = "Clothing";
                return true;
            case R.id.category3:
                Toast.makeText(this, "Furniture was selected", Toast.LENGTH_LONG).show();
                category = "Furniture";
                return true;
            case R.id.category4:
                Toast.makeText(this, "Jewelry was selected", Toast.LENGTH_LONG).show();
                category = "Jewelry";
                return true;
            case R.id.category5:
                Toast.makeText(this, "Antique was selected", Toast.LENGTH_LONG).show();
                category = "Antique";
                return true;
            case R.id.category6:
                Toast.makeText(this, "Arts and Crafts was selected", Toast.LENGTH_LONG).show();
                category = "Arts and Crafts";
                return true;
            case R.id.category7:
                Toast.makeText(this, "Sport was selected", Toast.LENGTH_LONG).show();
                category = "Sport";
                return true;
            case R.id.category8:
                Toast.makeText(this, "Books was selected", Toast.LENGTH_LONG).show();
                category = "Books";
                return true;
            case R.id.category9:
                Toast.makeText(this, "Electronics was selected", Toast.LENGTH_LONG).show();
                category = "Electronics";
                return true;
            case R.id.category10:
                Toast.makeText(this, "Miscellaneous was selected", Toast.LENGTH_LONG).show();
                category = "Miscellaneous";
                return true;
            default:
                return false;
        }
    }

    public void backToMenu3(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(AddItems.this,Menu.class);
        intent.putExtra("username",username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }


}


