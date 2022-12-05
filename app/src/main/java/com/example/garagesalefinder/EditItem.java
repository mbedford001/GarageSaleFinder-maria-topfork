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
import android.widget.TextView;
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;

public class EditItem extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{
    String category;
    EditText description;
    EditText quantity;
    EditText image;
    EditText priceRange;
    TextView itemTitle;
    Button SaveButton;
    Button ReturnBtn;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(EditItem.this);

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        description = findViewById(R.id.inputDescription);
        quantity = findViewById(R.id.inputQuantity);
        image = findViewById(R.id.inputImage);
        priceRange = findViewById(R.id.inputPrice);
        SaveButton = findViewById(R.id.SaveButton);
        ReturnBtn = findViewById(R.id.button);

        String username = getIntent().getStringExtra("username");
        Post post = (Post) getIntent().getSerializableExtra("post");
        String postname = post.getTitle();
        String itemName = getIntent().getStringExtra("itemTitle");
        System.out.println("Edit Item info: ");
        System.out.println("Item Name "+itemName);
        System.out.println("Post Name "+postname);

        itemTitle = findViewById(R.id.itemTitle);
        itemTitle.setText("Edit " +itemName);


        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String pDescription = description.getText().toString().trim();
                String pImage = image.getText().toString().trim();
                String pPrice = priceRange.getText().toString().trim();
                String pQuantity = quantity.getText().toString().trim();
                String pCategory = category;


                if (TextUtils.isEmpty(pDescription)&& TextUtils.isEmpty(pQuantity) && TextUtils.isEmpty(pImage) && TextUtils.isEmpty(pPrice)) {//verifies a username was entered
                    description.setError("Please fill in the information you want to change");
                    image.setError("Please fill in the information you want to change");
                    priceRange.setError("Please fill in the information you want to change");
                    quantity.setError("Please fill in the information you want to change");
                    return;
                }
                System.out.println("Editing Item info: ");
                System.out.println("username "+username);
                System.out.println("post name "+postname);
                System.out.println("item name "+itemName);
                System.out.println("category "+pCategory);
                System.out.println("quantity "+pQuantity);
                System.out.println("description "+pDescription);
                System.out.println("price "+pPrice);
                System.out.println("image "+pImage);
               if(dbhc.editItem(pDescription, pQuantity, pImage, pPrice, itemName, pCategory, username, postname)){
                    Toast.makeText(EditItem.this, "Item was edited", Toast.LENGTH_LONG).show();
                }
                //startActivity(new Intent(getApplicationContext(), ViewPost.class));
                //finish();
            }

        });

        /**
         * Button returns to View-Account page
         * @param view v
         */
        ReturnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Post p = (Post) getIntent().getSerializableExtra("post");
                ArrayList<Items> results = (ArrayList<Items>)dbhc.viewItems(p.getOwner(), p.getTitle());
                //ArrayList<Items> results = (ArrayList<Items>) getIntent().getSerializableExtra("itemResults");
                //ArrayList<Post> results = (ArrayList<Post>) dbhc.viewAllOwnPosts(username);
                int position = getIntent().getIntExtra("itemPosition", -1);//item position
                int postPosition = getIntent().getIntExtra("position",-1);
                ArrayList<Post> postResults = (ArrayList<Post>) dbhc.viewAllOwnPosts(username);
                Intent intent = new Intent(EditItem.this, ViewItem.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("source", "myPosts");
                intent.putExtra("itemResults", results);
                intent.putExtra("itemPosition", position);
                intent.putExtra("position",postPosition);
                intent.putExtra("results", postResults);

                startActivity(intent);
                finish();
            }
        });
    }

    public void showCategoryChoices(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_category);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
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
}
