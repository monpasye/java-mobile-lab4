package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private Button btnGoToSecond;
    private TextView textViewResult;

    private static final int REQUEST_CODE_DATETIME = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.edit_text_name);
        btnGoToSecond = findViewById(R.id.btn_go_to_second);
        textViewResult = findViewById(R.id.text_view_result);

        btnGoToSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();

                if (name.isEmpty()) {
                    editTextName.setError("Введите имя!");
                } else {
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("USER_NAME", name);
                    startActivityForResult(intent, REQUEST_CODE_DATETIME);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_DATETIME && resultCode == RESULT_OK) {
            if (data != null) {
                String name = data.getStringExtra("USER_NAME");
                long dateTimeMillis = data.getLongExtra("SELECTED_DATETIME", 0);

                if (dateTimeMillis > 0) {
                    String formattedDate = android.text.format.DateFormat.format(
                            "dd.MM.yyyy HH:mm", dateTimeMillis).toString();

                    textViewResult.setText(name + ", вы выбрали: " + formattedDate);
                }
            }
        }
    }
}