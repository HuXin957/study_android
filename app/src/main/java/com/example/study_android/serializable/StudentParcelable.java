package com.example.study_android.serializable;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class StudentParcelable implements Parcelable {
    public String name;
    public int age;

    public StudentParcelable(){}

    protected StudentParcelable(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // 把对象写入到 Parcel对象里面去
    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    public static final Creator<StudentParcelable> CREATOR = new Creator<StudentParcelable>() {
        // 创建StudentParcelable对象 并且Parcel构建好，传递给我们的StudentParcelable（成员数据就可以从Parcel对象获取了）
        @Override
        public StudentParcelable createFromParcel(Parcel source) {
            return new StudentParcelable(source);
        }

        @Override
        public StudentParcelable[] newArray(int size) {
            return new StudentParcelable[size];
        }
    };
}
