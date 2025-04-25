package com.example.study_android.fragment;

public interface IFragmentCallback {
    void sendMsgToActivity(String string);

    String getMsgFromActivity(String msg);
}
