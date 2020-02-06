package com.techme.direction;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * Class: this is to prevent the ViewModel class and Room to be in direct contact with each other
 */
public class Repository {
    private Dao dao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<CreateNote>> allCreateNotes;
    private LiveData<List<Store>> allSelectedStores;
    private LiveData<List<Store>> allUnSelectedStores;
    private LiveData<List<Store>> allGroceries;
    private LiveData<List<Store>> allDining;
    private LiveData<List<Country>> allCountries;


    public Repository(Application application)
    {
         DatabaseRoom database = DatabaseRoom.getInstance(application);
         dao = database.dao();
         allNotes = dao.getAllNotes();
         allCreateNotes = dao.getAllCreateNotes();
         allSelectedStores = dao.getAllSelectedStores();
         allCountries = dao.getAllCountries();
         allGroceries = dao.getAllGrocery();
         allDining = dao.getAllDining();
         allUnSelectedStores = dao.getAllUnSelectedStores();
    }

    public void insertNotes(Note note){
        new InsertNotesAsyncTask(dao).execute(note);
    }

    public void insertCreateNote(CreateNote createNote){
        new InsertCreateNoteAsyncTask(dao).execute(createNote);
    }

    public void updateCreateNote(CreateNote createNote){
        new UpdateCreateNoteAsyncTask(dao).execute(createNote);
    }

    public void updateStore(Store store){
        new UpdateStoreAsyncTask(dao).execute(store);
    }

    public void deleteNote(Note note){
        new DeleteNoteAsyncTask(dao).execute(note);
    }

    public void deleteCreateNote(CreateNote createNote){
        new DeleteCreateAsyncTask(dao).execute(createNote);
    }

    // Live Data for the tables
    public LiveData<List<Note>> getAllNotes(){return allNotes;}
    public LiveData<List<Country>> getAllCountries(){return allCountries;}
    public LiveData<List<CreateNote>> getAllCreateNotes(){return allCreateNotes;}
    public LiveData<List<Store>> getAllSelectedStores(){return allSelectedStores;}
    public LiveData<List<Store>> getAllUnSelectedStores(){return allUnSelectedStores;}
    public LiveData<List<Store>> getAllGroceries(){return allGroceries;}
    public LiveData<List<Store>> getAllDining(){return allDining;}

    private static class InsertNotesAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private Dao dao;

        private InsertNotesAsyncTask(Dao dao){this.dao = dao;}
        @Override
        protected Void doInBackground(Note... notes) {
            dao.insertNote(notes[0]);
            return null;
        }
    }

    private static class InsertCreateNoteAsyncTask extends AsyncTask<CreateNote,Void,Void>
    {
        private Dao dao;
        private InsertCreateNoteAsyncTask(Dao dao){this.dao =dao;}

        @Override
        protected Void doInBackground(CreateNote... createNotes) {
            dao.insertCreateNote(createNotes[0]);
            return null;
        }
    }

    private static class UpdateCreateNoteAsyncTask extends AsyncTask<CreateNote,Void,Void>
    {
        private Dao dao;
        private UpdateCreateNoteAsyncTask(Dao dao){this.dao =dao;}

        @Override
        protected Void doInBackground(CreateNote... createNotes) {
            dao.updateCreateNote(createNotes[0]);
            return null;
        }
    }

    private static class UpdateStoreAsyncTask extends AsyncTask<Store,Void,Void>
    {
        private Dao dao;
        private UpdateStoreAsyncTask(Dao dao) {this.dao = dao;}

        @Override
        protected Void doInBackground(Store... stores) {
            dao.updateStore(stores[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note,Void,Void>
    {
        private Dao dao;
        private DeleteNoteAsyncTask(Dao dao){this.dao = dao;}

        @Override
        protected Void doInBackground(Note... notes) {
            dao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteCreateAsyncTask extends AsyncTask<CreateNote,Void,Void>
    {
        private Dao dao;
        private DeleteCreateAsyncTask(Dao dao){this.dao = dao;}
        @Override
        protected Void doInBackground(CreateNote... createNotes) {
            dao.deleteCreateNote(createNotes[0]);
            return null;
        }
    }


}
