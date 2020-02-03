package com.techme.direction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class Country {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    public Country(String name)
    {
        this.name = name;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
}
