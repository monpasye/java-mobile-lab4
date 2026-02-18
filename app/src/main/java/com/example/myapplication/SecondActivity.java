package com.example.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class SecondActivity extends AppCompatActivity {

    private TextView textViewWelcome;
    private TextView textViewSelectedDateTime;
    private Button btnSelectDate;
    private Button btnSelectTime;
    private Button btnOk;

    private Calendar selectedCalendar = Calendar.getInstance();
    private boolean dateSelected = false;
    private boolean timeSelected = false;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textViewWelcome = findViewById(R.id.text_view_welcome);
        textViewSelectedDateTime = findViewById(R.id.text_view_selected_datetime);
        btnSelectDate = findViewById(R.id.btn_select_date);
        btnSelectTime = findViewById(R.id.btn_select_time);
        btnOk = findViewById(R.id.btn_ok);

        userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            textViewWelcome.setText("Привет, " + userName + "! Выбери дату и время");
        }

        btnSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnSelectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dateSelected && timeSelected) {
                    long millis = selectedCalendar.getTimeInMillis();

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("USER_NAME", userName);
                    resultIntent.putExtra("SELECTED_DATETIME", millis);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(SecondActivity.this,
                            "Выберите дату и время!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDatePicker() {
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedCalendar.set(Calendar.YEAR, year);
                        selectedCalendar.set(Calendar.MONTH, month);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        dateSelected = true;
                        updateDisplay();
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePicker() {
        int hour = selectedCalendar.get(Calendar.HOUR_OF_DAY);
        int minute = selectedCalendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedCalendar.set(Calendar.MINUTE, minute);
                        timeSelected = true;
                        updateDisplay();
                    }
                }, hour, minute, true);

        timePickerDialog.show();
    }

    private void updateDisplay() {
        if (dateSelected && timeSelected) {
            String formatted = android.text.format.DateFormat.format(
                    "dd.MM.yyyy HH:mm", selectedCalendar).toString();
            textViewSelectedDateTime.setText("Выбрано: " + formatted);
            btnOk.setEnabled(true);
        }
    }
}