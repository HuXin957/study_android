package com.example.study_android;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlertDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_dialog);
        TextView tv = findViewById(R.id.tv);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogActivity.this);
                builder.setTitle("尊敬的用户");
                builder.setMessage("你真的要卸载吗");
                builder.setPositiveButton("残忍卸载", (dialogInterface, i) -> {
                    tv.setText("卸载成功");
                });
                builder.setNegativeButton("我再想想", (dialogInterface, i) -> {
                    tv.setText("取消卸载");
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }
}