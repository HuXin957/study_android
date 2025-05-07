package com.example.study_android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.serializable.Student;
import com.example.study_android.serializable.StudentParcelable;

public class SerializableActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serializable);

        TextView tv = findViewById(R.id.tv);

        Intent intent = getIntent();
        StudentParcelable stu = intent.getParcelableExtra("student");
        if (stu.name != null) {
            tv.setText(stu.name);
        }
    }

    public void startAction(View view) {
        Intent intent = new Intent(this, ParcelableActivity.class);
        Student student = new Student(1, "zs", 12);

        intent.putExtra("student", student);
        startActivity(intent);
    }
}