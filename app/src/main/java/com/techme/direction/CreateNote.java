package com.techme.direction;

import java.sql.Timestamp;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "create_note_table")
public class CreateNote {
    @PrimaryKey(autoGenerate = true)
    private long create_id;

    private String item;

    private int amount;

    private int timestamp; // keep incrementing to keep track of las input

    private boolean checked;
    @ForeignKey(entity = Note.class,parentColumns = "note_id",childColumns = "create_id",onDelete = ForeignKey.CASCADE)
    private long note_id;

    public CreateNote(String item, int amount, boolean checked,int timestamp, long note_id) {
        this.item = item;
        this.amount = amount;
        this.checked = checked;
        this.timestamp = timestamp;
        this.note_id = note_id;
    }

    public void setCreate_id(long create_id) {
        this.create_id = create_id;
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

    public long getCreate_id() {
        return create_id;
    }

    public String getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }
}
