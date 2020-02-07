package com.techme.direction;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private long note_id;

    private int selected; // 0 is false, 1 is true

    private String name;

    public Note(String name, int selected) {
        this.name = name;
        this.selected = selected;
    }

    public long getNote_id() {
        return note_id;
    }

    public void setNote_id(long note_id) {
        this.note_id = note_id;
    }

    public void setSelected(int selected) { this.selected = selected; }

    public int getSelected() { return selected; }

    public String getName() {
        return name;
    }

}
