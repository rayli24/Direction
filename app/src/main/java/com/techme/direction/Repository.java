package com.techme.direction;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

/**
 * Class: this is to prevent the ViewModel class and Room to be in direct contact with each other
 */
public class Repository {
    private Dao dao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<ToDoList>> allToDoList;
    private LiveData<List<Store>> allSelectedStores;
    private LiveData<List<Store>> allUnSelectedStores;
    private LiveData<List<Country>> allCountries;


//    private String name;
    public Repository(Application application) {
        DatabaseRoom database = DatabaseRoom.getInstance(application);
        dao = database.dao();
        allNotes = dao.getAllNotes();
        allToDoList = dao.getAllToDoList();
        allSelectedStores = dao.getAllSelectedStores();
        allCountries = dao.getAllCountries();
        allUnSelectedStores = dao.getAllUnSelectedStores();

    }

    public List<Note> searchNote(String name) throws ExecutionException, InterruptedException {
        SearchNoteAsyncTask search = new SearchNoteAsyncTask(dao);
        search.execute(name);
        return search.get();
    }

    public List<Store> searchMyStore(String name) throws ExecutionException, InterruptedException {
        SearchMyStoreAsyncTask search= new SearchMyStoreAsyncTask(dao);
        search.execute(name);
        return search.get();
    }

    public List<Store> searchAddStore(String name) throws ExecutionException, InterruptedException {
        SearchAddStoreAsyncTask search = new SearchAddStoreAsyncTask(dao);
        search.execute(name);
        return search.get();
    }

    private static class SearchAddStoreAsyncTask extends  AsyncTask<String,Void,List<Store>>{
        private Dao dao;
        private SearchAddStoreAsyncTask(Dao dao){this.dao = dao;}

        @Override
        protected List<Store> doInBackground(String... strings) {
            List<Store> list = dao.searchAddStore(strings[0]);
            return list.size() > 0? list: new ArrayList<Store>() ;
        }
    }

    private static class SearchMyStoreAsyncTask extends AsyncTask<String, Void, List<Store>>{
        private Dao dao;
        private SearchMyStoreAsyncTask(Dao dao){ this.dao = dao;}
        @Override
        protected List<Store> doInBackground(String... strings) {
            List<Store> list = dao.searchMyStore(strings[0]);
            return list.size() > 0? list: new ArrayList<Store>();
        }
    }

    private static class SearchNoteAsyncTask extends AsyncTask<String,Void,List<Note>>{
        private Dao dao;
        private SearchNoteAsyncTask(Dao dao){
            this.dao =dao;
        }
        @Override
        protected List<Note> doInBackground(String... strings) {
            List<Note> list = dao.searchNote(strings[0]);
            return list.size() > 0? list: new ArrayList<Note>();

        }
    }



    public void insertNotes(Note note) {
        new InsertNotesAsyncTask(dao).execute(note);
    }

    public void insertToDoList(ToDoList toDoList) {
        new InsertToDoListAsyncTask(dao).execute(toDoList);
    }

    public void updateToDoList(ToDoList toDoList) {
        new UpdateToDoListAsyncTask(dao).execute(toDoList);
    }

    public void updateCountry(Country country){
        new UpdateCountryAsyncTask(dao).execute(country);
    }

    public void updateNote(Note note) {
        new UpdateNoteAsyncTask(dao).execute(note);
    }

    public void updateStore(Store store) {
        new UpdateStoreAsyncTask(dao).execute(store);
    }

    public void deleteNote(Note note) {
        new DeleteNoteAsyncTask(dao).execute(note);
    }

    public void deleteToDoList(ToDoList toDoList) {
        new DeleteToDoListAsyncTask(dao).execute(toDoList);
    }

    public void deleteAllToDoList() {new DeleteAllToDoListAsyncTask(dao).execute();}

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(dao).execute();
    }

    public void deleteAllNotesId(long id) {
        new DeleteAllNotesIdAsyncTask(dao, id).execute();
    }


    // Live Data for the tables
    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Country>> getAllCountries() {
        return allCountries;
    }

    public LiveData<List<ToDoList>> getAllToDoList() {
        return allToDoList;
    }

    public LiveData<List<Store>> getAllSelectedStores() {
        return allSelectedStores;
    }

    public LiveData<List<Store>> getAllUnSelectedStores() {
        return allUnSelectedStores;
    }

    private static class InsertNotesAsyncTask extends AsyncTask<Note, Void, Void> {
        private Dao dao;

        private InsertNotesAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.insertNote(notes[0]);
            return null;
        }
    }

    private static class InsertToDoListAsyncTask extends AsyncTask<ToDoList, Void, Void> {
        private Dao dao;

        private InsertToDoListAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ToDoList... toDoLists) {
            dao.insertToDoList(toDoLists[0]);
            return null;
        }
    }

    private static class UpdateToDoListAsyncTask extends AsyncTask<ToDoList, Void, Void> {
        private Dao dao;

        private UpdateToDoListAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ToDoList... toDoLists) {
            dao.updateToDoList(toDoLists[0]);
            return null;
        }
    }

    private static class UpdateCountryAsyncTask extends AsyncTask<Country,Void,Void>{
        private Dao dao;
        private UpdateCountryAsyncTask(Dao dao){this.dao = dao;}
        @Override
        protected Void doInBackground(Country... countries) {
            dao.updateCountry(countries[0]);
            return null;
        }
    }

    private static class UpdateStoreAsyncTask extends AsyncTask<Store, Void, Void> {
        private Dao dao;

        private UpdateStoreAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Store... stores) {
            dao.updateStore(stores[0]);
            return null;
        }
    }

    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private Dao dao;

        private UpdateNoteAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.updateNote(notes[0]);
            return null;
        }
    }

    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private Dao dao;

        private DeleteNoteAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            dao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class DeleteToDoListAsyncTask extends AsyncTask<ToDoList, Void, Void> {
        private Dao dao;

        private DeleteToDoListAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(ToDoList... toDoLists) {
            dao.deleteToDoList(toDoLists[0]);
            return null;
        }
    }

    private static class DeleteAllNotesAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllNotesAsyncTask(Dao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllNotes();
            return null;
        }
    }

    private static class DeleteAllNotesIdAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;
        private long id;

        private DeleteAllNotesIdAsyncTask(Dao dao, long id) {
            this.dao = dao;
            this.id = id;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteNotesId(id);
            return null;
        }
    }

    private static class DeleteAllToDoListAsyncTask extends AsyncTask<Void,Void,Void>{
        private Dao dao;
        private DeleteAllToDoListAsyncTask(Dao dao){this.dao = dao;}
        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllToDoList();
            return null;
        }
    }

}
