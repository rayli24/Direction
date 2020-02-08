package com.techme.direction;

import android.app.Application;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class DirectionViewModel extends AndroidViewModel {
    private Repository repository;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<CreateNote>> allCreateNote;
    private LiveData<List<Country>> allCountries;
    private LiveData<List<Store>> allSelectedStores;
    private LiveData<List<Store>> allUnSelectedStores;


    public DirectionViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allCountries = repository.getAllCountries();
        allCreateNote = repository.getAllCreateNotes();
        allNotes = repository.getAllNotes();
        allSelectedStores = repository.getAllSelectedStores();
        allUnSelectedStores = repository.getAllUnSelectedStores();
    }

    // methods to be call to do sqlite database executions
    public void insertNote(Note note){repository.insertNotes(note);}
    public void insertCreateNote(CreateNote createNote){repository.insertCreateNote(createNote);}
    public void updateCreateNote(CreateNote createNote){repository.updateCreateNote(createNote);}
    public void updateStore(Store store){repository.updateStore(store);}
    public void updateNote(Note note){repository.updateNote(note);}
    public void deleteCreateNote(CreateNote createNote){repository.deleteCreateNote(createNote);}
    public void deleteNote(Note note){repository.deleteNote(note);}
    public void deleteAllNotes(){repository.deleteAllNotes();}

    // Live Data for the tables
    public LiveData<List<Note>> getAllNotes(){return allNotes;}
    public LiveData<List<CreateNote>> getAllCreateNote(){return allCreateNote;}
    public LiveData<List<Country>> getAllCountries(){return allCountries;}
    public LiveData<List<Store>> getAllSelectedStores(){return allSelectedStores;}
    public LiveData<List<Store>> getAllUnSelectedStores(){return allUnSelectedStores;}
}
