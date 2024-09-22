package com.example.study_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AppWriteActivity extends AppCompatActivity {
    EditText nameInput, ageInput;
    MyApplication mApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_write);
        Button btn = findViewById(R.id.save11);
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);
        mApp = MyApplication.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mApp.infoMap.put("name", nameInput.getText().toString());
                mApp.infoMap.put("age", ageInput.getText().toString());
            }
        });

        reload();
    }

    private void reload() {
        String name = mApp.infoMap.get("name");

        if (name == null) {
            return;
        }

        String age = mApp.infoMap.get("age");
        nameInput.setText(name);
        ageInput.setText(age);
    }
}