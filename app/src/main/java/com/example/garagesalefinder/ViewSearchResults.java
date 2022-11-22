package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.garagesalefinder.PostStuff.Post;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewSearchResults extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;//not sure what's up with this error
    ArrayList<Post> results = new ArrayList<Post>();
    Button returnBtn;
    Button searchBtn;
    Button viewPostBtn;
    String username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);
        returnBtn = findViewById(R.id.btnReturn);
        searchBtn = findViewById(R.id.btnSearchAgain);


        results = (ArrayList<Post>) getIntent().getSerializableExtra("results");//this line won't work because the ArrayList passed through intent isn't type String
        list = (ListView) findViewById(R.id.listview);//locates ListView in xml file
        adapter = new ListViewAdapter(this, results);//not sure what's up with this error
        list.setAdapter(adapter);

         /*
        When search button from SearchByLocation is selected, pertinent information is passed from
        that page to this one, and from here the results are processed and displayed to the user
        */
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(ViewSearchResults.this, "List item was clicked at " + i, Toast.LENGTH_SHORT).show();
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewSearchResults.this, ViewPost.class);
                intent.putExtra("results", results);
                intent.putExtra("position", i);
                intent.putExtra("username",username);
                intent.putExtra("source", "search");
                System.out.println("*&*&*&*&*&*&*&*&" + username + "*&*&*&*&*&*&*&*&");
                startActivity(intent);
                finish();
            }
        });

          /*
        When Menu button is clicked, user is sent to menu. Username and Password is passed back.
        */
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewSearchResults.this,Menu.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

           /*
        When Search Again button is clicked, user is sent to previous page. Username and Password is passed back.
        */
        searchBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                System.out.println("Username in results is "+username);
                Intent intent = new Intent(ViewSearchResults.this,SearchByLocation.class);
                intent.putExtra("username",username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });


    }



}
