package com.example.cpclient.entity;

public class Contact {
    public String name;
    public String phone;
    public String email;

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
