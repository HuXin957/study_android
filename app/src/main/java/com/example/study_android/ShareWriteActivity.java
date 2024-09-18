package com.example.study_android;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareWriteActivity extends AppCompatActivity {
    SharedPreferences storage;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_write);

        Button btn = findViewById(R.id.save11);
        input = findViewById(R.id.input);

        storage = getSharedPreferences("storage", Context.MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = input.getText().toString();

                SharedPreferences.Editor editor = storage.edit();
                editor.putString("name", name);
                editor.commit();
            }
        });
        reload();
    }

    private void reload() {
        String name = storage.getString("name", null);
        if (name != null) {
            input.setText(name);
        }
    }
}