package com.techme.direction;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Update;

/**
 * Class: this is to prevent the ViewModel class and Room to be in direct contact with each other
 */
public class Repository {
    private Dao dao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<CreateNote>> allCreateNotes;
    private LiveData<List<Store>> allStores;
    private LiveData<List<Store>> allGroceries;
    private LiveData<List<Store>> allDining;
    private LiveData<List<Country>> allCountries;


    public Repository(Application application)
    {
         DatabaseRoom database = DatabaseRoom.getInstance(application);
         dao = database.dao();
         allNotes = dao.getAllNotes();
         allCreateNotes = dao.getAllCreateNotes();
         allStores = dao.getAllStores();
         allCountries = dao.getAllCountries();
    }

    public void insertNotes(Note note){
        new InsertNotes(dao).execute(note);
    }

    public void insertCreateNote(CreateNote createNote){
        new InsertCreateNote(dao).execute(createNote);
    }

    public void updateCreateNote(CreateNote createNote){
        new UpdateCreateNote(dao).execute(createNote);
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
    public LiveData<List<Store>> getAllStores(){return allStores;}
    public LiveData<List<Store>> getAllGroceries(){return allGroceries;}
    public LiveData<List<Store>> getAllDining(){return allDining;}

    private static class InsertNotes extends AsyncTask<Note,Void,Void>
    {
        private Dao dao;

        private InsertNotes(Dao dao){this.dao = dao;}
        @Override
        protected Void doInBackground(Note... notes) {
            dao.insertNote(notes[0]);
            return null;
        }
    }

    private static class InsertCreateNote extends AsyncTask<CreateNote,Void,Void>
    {
        private Dao dao;
        private InsertCreateNote(Dao dao){this.dao =dao;}

        @Override
        protected Void doInBackground(CreateNote... createNotes) {
            dao.insertCreateNote(createNotes[0]);
            return null;
        }
    }

    private static class UpdateCreateNote extends AsyncTask<CreateNote,Void,Void>
    {
        private Dao dao;
        private UpdateCreateNote(Dao dao){this.dao =dao;}

        @Override
        protected Void doInBackground(CreateNote... createNotes) {
            dao.updateCreateNote(createNotes[0]);
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
