package com.example.study_android.util;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NetUtil {
    public static String doGeg() {
        String result = "";
        BufferedReader reader;
        try {
            // 1.建立连接
            HttpURLConnection httpURLConnection = null;
            String url = "https://www.baidu.com";
            URL requestUrl = new URL(url);
            httpURLConnection = (HttpURLConnection) requestUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.connect();

            // 2.获取二进制流
            InputStream inputStream = httpURLConnection.getInputStream();

            // 3.将二进制流包装
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // 从Buffer reader中读取string字符串
            String line;
            StringBuilder builder = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) {
                return null;
            }
            result = builder.toString();
        } catch (Exception e) {
            Log.d("EEEE", "1");
            e.printStackTrace();
        }
        return result;
    }

    public static boolean doPost(String utlStr) {
        HttpURLConnection urlConnection = null;
        OutputStream outputStream = null;
        boolean result = false;

        try {
            URL url = new URL(utlStr);

            // 1.打开连接
            urlConnection = (HttpURLConnection) url.openConnection();
            // 2.准备请求数据
            Map<String, String> paramMap = new HashMap<>();
            paramMap.put("userName", "zs");
            paramMap.put("pass", "123");
            String paramData = paramMapToString(paramMap);

            // 3.设置连接信息
            urlConnection.setRequestMethod("POST");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestProperty("Content-Length", String.valueOf(paramData.length()));

            // 设置conn可以向服务端输出的内容
            urlConnection.setDoOutput(true);

            // 4.获取输出流，并进行输出
            outputStream = urlConnection.getOutputStream();
            outputStream.write(paramData.getBytes());

            // 5.获取服务端的响应结果
            int code = urlConnection.getResponseCode();
            if (code == 200) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public static String paramMapToString(Map<String, String> paramMap) {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<String, String>> entries = paramMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            sb.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }
        // 去掉最后一个&
        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    public static void handleJson(String jsonStr) {
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            String name = jsonObject.optString("name");
            Log.d("AAAA", name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
