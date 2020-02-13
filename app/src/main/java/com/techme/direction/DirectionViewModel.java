package com.techme.direction;

import android.app.Application;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DirectionViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<ToDoList>> allToDoList;
    private LiveData<List<Country>> allCountries;
    private LiveData<List<Store>> allSelectedStores;
    private LiveData<List<Store>> allUnSelectedStores;
//    private MutableLiveData<List<Note>> allSearchNotes;

    //test
//    private LiveData<List<Note>> searchTest;

    public DirectionViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCountries = repository.getAllCountries();
        allToDoList = repository.getAllToDoList();
        allNotes = repository.getAllNotes();
        allSelectedStores = repository.getAllSelectedStores();
        allUnSelectedStores = repository.getAllUnSelectedStores();
//        allSearchNotes = repository.getAllSearchNote();

        //test
//        searchTest = repository.getSearchTest();

    }




    // methods to be call to do sqlite database executions
    public void insertNote(Note note){repository.insertNotes(note);}
    public void insertToDoList(ToDoList toDoList){repository.insertToDoList(toDoList);}
    public void updateToDoList(ToDoList toDoList){repository.updateToDoList(toDoList);}
    public void updateStore(Store store){repository.updateStore(store);}
    public void updateNote(Note note){repository.updateNote(note);}
    public void deleteToDoList(ToDoList toDoList){repository.deleteToDoList(toDoList);}
    public void deleteNote(Note note){repository.deleteNote(note);}
    public void deleteAllNotes(){repository.deleteAllNotes();}
    public void deleteAllNotesId(long id){repository.deleteAllNotesId(id);}
//    public void searchNote(String name){repository.searchNote(name);}


    //test
    public List<Note> noteTest(String name) throws ExecutionException, InterruptedException { return repository.noteTest(name);}


    // Live Data for the tables
    public LiveData<List<Note>> getAllNotes(){return allNotes;}
    public LiveData<List<ToDoList>> getAllToDoList(){return allToDoList;}
    public LiveData<List<Country>> getAllCountries(){return allCountries;}
    public LiveData<List<Store>> getAllSelectedStores(){return allSelectedStores;}
    public LiveData<List<Store>> getAllUnSelectedStores(){return allUnSelectedStores;}
//    public LiveData<List<Note>> getAllSearchNotes(){return allSearchNotes;}
}
