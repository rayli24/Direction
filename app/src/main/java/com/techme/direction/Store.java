package com.techme.direction;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "store_table")
public class Store {
    @PrimaryKey(autoGenerate = true)
    private long id;

    private String countryName;

    private String name;

    private String type;

    private boolean selected;

    private int time;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] logo;

    public Store(String name, int time, byte[] logo, String type, String countryName, boolean selected) {
        this.name = name;
        this.time = time;
        this.logo = logo;
        this.type = type;
        this.countryName = countryName;
        this.selected = selected;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getType(){return type;}

    public String getCountryName() {
        return countryName;
    }

    public String getName() {
        return name;
    }

    public int getTime() {
        return time;
    }

    public byte[] getLogo() {
        return logo;
    }

    public boolean getSelected(){return selected;}
}

