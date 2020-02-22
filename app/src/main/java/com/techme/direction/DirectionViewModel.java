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


    public DirectionViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCountries = repository.getAllCountries();
        allToDoList = repository.getAllToDoList();
        allNotes = repository.getAllNotes();
        allSelectedStores = repository.getAllSelectedStores();
        allUnSelectedStores = repository.getAllUnSelectedStores();

    }


    // methods to be call to do sqlite database executions
    public void insertNote(Note note) {
        repository.insertNotes(note);
    }

    public void insertToDoList(ToDoList toDoList) {
        repository.insertToDoList(toDoList);
    }

    public void updateToDoList(ToDoList toDoList) {
        repository.updateToDoList(toDoList);
    }

    public void updateStore(Store store) {
        repository.updateStore(store);
    }

    public void updateNote(Note note) {
        repository.updateNote(note);
    }

    public void updateCountry(Country country){
        repository.updateCountry(country);
    }

    public void deleteToDoList(ToDoList toDoList) {
        repository.deleteToDoList(toDoList);
    }

    public void deleteNote(Note note) {
        repository.deleteNote(note);
    }

    public void deleteAllNotes() {
        repository.deleteAllNotes();
    }

    public void deleteAllNotesId(long id) {
        repository.deleteAllNotesId(id);
    }

    public void deleteAllToDoList() {repository.deleteAllToDoList();}


    // methods for search views
    public List<Note> searchNote(String name) throws ExecutionException, InterruptedException {
        return repository.searchNote(name);
    }

    public List<Store> searchMyStore(String name) throws ExecutionException, InterruptedException {
        return repository.searchMyStore(name);
    }

    public List<Store> searchAddStore(String name) throws ExecutionException, InterruptedException {
        return repository.searchAddStore(name);
    }


    // Live Data for the tables
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<ToDoList>> getAllToDoList() {
        return allToDoList;
    }

    public LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }

    public LiveData<List<Store>> getAllSelectedStores() {
        return allSelectedStores;
    }

    public LiveData<List<Store>> getAllUnSelectedStores() {
        return allUnSelectedStores;
    }
}
