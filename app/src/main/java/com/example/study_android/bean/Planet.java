package com.example.study_android.bean;

import com.example.study_android.R;

import java.util.ArrayList;
import java.util.List;

public class Planet {
    public int image;
    public String name;
    public String desc;

    public Planet(int image, String name, String desc) {
        this.image = image;
        this.name = name;
        this.desc = desc;
    }

    private static int[] iconArray = {
            R.drawable.shuixing,
            R.drawable.diqiu,
            R.drawable.jinxing,
            R.drawable.muxing,
            R.drawable.tuxing,
            R.drawable.huoxing,
    };

    private static String[] nameArray = {
            "金星",
            "木星",
            "水星",
            "火星",
            "土星",
            "地球",
    };

    private static String[] descArray = {
            "时间法律手段会计法李会计阿萨德",
            "时间法律手段会计法李会计阿萨德",
            "时间法律手段会计法李会计阿萨德",
            "时间法律手段会计法李会计阿萨德",
            "时间法律手段会计法李会计阿萨德",
            "时间法律手段会计法李会计阿萨德",
    };

    public static List<Planet> getDefaultList() {
        List<Planet> planetList = new ArrayList<>();
        for (int i = 0; i < iconArray.length; i++) {
            planetList.add(new Planet(iconArray[i], nameArray[i], descArray[i]));
        }

        return planetList;
    }
}
