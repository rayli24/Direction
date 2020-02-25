package com.techme.direction;

import android.content.Context;
import android.os.AsyncTask;

import com.techme.direction.countries.CanadaStores;
import com.techme.direction.countries.UnitedStatesStores;


import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.techme.direction.helper.VariablesHelper.CANADA;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.USA;

@Database(entities = {Country.class, ToDoList.class, Note.class, Store.class}, version = 1)
public abstract class DatabaseRoom extends RoomDatabase {

    private static DatabaseRoom instance;

    public abstract Dao dao();

    public static synchronized DatabaseRoom getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseRoom.class, "direction_database")
                    .addCallback(roomCall)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCall = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DirectionAsyncTask(instance).execute();
        }
    };

    // test to fill new elements for now
    private static RoomDatabase.Callback roomOpen = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DirectionAsyncTask(instance).execute();
        }
    };


    private static RoomDatabase.Callback roomDestroy = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DeleteAllAsyncTask(instance).execute();
        }
    };

    public static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {
        private Dao dao;

        private DeleteAllAsyncTask(DatabaseRoom db) {
            this.dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStore();
            dao.deleteAllNotes();
            dao.deleteAllToDoList();
            dao.deleteAllCountry();
            return null;
        }
    }

    public static class DirectionAsyncTask extends AsyncTask<Void, Void, Void> {

        private Dao dao;

        private DirectionAsyncTask(DatabaseRoom db) // the database class
        {
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            country();
            defaultNote();
            try {
                store();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        public void defaultNote() {

            dao.insertNote(new Note("Daily", FALSE));
        }

        public void country() {
            dao.insertCountry(new Country(CANADA, FALSE));
            dao.insertCountry(new Country(USA, FALSE));
        }

        public void store() throws IOException {
            // Canada
            CanadaStores.stores(dao);

            // USA
            UnitedStatesStores.Stores(dao);

        }

    }

}
