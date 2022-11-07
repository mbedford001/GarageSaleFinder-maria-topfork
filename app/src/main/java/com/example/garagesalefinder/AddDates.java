package com.example.garagesalefinder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.garagesalefinder.PostStuff.Items;
import com.example.garagesalefinder.controllers.DataBaseHelperClass;

public class AddDates extends AppCompatActivity {

    EditText postName;
    EditText date;
    Button createButton;

    DataBaseHelperClass dbhc = new DataBaseHelperClass(AddDates.this);

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddates);
        postName = findViewById(R.id.inputPostName);
        date = findViewById(R.id.inputDate);
        createButton = findViewById(R.id.btnCreate);
        String username = getIntent().getStringExtra("username");

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String pDate = date.getText().toString().trim();
                String pPostName = postName.getText().toString().trim();

                if(TextUtils.isEmpty(pDate)){//verifies a location was entered
                    date.setError("Date is required.");
                    return;
                }

                if(TextUtils.isEmpty(pPostName)){
                    postName.setError("Post Name is required.");
                    return;
                }
                dbhc.addDate(pDate, pPostName, username);
            }

        });
    }

    public void moveToAddItems(View view){
        Intent intent = new Intent(getApplicationContext(), AddItems.class);
        String username = getIntent().getStringExtra("username");
        intent.putExtra("username",username);
        startActivity(intent);
        //startActivity(new Intent(getApplicationContext(), AddDates.class));
        finish();
    }

    public void backToMenu2(View view){
        startActivity(new Intent(getApplicationContext(), Menu.class));
        finish();
    }
}



