package com.example.study_android.entity;

import java.util.ArrayList;

public class GoodsInfo {
    public int id;
    public String  name;
    public String  description;

    private static String[] mNameArray = {
            "iPhone11", "Mate30", "小米10", "OPPO Reno3", "vivo X30", "荣耀30S"
    };

    // 声明一个手机商品的描述数组
    private static String[] mDescArray = {
            "Apple iPhone11 256GB 绿色 4G全网通手机",
            "华为 HUAWEI Mate30 8GB+256GB 丹霞橙 5G全网通 全面屏手机",
            "小米 MI10 8GB+128GB 钛银黑 5G手机 游戏拍照手机",
            "OPPO Reno3 8GB+128GB 蓝色星夜 双模5G 拍照游戏智能手机",
            "vivo X30 8GB+128GB 绯云 5G全网通 美颜拍照手机",
            "荣耀30S 8GB+128GB 蝶羽红 5G芯片 自拍全面屏手机"
    };


    // 获取默认的手机信息列表
    public static ArrayList<GoodsInfo> getDefaultList() {
        ArrayList<GoodsInfo> goodsList = new ArrayList<GoodsInfo>();
        for (int i = 0; i < mNameArray.length; i++) {
            GoodsInfo info = new GoodsInfo();
            info.id = i;
            info.name = mNameArray[i];
            info.description = mDescArray[i];
            goodsList.add(info);
        }
        return goodsList;
    }
}
