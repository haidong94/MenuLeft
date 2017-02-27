package com.example.dong.menuleft.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by DONG on 08-Feb-17.
 */

public class Types extends RealmObject {
    @PrimaryKey
    private int type_id;
    private String name;

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
