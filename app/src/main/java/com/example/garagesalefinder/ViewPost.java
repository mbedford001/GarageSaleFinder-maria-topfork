package com.example.garagesalefinder;

import java.util.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;



public class ViewPost extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;
    Post post;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
    ArrayList<Post> results3 = new ArrayList<Post>(0);
    //ArrayList list;
    public TextView UserText, TitleText, LocationText, DescriptionText, TimeText, PriceRangeText, ImageText;
    Button loadDatabtn;
    String testTitle = "A Big Sale";
    String test = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        String username = getIntent().getStringExtra("username");
        System.out.println("username intent "+ username);
        results3 = dbhc.getPostData(username);
       // String title = results3.get(1);
        //  results2 = (ArrayList<Post>) getIntent().getSerializableExtra("results1")
/*
        String username = getIntent().getStringExtra("username");
        list = (ArrayList<Post>)dbhc.getPostData(username);;
        System.out.println("---------------------PRINTING HERE-----------------");
        System.out.println("Post data: " + list);
        Object title1 = list.get(2);
        System.out.println("title should be : " + title1);
*/
        if(results3 == null) {
            System.out.println("---------------------PRINTING HERE-----------------");
            System.out.println("---------------------NOT WORKING-----------------");
        }
        else {
            System.out.println("---------------------PRINTING HERE-----------------");
            System.out.println(results3.toString());
        }

        String joined = TextUtils.join(", ", results3);
        System.out.println("---------------------PRINTING HERE-----------------");
        System.out.println(joined);
        TextView []tv = new TextView[results3.size()];
        tv[0]=(TextView)findViewById(R.id.title);
  /*    tv[2]=(TextView)findViewById(R.id.location);
        tv[3]=(TextView)findViewById(R.id.description);
        tv[4]=(TextView)findViewById(R.id.time);
        tv[5]=(TextView)findViewById(R.id.priceRange);
        tv[6]=(TextView)findViewById(R.id.image);
     */
        System.out.println("---------------------PRINTING HERE-----------------");
        System.out.println(findViewById(R.id.title));

        UserText = findViewById(R.id.username);
        UserText.setText(username);

        post = results3.get(0);

        String title = post.getTitle();
        //String title = getIntent().getStringExtra("title");
        TitleText = findViewById(R.id.title);
        TitleText.setText(title);

        String location = post.getLocation();
        //String location = getIntent().getStringExtra("location");
        LocationText = findViewById(R.id.location);
        LocationText.setText(location);

        String description = post.getDescription();
        //String description = getIntent().getStringExtra("description");
        DescriptionText = findViewById(R.id.description);
        DescriptionText.setText(description);

        String time = post.getTime();
        //String time = getIntent().getStringExtra("time");
        TimeText = findViewById(R.id.time);
        TimeText.setText(time);

        String priceRange = post.getPriceRange();
        //String priceRange = getIntent().getStringExtra("priceRange");
        PriceRangeText = findViewById(R.id.priceRange);
        PriceRangeText.setText(priceRange);

        //String image = getIntent().getStringExtra("image");
        //ImageText = findViewById(R.id.image);
        //ImageText.setText(image);


          }

        }


