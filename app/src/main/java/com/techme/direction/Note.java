package com.techme.direction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long note_id;

    private String name;

    public Note(String name) {
        this.name = name;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getName() {
        return name;
    }

}
