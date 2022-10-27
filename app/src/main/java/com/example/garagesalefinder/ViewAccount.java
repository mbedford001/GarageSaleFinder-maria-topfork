package com.example.garagesalefinder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ViewAccount extends AppCompatActivity {
    Button viewProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_account);
    }
    public void go(View view){
        TextView output = findViewById(R.id.textView);
        output.setText("You tapped the button!");
    }

}