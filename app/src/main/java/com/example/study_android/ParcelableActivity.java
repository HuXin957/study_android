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

public class ParcelableActivity extends AppCompatActivity {

    private TextView tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcelable);

        tv_name = findViewById(R.id.name);

        Intent intent = getIntent();
        Student student = (Student) intent.getSerializableExtra("student");
        if (student != null) {
            tv_name.setText(student.name);
        }
    }

    public void startAction(View view) {
        Intent intent = new Intent(this, SerializableActivity.class);
        StudentParcelable stu = new StudentParcelable();
        stu.name = "zssss";
        stu.age = 12;
        intent.putExtra("student", stu);
        startActivity(intent);
    }
}