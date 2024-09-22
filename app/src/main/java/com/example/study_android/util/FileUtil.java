package com.example.study_android.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {
    // 把字符串保存到指定路径的文本文件
    public static void saveText(String path, String txt) {
        BufferedWriter os = null;
        try {
            os = new BufferedWriter(new FileWriter(path));
            os.write(txt);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 从指定路径的文本文件中读取内容字符串
    public static String openText(String path) {
        BufferedReader is = null;
        StringBuilder sb = new StringBuilder();
        try {
            is = new BufferedReader(new FileReader(path));
            String line;

            while ((line = is.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    public static void saveImage(String path, Bitmap bitmap) {
        try (FileOutputStream fos = new FileOutputStream(path)) {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap readImage(String path) {
        Bitmap bitmap = null;

        try (FileInputStream fis = new FileInputStream(path)) {
            bitmap = BitmapFactory.decodeStream(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
