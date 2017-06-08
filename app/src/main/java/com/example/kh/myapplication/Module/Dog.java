package com.example.kh.myapplication.Module;

import io.realm.RealmObject;

/**
 * Created by kh on 6/8/2017.
 */

public class Dog extends RealmObject {
    private String name;
    private String color;
    public Dog(){

    }
    public Dog(String name, String color){
        this.name = name;
        this.color = color;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
