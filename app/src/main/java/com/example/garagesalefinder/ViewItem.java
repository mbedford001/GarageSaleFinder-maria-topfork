package com.example.garagesalefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.PostStuff.Post;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class ViewItem extends AppCompatActivity {
    Items item;
    ArrayList<Items> results3 = new ArrayList<Items>(0);
    public TextView TitleText, DescriptionText, QuantityText, PriceRangeText, ImageText;
    Button returnBtn;
    String title;
    Button back1Btn;
    Button editItemBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DataBaseHelperClass dbhc = new DataBaseHelperClass(ViewItem.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewitem);
        String username = getIntent().getStringExtra("username");
        String password = getIntent().getStringExtra("password");
        String from = getIntent().getStringExtra("source");
        int position = getIntent().getIntExtra("itemPosition", -1);
        results3 = (ArrayList<Items>) getIntent().getSerializableExtra("itemResults");
        //results3 = (ArrayList<Items>) getIntent().getParcelableExtra("results");
        String source = getIntent().getStringExtra("source");
        returnBtn = findViewById(R.id.btnBack);
        back1Btn = findViewById(R.id.back1Btn);
        editItemBtn = findViewById(R.id.editItemBtn);
        if (results3 == null) {
            System.out.println("---------------------PRINTING HERE-----------------");
            System.out.println("---------------------NOT WORKING-----------------");
        } else {
            System.out.println("---------------------PRINTING HERE-----------------");
            System.out.println(results3.toString());
        }
        item = results3.get(position);

        title = item.getItem_title();
        //String title = getIntent().getStringExtra("title");
        TitleText = findViewById(R.id.title);
        TitleText.setText(title);

        String description = item.getDescription();
        //String description = getIntent().getStringExtra("description");
        DescriptionText = findViewById(R.id.description);
        DescriptionText.setText(description);

        String priceRange = item.getPrice();
        //String priceRange = getIntent().getStringExtra("priceRange");
        PriceRangeText = findViewById(R.id.priceRange);
        PriceRangeText.setText(priceRange);

        String quantity = item.getQuantity();
        //String priceRange = getIntent().getStringExtra("priceRange");
        QuantityText = findViewById(R.id.quantity);
        QuantityText.setText(quantity);


        // String image = getIntent().getStringExtra("image");
        String image = item.getImage();
        System.out.println("image URL "+image);
        //Uri image = (Uri) item.getImage();
        //Drawable image1 = LoadImageFromWebOperations(image);
        //System.out.println("A Drawable: "+image1);
        //ImageView imageview = (ImageView) findViewById(R.id.imageView3);

        Drawable image1 = LoadImageFromWebOperations(image);
        new ViewItem.DownloadImageFromInternet((ImageView) findViewById(R.id.imageView)).execute(image);


        //String image = getIntent().getStringExtra("image");
        ImageText = findViewById(R.id.image);

        editItemBtn.setVisibility(View.GONE);

        String itemUsername = item.getSale_post_username();

        if (username.equals(itemUsername)){
            editItemBtn.setVisibility(View.VISIBLE);
        }

        //imageview.setImageURI(Uri image);
        //imageview.setImageDrawable(image1);
        //imageview.setImageResource(R.drawable.android_green_3d);


        //res/drawable/image1;
        /*ImageView iv = (ImageView)findViewById(v);
        iv.setImageResource(R.drawable.image_name);*/

        /*if (isUrlValid(image)) {
            InputStream inputStream = null;
            try {
                inputStream = (InputStream) new URL(image).getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Drawable drawable = Drawable.createFromStream(inputStream, "srcname");
        } else {
            System.out.println("the URL is invalid");

        }*/
        //ImageText = findViewById(R.id.image);
        //ImageText.setText(image);

        returnBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                Intent intent = new Intent(ViewItem.this,Menu.class);
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
                int postPosition = getIntent().getIntExtra("position", -1);
                ArrayList<Post> postResults = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Intent intent = new Intent(ViewItem.this,ViewItems.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("results", postResults);
                //intent.putExtra("itemResults", itemResults);
                intent.putExtra("source", "myItems");
                intent.putExtra("position", postPosition);
                startActivity(intent);
                finish();
            }
        });
        editItemBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String username = getIntent().getStringExtra("username");
                String password = getIntent().getStringExtra("password");
                int postPosition = getIntent().getIntExtra("position",-1);
                ArrayList<Post> postResults = (ArrayList<Post>) getIntent().getSerializableExtra("results");
                Post post = postResults.get(postPosition);
                Intent intent = new Intent(ViewItem.this,EditItem.class);
                intent.putExtra("username",username);
                intent.putExtra("password",password);
                intent.putExtra("itemTitle", title);
                intent.putExtra("source", "myPosts");
                intent.putExtra("itemResults", results3);//item Results
                intent.putExtra("itemPosition", position);//item position
                intent.putExtra("post", post);
                intent.putExtra("position",postPosition);
                intent.putExtra("results", postResults);
                startActivity(intent);
                finish();
            }
        });
    }
    public static boolean isUrlValid(String url) {
        try {
            URL obj = new URL(url);
            obj.toURI();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (URISyntaxException e) {
            return false;
        }
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

}
