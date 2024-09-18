package com.example.study_android;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class DatePickerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        DatePicker datePicker = findViewById(R.id.datePicker);
        TextView tv = findViewById(R.id.result);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String desc = String.format("您选择的日期是%d年%d月%d日", datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
//                tv.setText(desc);

//                打开dialogPicker
//                Calendar calendar = Calendar.getInstance();
//
//                DatePickerDialog dialog = new DatePickerDialog(DatePickerActivity.this, DatePickerActivity.this, 2024, 8, 2);
//                dialog.show();

//                timepicker
                TimePicker tmpicker=findViewById(R.id.tmpicker);
                tmpicker.setIs24HourView(true);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int m, int d) {

    }
}