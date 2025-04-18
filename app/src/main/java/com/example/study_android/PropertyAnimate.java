package com.example.study_android;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PropertyAnimate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animate);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f);
        valueAnimator.setDuration(2000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(@NonNull ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.e("leo", "value:" + value);
            }
        });
        valueAnimator.start();


        TextView tv = findViewById(R.id.tv);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(tv, "alpha", 0f, 1f);
        objectAnimator.setDuration(4000);
        objectAnimator.start();

        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(@NonNull Animator animation) {
                //开始
            }

            @Override
            public void onAnimationEnd(@NonNull Animator animation) {
                //结束
            }

            @Override
            public void onAnimationCancel(@NonNull Animator animation) {
                //取消
            }

            @Override
            public void onAnimationRepeat(@NonNull Animator animation) {
                //重复
            }
        });
    }
}