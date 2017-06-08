package com.example.kh.myapplication.Module;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kh on 6/8/2017.
 */

public class Person extends RealmObject {
    @PrimaryKey
    private long id;
    private String name;
    private RealmList<Dog> dog;
    public Person(){

    }

    public Person(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Dog> getDog() {
        return dog;
    }

    public void setDog(RealmList<Dog> dog) {
        this.dog = dog;
    }
}
