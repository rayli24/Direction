package com.techme.direction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "country_table")
public class Country {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;

    private int selected;

    public Country(String name, int selected)
    {
        this.name = name;
        this.selected = selected;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSelected(int selected){this.selected = selected;}

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public int getSelected(){return selected;}
}
