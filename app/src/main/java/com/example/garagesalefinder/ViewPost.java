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
    Button returnBtn;
    String title;
    Button deleteBtn;
    Button back1Btn;
    Button back2Btn;
    Button back3Btn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String from = getIntent().getStringExtra("source");
        int position = getIntent().getIntExtra("position", -1);
        ArrayList<Post> results = (ArrayList<Post>) getIntent().getSerializableExtra("results");

        //Integer i = Integer.valueOf(position);
        System.out.println("username intent "+ username);
        results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
        String source = getIntent().getStringExtra("source");
                //dbhc.getPostData(username);
        returnBtn = findViewById(R.id.btnBack);
        deleteBtn = findViewById(R.id.deleteBtn);
        back1Btn = findViewById(R.id.back1Btn);
        back2Btn = findViewById(R.id.back2Btn);
        back3Btn = findViewById(R.id.back3Btn);

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

//        String joined = TextUtils.join(", ", results3);
//        System.out.println("---------------------PRINTING HERE-----------------");
//        System.out.println(joined);
//        TextView []tv = new TextView[results3.size()];
//        tv[0]=(TextView)findViewById(R.id.title);
  /*    tv[2]=(TextView)findViewById(R.id.location);
        tv[3]=(TextView)findViewById(R.id.description);
        tv[4]=(TextView)findViewById(R.id.time);
        tv[5]=(TextView)findViewById(R.id.priceRange);
        tv[6]=(TextView)findViewById(R.id.image);
     */
        System.out.println("---------------------PRINTING HERE-----------------");
        System.out.println(findViewById(R.id.title));

        post = results3.get(position);

        UserText = findViewById(R.id.username);
        UserText.setText(post.getOwner());

        title = post.getTitle();
        //String title = getIntent().getStringExtra("title");
        TitleText = findViewById(R.id.title);
        TitleText.setText(title);

        //need to change this so that it breaks up the full address into seperate fields
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


        back1Btn.setVisibility(View.GONE);
        back2Btn.setVisibility(View.GONE);
        back3Btn.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);
        //if search result and not own post
        System.out.println(username);
        if(source.equals("search") && !username.equals(post.getOwner())){
            System.out.println(username + " is not the same as "+ post.getOwner());
            back2Btn.setVisibility(View.VISIBLE);
        }
        //else if search result and owner
        else if(source.equals("search") && username.equals(post.getOwner())){
            back3Btn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }
        //else would have come from view own post
        else{
            System.out.println("this ok");
            back1Btn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        }





        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this,Menu.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

        back1Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this,ViewMyPosts.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("results", results3);
                intent.putExtra("source", "myPosts");
                startActivity(intent);
                finish();
            }
        });

        back2Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this,ViewSearchResults.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("results", results3);
                intent.putExtra("source", "search");
                startActivity(intent);
                finish();
            }
        });

        back3Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this,ViewSearchResults.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("results", results3);
                intent.putExtra("source", "search");
                startActivity(intent);
                finish();
            }
        });


          }
        public void deleteBtn(View view){
            DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
            String username = getIntent().getStringExtra("username");
            String password = getIntent().getStringExtra("password");
            dbhc.deletePost(username, title);
            Intent intent = new Intent(ViewPost.this,ViewMyPosts.class);
            intent.putExtra("username",username);
            intent.putExtra("username",password);
            startActivity(intent);
            finish();
        }
    }




