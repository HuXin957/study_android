package com.example.study_android;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AlphaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alpha);

        ImageView iv = findViewById(R.id.iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animation = AnimationUtils.loadAnimation(AlphaActivity.this, R.anim.alpha);
//                Animation animation = AnimationUtils.loadAnimation(AlphaActivity.this, R.anim.rotate);
//                Animation animation = AnimationUtils.loadAnimation(AlphaActivity.this, R.anim.scale);
                Animation animation = AnimationUtils.loadAnimation(AlphaActivity.this, R.anim.translate);
                iv.startAnimation(animation);
            }
        });
    }
}