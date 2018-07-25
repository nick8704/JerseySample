package com.company.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("first_name")
    private String firstName;
    @SerializedName("second_name")
    private String secondName;
    private int age;

    public User(String firstName) {
        this.firstName = firstName;
    }

    public User(String firstName, String secondName, int age) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}