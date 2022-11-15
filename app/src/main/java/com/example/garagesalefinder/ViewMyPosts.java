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

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Class to view a user's created posts
 */
public class ViewMyPosts extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;//not sure what's up with this error
    ArrayList<Post> results = new ArrayList<Post>();
    Button returnBtn;
    DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewMyPosts.this);



    @SuppressLint("MissingInflatedId")
    @Override
    /**
     * onCreate runs when ViewMyPosts page is generated
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_posts);
        returnBtn = findViewById(R.id.btnReturnMenu);


        String username = getIntent().getStringExtra("username");
        results = (ArrayList<Post>)dbhc.viewAllOwnPosts(username);
        list = (ListView) findViewById(R.id.listview);//locates ListView in xml file
        adapter = new ListViewAdapter(this, results);//not sure what's up with this error
        list.setAdapter(adapter);

        /**
         * When a list item is clicked (one of the user's posts), sends user to
         * view post page
         * Sends intent containing user's username, password, and all user's posts
         */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ViewMyPosts.this, "List item was clicked at " + i, Toast.LENGTH_SHORT).show();
                String title = getIntent().getStringExtra("title");
                String password = getIntent().getStringExtra("password");
                String username = getIntent().getStringExtra("username");
                Intent intent = new Intent(ViewMyPosts.this, ViewPost.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                //intent.putExtra("title", title);
                intent.putExtra("results", results);
                intent.putExtra("position", i);
                intent.putExtra("source", "myPosts");
                startActivity(intent);
                //startActivity(new Intent(getApplicationContext(), ViewPost.class));
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
                Intent intent = new Intent(ViewMyPosts.this,Menu.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

    }



}