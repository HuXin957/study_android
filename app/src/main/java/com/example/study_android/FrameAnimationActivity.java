package com.example.study_android;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FrameAnimationActivity extends AppCompatActivity {
    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_animation);

        RelativeLayout relativeLayout = findViewById(R.id.main);
        AnimationDrawable anim = (AnimationDrawable) relativeLayout.getBackground();
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    anim.start();
                    flag = false;
                } else {
                    anim.stop();
                    flag = true;
                }
            }
        });
    }
}