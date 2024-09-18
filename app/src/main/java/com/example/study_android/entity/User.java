package com.example.study_android.entity;

public class User {
    public int id;
    public String name;

    public User(){}

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
