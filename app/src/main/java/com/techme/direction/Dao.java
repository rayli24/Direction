package com.techme.direction;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@androidx.room.Dao
public interface Dao {

    // sqlite insert methods
    @Insert
    void insertCountry(Country country);
    @Insert
    void insertStore(Store store);
    @Insert
    void insertNote(Note note);
    @Insert
    void insertCreateNote(CreateNote createNote);

    // sqlite update methods
    @Update
    void updateCountry(Country country);
    @Update
    void updateStore(Store store);
    @Update
    void updateCreateNote(CreateNote createNote);

    // sqlite delete methods
    @Delete
    void deleteCountry(Country country);
    @Delete
    void deleteNote(Note note);
    @Delete
    void deleteCreateNote(CreateNote createNote);

    // empty the whole store table
    @Query("delete from store_table")
    void deleteAllStore();

    // get the data from database with LiveData
    @Query("select * from store_table where selected = 1 order by time") // 1 stands for true
    LiveData<List<Store>> getAllSelectedStores();

    @Query("select * from store_table where selected = 0 order by name") // 0 stands for false
    LiveData<List<Store>> getAllUnSelectedStores();

    @Query("select * from store_table where type = 'grocery' order by name")
    LiveData<List<Store>> getAllGrocery();

    @Query("select * from store_table where type = 'dining' order by name")
    LiveData<List<Store>> getAllDining();

    @Query("select * from note_table order by name")
    LiveData<List<Note>> getAllNotes();

    @Query("select * from country_table order by name")
    LiveData<List<Country>> getAllCountries();

    @Query("select * from create_note_table order by timestamp desc")
    LiveData<List<CreateNote>> getAllCreateNotes();

}
