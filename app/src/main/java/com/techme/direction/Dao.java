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
    void insertToDoList(ToDoList toDoList);

    // sqlite update methods
    @Update
    void updateCountry(Country country);
    @Update
    void updateStore(Store store);
    @Update
    void updateNote(Note note);
    @Update
    void updateToDoList(ToDoList toDoList);

    // sqlite delete methods
    @Delete
    void deleteCountry(Country country);
    @Delete
    void deleteNote(Note note);
    @Delete
    void deleteToDoList(ToDoList toDoList);

    // delete all create notes that belong to note
    @Query("delete from to_do_list_table where note_id = :id")
    void deleteNotesId(long id);

    // delete all notes if you change country
    @Query("delete from note_table")
    void deleteAllNotes();

    // empty the whole store table
    @Query("delete from store_table")
    void deleteAllStore();

    @Query("delete from to_do_list_table")
    void deleteAllToDoList();

    @Query("delete from country_table")
    void deleteAllCountry();

    //get a specific search data
     @Query("select * from note_table where name like :name and selected = 1 order by name")
     List<Note> searchNote(String name);

     @Query("select * from store_table where name like :name and selected = 1 order by name")
     List<Store> searchMyStore(String name);

     @Query("select * from store_table where name like :name and selected = 0 order by name")
     List<Store> searchAddStore(String name);






    // get the data from database with LiveData
    @Query("select * from store_table where selected = 1 order by name") // 1 stands for true
    LiveData<List<Store>> getAllSelectedStores();

    @Query("select * from store_table where selected = 0 order by name") // 0 stands for false
    LiveData<List<Store>> getAllUnSelectedStores();

    @Query("select * from note_table order by name")
    LiveData<List<Note>> getAllNotes();

    @Query("select * from country_table order by name")
    LiveData<List<Country>> getAllCountries();

    @Query("select * from to_do_list_table order by timestamp desc")
    LiveData<List<ToDoList>> getAllToDoList();

}
