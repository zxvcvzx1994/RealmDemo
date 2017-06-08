package com.example.kh.myapplication.Module;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmSchema;

/**
 * Created by kh on 6/8/2017.
 */

public class MyMigration implements RealmMigration {

    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        RealmSchema schema = realm.getSchema();
        if(oldVersion==0){

           schema.create("Person").addField("id",long.class).addField("name",String.class);
            oldVersion++;
        }
        if(oldVersion==1){
             schema.create("Dog").addField("name",String.class).addField("color",String.class);
            schema.get("Person").addRealmListField("dog", schema.get("Dog"));

            oldVersion++;
        }
    }
}
