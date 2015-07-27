package net.sarazan.statement.data;

import android.support.annotation.Nullable;

import java.io.Serializable;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Aaron Sarazan on 7/23/15
 * Copyright(c) 2015 Level, Inc.
 */
public class Item extends RealmObject implements Serializable {

    @PrimaryKey
    private long id;

    private String text;

    public Item() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getId() {
        return id;
    }

    // You shouldn't call this by hand.
    @Deprecated
    public void setId(long id) {
        this.id = id;
    }

    public static Item create(String name) {
        Item retval = new Item();
        retval.id = System.currentTimeMillis();
        retval.text = name;
        return retval;
    }

    @Nullable
    public static Item withId(Realm realm, long id) {
        return realm.where(Item.class).equalTo("id", id).findFirst();
    }
}
