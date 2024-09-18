package com.example.study_android;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.study_android.util.ViewUtil;

public class EditTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text);
        EditText textinput = findViewById(R.id.textinput);
        TextView tv = findViewById(R.id.tv);

        textinput.addTextChangedListener(new HideTextWatcher(textinput, 11));


//        textinput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b) {
//                    String str = textinput.getText().toString();
//
//                    if (TextUtils.isEmpty(str) || str.length() < 11) {
//                        textinput.requestFocus();
//                        Toast.makeText(EditTextActivity.this, "请输入正确号码", Toast.LENGTH_SHORT).show();
//                    }
//                    tv.setText(str);
//                }
//            }
//        });
    }

    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int maxLength;

        public HideTextWatcher(EditText v, int maxLength) {
            this.mView = v;
            this.maxLength = maxLength;
        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String str = editable.toString();
            if (str.length() == maxLength) {
                ViewUtil.hideOneInputMethod(EditTextActivity.this, mView);
            }

        }
    }
}