package com.techme.direction;

import java.sql.Timestamp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "to_do_list_table")
@ForeignKey(entity = Note.class,parentColumns = "note_id",childColumns = "create_id",onDelete = ForeignKey.CASCADE)
public class ToDoList {
    @PrimaryKey(autoGenerate = true)
    private long to_do_id;

    private String item;

    private int amount;

    private int timestamp; // keep incrementing to keep track of las input

    private boolean checked;

    private long note_id;

    public ToDoList(String item, int amount, boolean checked, int timestamp, long note_id) {
        this.item = item;
        this.amount = amount;
        this.checked = checked;
        this.timestamp = timestamp;
        this.note_id = note_id;
    }

    public void setTo_do_id(long to_do_id) {
        this.to_do_id = to_do_id;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int timestamp()
    {
        return timestamp;
    }

    public boolean isChecked() {
        return checked;
    }

    public long getNote_id() {
        return note_id;
    }

    public long getTo_do_id() {
        return to_do_id;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
