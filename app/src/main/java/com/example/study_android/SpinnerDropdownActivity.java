package com.example.study_android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpinnerDropdownActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final int[] iconArray = {
            R.drawable.shuixing, R.drawable.tuxing,
            R.drawable.muxing, R.drawable.diqiu,
            R.drawable.huoxing, R.drawable.jinxing,
    };
    private static final String[] starArray = {"金星", "水星", "木星", "地球", "木星", "土星"};
    private Spinner sp_dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_dropdown);
        sp_dropdown = findViewById(R.id.sp_dropdown);

        // //=====================
        // ArrayAdapter
        // ArrayAdapter<String> starArrayAdapter = new ArrayAdapter<>(this, R.layout.item_select, starArray);
        // sp_dropdown.setAdapter(starArrayAdapter);
        // // 设置下拉框默认显示第一项
        // sp_dropdown.setSelection(0);
        // sp_dropdown.setOnItemSelectedListener(this);
        // //=====================

        // //=====================
        // SimpleAdapter
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < iconArray.length; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("icon", iconArray[i]);
            item.put("name", starArray[i]);

            list.add(item);
        }
        SimpleAdapter starSimpleAdapter = new SimpleAdapter(this, list,
                R.layout.item_simple,
                new String[]{"icon", "name"},
                new int[]{R.id.iv_icon, R.id.tv_name});

        sp_dropdown.setAdapter(starSimpleAdapter);
        sp_dropdown.setSelection(0);
        sp_dropdown.setOnItemSelectedListener(this);
        // //=====================
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        Log.d("你选择的是", starArray[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}