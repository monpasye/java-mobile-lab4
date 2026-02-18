package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewName = findViewById(R.id.text_view_name);

        Intent intent = getIntent();

        String name = intent.getStringExtra("USER_NAME");

        if (name != null && !name.isEmpty()) {
            textViewName.setText(name);
        } else {
            textViewName.setText("Аноним");
        }
    }
}