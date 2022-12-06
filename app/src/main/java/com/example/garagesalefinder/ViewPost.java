package com.example.garagesalefinder;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

//package com.example.sample;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.InputStream;
import java.util.concurrent.Executors;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView;
import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * Class that views post data from data base. Displays all items related to
 * specific item post
 *
 */
public class ViewPost extends AppCompatActivity {

    ListView list;
    ListViewAdapter adapter;
    Post post;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
    ArrayList<Post> results3 = new ArrayList<Post>(0);
    //ArrayList list;
    public TextView UserText, TitleText, LocationText, DescriptionText, TimeText, PriceRangeText, ImageText,DateText;
    Button loadDatabtn;
    String testTitle = "A Big Sale";
    String test = "";
    Button returnBtn;
    String title;
    Button deleteBtn;
    Button back1Btn;
    Button back2Btn;
    Button back3Btn;
    Button viewItems;
    Button viewItemsFromSearch;
    Button viewItemsFromAll;
    Button back2All;
    String image;
    Button removeFromSaved;
    Button addToSaved;
    Button back2Saved;
    Button viewItemsFromSaved;
    Button editPostBtn;

    /**
     * On create method sets all variables from a given post to be able to be displayed
     * on the app, also shows data once data is filled in
     *
     * @param savedInstanceState
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        System.out.println("ONCREATE VIEW POST PASSWORD IS: "+password);
        String source = getIntent().getStringExtra("source");
        int position = getIntent().getIntExtra("position", -1);
        ArrayList<Post> results = (ArrayList<Post>) getIntent().getSerializableExtra("results");


        //Integer i = Integer.valueOf(position);
        System.out.println("username intent "+ username);
        results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");

        //dbhc.getPostData(username);
        returnBtn = findViewById(R.id.btnBack);
        deleteBtn = findViewById(R.id.deleteBtn);
        back1Btn = findViewById(R.id.back1Btn);
        back2Btn = findViewById(R.id.back2Btn);
        back3Btn = findViewById(R.id.back3Btn);
        viewItemsFromSearch = findViewById(R.id.toItemsFromSearch);
        viewItems = findViewById(R.id.toItemsFromMyPosts);
        viewItemsFromAll = findViewById(R.id.toItemsFromAllPosts);
        back2All = findViewById(R.id.back2All);
        addToSaved = findViewById(R.id.addToSaved);
        removeFromSaved = findViewById(R.id.removeFromSaved);

        back2Saved = findViewById(R.id.back2Saved);
        viewItemsFromSaved = findViewById(R.id.toItemsFromSaved);
        editPostBtn = findViewById(R.id.editPostBtn);


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
        if (results3 == null) {
            System.out.println("---------------------PRINTING HERE-----------------");
            System.out.println("---------------------NOT WORKING-----------------");
        } else {
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
        System.out.println("---------------------PRINTING TITLE HERE-----------------");
        System.out.println(findViewById(R.id.title));

        if (source.equals("notification")){
            Bundle extras = getIntent().getExtras();
            username = extras.getString("username");
            password = extras.getString("password");
            post = (Post)getIntent().getSerializableExtra("clickedPost");
            System.out.println("POOOOOOOOOOOOOOOOOOST" + post.getTitle());
        }
        else{
            post = results3.get(position);
        }

        //UserText = findViewById(R.id.username);
        //UserText.setText(post.getOwner());
        String time2 = post.getTime();
        title = post.getTitle();
        String description2 = post.getDescription();
        System.out.println("TITLE IS: "+title);
        //String title = getIntent().getStringExtra("title");
        System.out.println("TIME IS: "+time2);
        System.out.println("DESCRIPTION IS: "+description2);
        TitleText = findViewById(R.id.title);
        TitleText.setText(title);

        //format for google maps is "####(building number) ________(Street name), __(state id ex:MN)  #####(5 num zip)"
        //need to change this so that it breaks up the full address into seperate fields
        String[] location = (dbhc.splitLocation(post.getLocation()));
        System.out.println("Location IS: "+post.getLocation());
        String address = location[0];
        String state = location[2];
        String zip = location[3];
        String format = address + ", " + state + " " + zip;
        //String location = getIntent().getStringExtra("location");
        LocationText = findViewById(R.id.location);
        LocationText.setText(format);

        DateText = findViewById(R.id.dates);
        String date_format = dbhc.getDates(post.getTitle());
        System.out.println("THe Dates of the Sale: "+date_format);
        if (date_format != null && date_format != ", " && date_format != "") {
            char c = ' ';
            while(c != ','){
                c = date_format.charAt(date_format.length()-1);
                date_format = date_format.substring(0, date_format.length()-1);
            }
               DateText.setText(date_format);

        }
        else{
            DateText.setText("Not yet scheduled");
        }

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

        String image4 = post.getImage();
        System.out.println("---------------------------PRINTING HERE------------------------------------");
        System.out.println(image4);

        String image6 = post.getImage();
        //Uri image = (Uri) item.getImage();
        Drawable image1 = LoadImageFromWebOperations(image6);
        new DownloadImageFromInternet((ImageView) findViewById(R.id.imageView)).execute(image6);


        //String image = getIntent().getStringExtra("image");
        ImageText = findViewById(R.id.image);
        //ImageText.setText(image);



        back1Btn.setVisibility(View.GONE);
        back2Btn.setVisibility(View.GONE);
        back3Btn.setVisibility(View.GONE);
        deleteBtn.setVisibility(View.GONE);
        viewItems.setVisibility(View.GONE);
        viewItemsFromSearch.setVisibility(View.GONE);
        viewItemsFromAll.setVisibility(View.GONE);
        back2All.setVisibility(View.GONE);
        addToSaved.setVisibility(View.GONE);
        removeFromSaved.setVisibility(View.GONE);
        back2Saved.setVisibility(View.GONE);
        viewItemsFromSaved.setVisibility(View.GONE);
        editPostBtn.setVisibility(View.GONE);

        //if search result and not own post
        if (source.equals("allPosts")) {
            back2All.setVisibility(View.VISIBLE);
            viewItemsFromAll.setVisibility(View.VISIBLE);
        } else if (source.equals("saved")) {
            back2Saved.setVisibility(View.VISIBLE);
            viewItemsFromSaved.setVisibility(View.VISIBLE);
        } else if (source.equals("search") && !username.equals(post.getOwner())) {
            System.out.println(username + " is not the same as " + post.getOwner());
            back2Btn.setVisibility(View.VISIBLE);
            viewItemsFromSearch.setVisibility(View.VISIBLE);
        }
        //else if search result and owner
        else if (source.equals("search") && username.equals(post.getOwner())) {
            System.out.println(username + " is the same as " + post.getOwner());
            back3Btn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            viewItemsFromSearch.setVisibility(View.VISIBLE);
            editPostBtn.setVisibility(View.VISIBLE);
        }
        //else would have come from view own post
        else {
            System.out.println("this ok");
            back1Btn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
            viewItems.setVisibility(View.VISIBLE);
            editPostBtn.setVisibility(View.VISIBLE);
        }

        System.out.println("USERRRRRRRRRRRRRRRNAME" + username);
        if (username.equals(post.getOwner())){
            //ignore
        }
        else if (dbhc.returnListSavedPosts(username, post.getTitle())){
            removeFromSaved.setVisibility(View.VISIBLE);
            addToSaved.setVisibility(View.GONE);
        }
        else {
            removeFromSaved.setVisibility(View.GONE);
            addToSaved.setVisibility(View.VISIBLE);
        }



/**
 * Button that sends post back to menu
 *
 */
        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this, Menu.class);
                System.out.println("Return button PASSWORD IS: "+password);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                startActivity(intent);
                finish();
            }
        });

        /**
         * Button that sends user back to view my posts
         */

        back1Btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this, ViewMyPosts.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
                intent.putExtra("results", results3);
                intent.putExtra("source", "myPosts");
                startActivity(intent);
                finish();
            }
        });

        /**
         * Button that sends user back to search results
         *
         */
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

        /**
         * button that sends user back to search results
         *
         */
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

/**
 * Button that sends user to view items
 *
 */
        viewItemsFromSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "search");


                startActivity(intent);
                finish();
            }
        });

        /**
         * button that sends user to view items
         *
         */
        viewItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "myPosts");
                startActivity(intent);
                finish();
            }
        });
/**
 * button that sends user to view items
 *
 */
        viewItemsFromAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "allPosts");
                startActivity(intent);
                finish();
            }
        });


        /**
         * Button that gets post and sends to view items
         *
         */
        viewItemsFromSaved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "saved");
                startActivity(intent);
                finish();
            }
        });
/**
 * Button that sends user back to view all posts with all intents
 *
 */
        back2All.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewAllPosts.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "allPosts");
                startActivity(intent);
                finish();
            }
        });

        /**
         * Sends user back to saved posts
         *
         */

        back2Saved.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int position = getIntent().getIntExtra("position", -1);
                results3 = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewPost.this,ViewSavedPosts.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("position", position);
                intent.putExtra("results", results3);
                intent.putExtra("source", "saved");
                startActivity(intent);
                finish();
            }
        });

        /**
         * edit post button
         */
        editPostBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewPost.this,EditPost.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("title", title);
                intent.putExtra("source", "myPosts");
                intent.putExtra("results", results3);
                intent.putExtra("position", position);
                startActivity(intent);
                finish();
            }
        });

          }

          private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
            ImageView imageView;
            public DownloadImageFromInternet(ImageView imageView){
                this.imageView = imageView;
                Toast.makeText(getApplicationContext(), "Its working", Toast.LENGTH_SHORT);
            }
            protected Bitmap doInBackground(String... urls) {
                String imageURL = urls[0];
                Bitmap bimage = null;
                try{
                    InputStream in = new java.net.URL(imageURL).openStream();
                    bimage = BitmapFactory.decodeStream(in);
                }catch (Exception e){
                    Log.e("Error Message", e.getMessage());
                    e.printStackTrace();
                }
                return bimage;
            }
            protected void onPostExecute (Bitmap result) {
                imageView.setImageBitmap(result);
            }
          }

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Delete button that deletes the post
     * @param view listens for button click
     */
    public void deleteBtn(View view) {
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        dbhc.deletePost(username, title);
        Intent intent = new Intent(ViewPost.this, ViewMyPosts.class);
        intent.putExtra("username", username);
        intent.putExtra("password", password);
        startActivity(intent);
        finish();
    }

    public void removeFromSaved(View view){
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        dbhc.removeFromSaved(post.getOwner(),username, title);
        removeFromSaved.setVisibility(View.INVISIBLE);
        addToSaved.setVisibility(View.VISIBLE);
    }

    public void addToSaved(View view){
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewPost.this);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        dbhc.savePost(post.getOwner(),username, title);
        removeFromSaved.setVisibility(View.VISIBLE);
        addToSaved.setVisibility(View.INVISIBLE);
    }

//    @Override
//    protected void onNewIntent(Intent intent){
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }


    }





