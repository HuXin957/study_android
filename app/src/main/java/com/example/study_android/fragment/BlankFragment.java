package com.example.study_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.study_android.R;
import com.example.study_android.util.ToastUtil;


public class BlankFragment extends Fragment {

    public BlankFragment() {
    }

    private IFragmentCallback fragmentCallback;

    public void setFragmentCallback(IFragmentCallback callback) {
        fragmentCallback = callback;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        String msg = bundle.getString("message");

        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        TextView tv = view.findViewById(R.id.tv);
        tv.setText(msg);

        Button btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentCallback.sendMsgToActivity("我尼玛");
            }
        });
        return view;
    }
}