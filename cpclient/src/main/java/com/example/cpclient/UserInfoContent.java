package com.example.cpclient;

import android.net.Uri;

public class UserInfoContent {
    public static final String AUTHORITIES = "com.example.cpserve.provider.MyContentProvider";

    // content://com.example.cpserve.provider.MyContentProvider/user
    // 访问内容提供器的uri
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

}
