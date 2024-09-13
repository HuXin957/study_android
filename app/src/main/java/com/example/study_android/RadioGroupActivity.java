package com.example.study_android;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RadioGroupActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_group);

         tv = findViewById(R.id.result);
        RadioGroup radiogroup = findViewById(R.id.radiogroup);
        radiogroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (i) {
            case R.id.r_mans:
                tv.setText("男人");
                break;
            case R.id.r_womans:
                tv.setText("女人");
                break;
        }
    }
}