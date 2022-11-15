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

public class AddDates extends AppCompatActivity {

    EditText postName;
    EditText date;
    Button createButton;
    String title;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(AddDates.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddates);
        //postName = findViewById(R.id.inputPostName);
        date = findViewById(R.id.inputDate);
        createButton = findViewById(R.id.btnCreate);
        String username = getIntent().getStringExtra("username");
        title = getIntent().getStringExtra("title");

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String pDate = date.getText().toString().trim();
                //String pPostName = postName.getText().toString().trim();

                if(TextUtils.isEmpty(pDate)){//verifies a location was entered
                    date.setError("Date is required.");
                    return;
                }

                /*if(TextUtils.isEmpty(pPostName)){
                    postName.setError("Post Name is required.");
                    return;
                }
                */

                dbhc.addDate(pDate, title, username);
                Toast.makeText(AddDates.this, "Date was added", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void moveToAddItems(View view){
        Intent intent = new Intent(getApplicationContext(), AddItems.class);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        intent.putExtra("username",username);
        intent.putExtra("password",password);
        intent.putExtra("title",title);
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(), AddDates.class));
        //Toast.makeText(this, "Item has been added", Toast.LENGTH_SHORT).show();
        finish();
    }
    //leave all this below commented out for now
    /**
    public void showOwnPosts(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_ownposts);
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
    */

    public void backToMenu2(View view){
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        Intent intent = new Intent(AddDates.this,Menu.class);
        intent.putExtra("username",username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }
}



