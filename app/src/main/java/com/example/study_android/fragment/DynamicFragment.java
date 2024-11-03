package com.example.study_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.study_android.R;

public class DynamicFragment extends Fragment {
    public static DynamicFragment newInstance(int position,String name, String desc) {
        DynamicFragment fragment = new DynamicFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("name", name);
        args.putString("desc", desc);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*
         * container：fragment根据该容器计算宽高
         * false：是否将该fragment添加到container容器中
         * */
        View view = inflater.inflate(R.layout.fragment_dynamic, container, false);

        Bundle arguments = getArguments();

        if (arguments != null) {
            TextView name = view.findViewById(R.id.name);
            TextView desc = view.findViewById(R.id.desc);
            name.setText(arguments.getString("name"));
            desc.setText(arguments.getString("desc"));
        }

        return view;
    }
}