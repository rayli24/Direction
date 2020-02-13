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

    //test
//    private LiveData<List<Note>> searchTest;
//
//    // searching members
//    private MutableLiveData<List<Note>> allSearchNote = new MutableLiveData<>();
//
//    private void finishAsync(List<Note> list) {
//        allSearchNote.setValue(list);
//    }

//    private String name;
    public Repository(Application application) {
        DatabaseRoom database = DatabaseRoom.getInstance(application);
        dao = database.dao();
        allNotes = dao.getAllNotes();
        allToDoList = dao.getAllToDoList();
        allSelectedStores = dao.getAllSelectedStores();
        allCountries = dao.getAllCountries();
        allUnSelectedStores = dao.getAllUnSelectedStores();

//        //test
        //searchTest = dao.searchTest(name);

    }

    public List<Note> noteTest(String name) throws ExecutionException, InterruptedException {
        SearchNoteAsyncTask test = new SearchNoteAsyncTask(dao);
        test.execute(name);
//        allSearchNote.setValue(test.test);
        return test.get();
        //searchTest = dao.searchTest(name);
    }
    private static class SearchNoteAsyncTask extends AsyncTask<String,Void,List<Note>>{
        private Dao dao;
        private SearchNoteAsyncTask(Dao dao){
            this.dao =dao;
//            this.name = name;
        }
        @Override
        protected List<Note> doInBackground(String... strings) {
            List<Note> list = dao.searchNote(strings[0]);
            return list.size() > 0? list: new ArrayList<Note>();

        }
    }

//    public LiveData<List<Note>> getSearchTest(){return searchTest;}


    public void insertNotes(Note note) {
        new InsertNotesAsyncTask(dao).execute(note);
    }

    public void insertToDoList(ToDoList toDoList) {
        new InsertToDoListAsyncTask(dao).execute(toDoList);
    }

    public void updateToDoList(ToDoList toDoList) {
        new UpdateToDoListAsyncTask(dao).execute(toDoList);
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

    public void deleteAllNotes() {
        new DeleteAllNotesAsyncTask(dao).execute();
    }

    public void deleteAllNotesId(long id) {
        new DeleteAllNotesIdAsyncTask(dao, id).execute();
    }

    // searching for data
//    public void searchNote(String name) {
//        GetNoteAsyncTask task = new GetNoteAsyncTask(dao);
//        task.repository = this;
//        task.execute(name);
//    }

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

//    public MutableLiveData<List<Note>> getAllSearchNote() {
//        return allSearchNote;
//    }

//     Threads for data executions
//    private static class GetNoteAsyncTask extends AsyncTask<String, Void, List<Note>> {
//
//        private Dao dao;
//        private Repository repository = null;
//
//        private GetNoteAsyncTask(Dao dao) {
//            this.dao = dao;
//        }
//
//        @Override
//        protected List<Note> doInBackground(String... strings) {
//            return dao.getNote(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(List<Note> notes) {
//            super.onPostExecute(notes);
//            repository.finishAsync(notes);
//        }
//    }

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

}
