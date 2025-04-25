package com.example.study_android;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.study_android.fragment.BlankFragment;
import com.example.study_android.fragment.BlankFragment2;
import com.example.study_android.fragment.IFragmentCallback;
import com.example.study_android.util.ToastUtil;

public class SwitchFragmentActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch_fragment);
        Button button = findViewById(R.id.btn_switch);
        Button button2 = findViewById(R.id.btn_switch2);

        button.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    private void replaceFragment(Fragment fm) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.my_framelayout, fm);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_switch:
                Bundle bundle = new Bundle();
                bundle.putString("message", "wonima");
                BlankFragment bf = new BlankFragment();
                bf.setArguments(bundle);

                bf.setFragmentCallback(new IFragmentCallback() {
                    @Override
                    public void sendMsgToActivity(String string) {
                        ToastUtil.show(SwitchFragmentActivity.this, string);
                    }

                    @Override
                    public String getMsgFromActivity(String msg) {
                        return "";
                    }
                });

                replaceFragment(bf);
                break;
            case R.id.btn_switch2:
                replaceFragment(new BlankFragment2());

        }
    }
}