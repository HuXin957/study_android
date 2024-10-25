package com.example.study_android;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.study_android.adapter.PlaneBaseAdapter;
import com.example.study_android.bean.Planet;
import com.example.study_android.util.ToastUtil;
import com.example.study_android.util.Utils;

import java.util.List;

public class ListViewActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    private List<Planet> planetList;
    private CheckBox ck_selector;
    private CheckBox ck_divider;
    private ListView lv_planet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lv_planet = findViewById(R.id.lv_planet);
        planetList = Planet.getDefaultList();
        PlaneBaseAdapter adapter = new PlaneBaseAdapter(this, planetList);
        lv_planet.setAdapter(adapter);

        lv_planet.setOnItemClickListener(this);

        ck_divider = findViewById(R.id.ck_divider);
        ck_selector = findViewById(R.id.ck_selector);

        ck_divider.setOnCheckedChangeListener(this);
        ck_selector.setOnCheckedChangeListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        ToastUtil.show(this, planetList.get(i).name);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.ck_divider:
                if (ck_divider.isChecked()) {
                    Drawable drawable = getResources().getDrawable(R.color.black, getTheme());
                    lv_planet.setDivider(drawable);
                    lv_planet.setDividerHeight(Utils.dip2px(this, 1));
                } else {
                    lv_planet.setDivider(null);
                    lv_planet.setDividerHeight(0);
                }
                break;
            case R.id.ck_selector:
                if (ck_selector.isChecked()) {
                    lv_planet.setSelector(R.drawable.list_selector);
                } else {
                    Drawable drawable = getResources().getDrawable(R.color.transparent, getTheme());
                    lv_planet.setSelector(drawable);
                }
                break;
        }
    }
}