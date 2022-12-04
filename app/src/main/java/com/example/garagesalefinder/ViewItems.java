package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ViewItems extends AppCompatActivity implements Serializable {
    ListView list;
    ListViewAdapterItems adapter;//not sure what's up with this error
    ArrayList<Items> itemResults = new ArrayList<Items>();
    Button returnBtn;
    ArrayList<Post> results3 = new ArrayList<Post>(0);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewItems.this);
        returnBtn = findViewById(R.id.btnReturnMyPost);

        String username = getIntent().getStringExtra("username");
        int postPosition = getIntent().getIntExtra("position", -1);
        results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
        System.out.println(username);
        for (Post p: results3){
            System.out.println(p.getTitle());
        }
        Post p = results3.get(postPosition);

        System.out.println("---------------------------------------------------------" + postPosition + " " + p.getTitle());
        //should be passing the username of the post owner, not the username of who is logged in
        itemResults = (ArrayList<Items>)dbhc.viewItems(p.getOwner(), p.getTitle());
        System.out.println("Here are the item results: " +itemResults);
        for (Items i: itemResults){
            System.out.println("Each item name: "+i.getItem_title());
        }
        if (itemResults.isEmpty()){
            Toast.makeText(ViewItems.this, "No items for this post", Toast.LENGTH_LONG).show();
        }
        //setContentView(R.layout.activity_view_items);
        list = (ListView) findViewById(R.id.listview);//locates ListView in xml file
        adapter = new ListViewAdapterItems(this, itemResults);//not sure what's up with this error
        list.setAdapter(adapter);

        /**
         * When a list item is clicked (one of the user's posts), sends user to
         * view item page
         * Sends intent containing user's username, password, and all user's posts
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Items anItem = itemResults.get(i);
                //String itemTitle = anItem.getItem_title();

                String title = getIntent().getStringExtra("title");
                String password = getIntent().getStringExtra("password");
                System.out.println("PASSWORD IS: "+password);
                String username = getIntent().getStringExtra("username");
                ArrayList<Post> results4 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewItems.this, ViewItem.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                //intent.putExtra("theItem", anItem);
                intent.putExtra("results", results4);
                intent.putExtra("itemResults", itemResults);
                intent.putExtra("itemPosition", i);
                intent.putExtra("position", postPosition);
                intent.putExtra("source", "myItems");
                startActivity(intent);
                finish();
            }
        });

        /**
         * Method that returns to the menu page when button is selected
         * Sends intent containing user's username and password
         */
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                String source = getIntent().getStringExtra("source");
                System.out.println("PASSWORD IS: "+password);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewItems.this,ViewPost.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", source);
                startActivity(intent);
                finish();
            }
        });




    }
}