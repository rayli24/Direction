package com.techme.direction;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class Store {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String country_name;

    private String name;

    private String type;

    private boolean selected;

    private int time;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] logo;

    public Store(String name, int time, byte[] logo, String type, String country_name, boolean selected) {
        this.name = name;
        this.time = time;
        this.logo = logo;
        this.type = type;
        this.country_name = country_name;
        this.selected = selected;
    }

    public long getId() {
        return id;
    }

    public String getType(){return type;}

    public String getCountryName() {
        return country_name;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public byte[] getImage() {
        return logo;
    }

    public boolean getSelected(){return selected;}
}

