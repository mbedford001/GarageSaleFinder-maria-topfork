package com.example.garagesalefinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.example.garagesalefinder.PostStuff.Post;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewSearchResults extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;//not sure what's up with this error
    ArrayList<Post> results = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_search_results);

        results = (ArrayList<Post>) getIntent().getSerializableExtra("results");//this line won't work because the ArrayList passed through intent isn't type String
        list = (ListView) findViewById(R.id.listview);//locates ListView in xml file
        adapter = new ListViewAdapter(this, results);//not sure what's up with this error
        list.setAdapter(adapter);


    }
}