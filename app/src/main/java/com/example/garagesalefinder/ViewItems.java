package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.util.ArrayList;
import java.util.List;

public class ViewItems extends AppCompatActivity {
    ListView list;
    ListViewAdapterItems adapter;//not sure what's up with this error
    List<Items> itemResults = new ArrayList<Items>();
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

        itemResults = (ArrayList<Items>)dbhc.viewItems(username, p.getTitle());
        for (Items i: itemResults){
            System.out.println(i.getItem_title());
        }
        list = (ListView) findViewById(R.id.listview);//locates ListView in xml file
        adapter = new ListViewAdapterItems(this, itemResults);//not sure what's up with this error
        list.setAdapter(adapter);

        /**
         * When a list item is clicked (one of the user's posts), sends user to
         * view post page
         * Sends intent containing user's username, password, and all user's posts
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String title = getIntent().getStringExtra("title");
                String password = getIntent().getStringExtra("password");
                String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(ViewItems.this, ViewItems.class); //CHANGE
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                //intent.putExtra("title", title);
                intent.putExtra("itemPosition", i);
                //intent.putExtra("source", "myPosts");
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