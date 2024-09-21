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

import com.example.study_android.util.ToastUtil;

public class ShareWriteActivity extends AppCompatActivity {
    SharedPreferences storage;
    EditText nameInput, ageInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_write);

        Button btn = findViewById(R.id.save11);
        nameInput = findViewById(R.id.nameInput);
        ageInput = findViewById(R.id.ageInput);

        storage = getSharedPreferences("storage", Context.MODE_PRIVATE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameInput.getText().toString();
                String age = ageInput.getText().toString();

                SharedPreferences.Editor editor = storage.edit();
                editor.putString("name", name);
                editor.putInt("age", Integer.parseInt(age));

                boolean isSuccess = editor.commit();

                if(isSuccess){
                    ToastUtil.show(ShareWriteActivity.this,"保存成功");
                }
            }
        });
        reload();
    }

    private void reload() {
        String name = storage.getString("name", null);
        int age = storage.getInt("age", 0);

        if (name != null) {
            nameInput.setText(name);
        }
        if (age != 0) {
            ageInput.setText(String.valueOf(age));
        }
    }
}