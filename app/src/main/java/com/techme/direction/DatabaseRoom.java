package com.techme.direction;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.techme.direction.helper.ContextHelper;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.techme.direction.helper.ConvertImage.convertImageToByte;

@Database(entities = {Country.class,CreateNote.class,Note.class,Store.class},version = 3)
public abstract class DatabaseRoom extends RoomDatabase {

    private static DatabaseRoom instance;

    public abstract Dao dao();

    public static synchronized DatabaseRoom getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseRoom.class,"direction_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCall)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCall = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new DirectionAsyncTask(instance).execute();
        }
    };

    // test to fill new elements for now
    private static RoomDatabase.Callback roomOpen = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DirectionAsyncTask(instance).execute();
        }
    };


    private static RoomDatabase.Callback roomDestroy = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new DeleteAllAsyncTask(instance).execute();
        }
    };

    public static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>
    {
        private Dao dao;

        private DeleteAllAsyncTask(DatabaseRoom db){this.dao = db.dao();}

        @Override
        protected Void doInBackground(Void... voids) {
            dao.deleteAllStore();
            return null;
        }
    }

    public static class DirectionAsyncTask extends AsyncTask<Void,Void,Void>{

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

        public void defaultNote()
        {
            dao.insertNote(new Note("Daily note",0));
        }

        public void country()
        {
            dao.insertCountry(new Country("Canada"));
            dao.insertCountry(new Country("USA"));
        }

        public void store() throws IOException {
            final String CANADA = "Canada";
            final String USA = "USA";
            final String GROCERY = "grocery";
            final String DINING = "dining";
            // Canada

            Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.sobey);
            dao.insertStore(new Store("Sobey's",12,convertImageToByte(bitmap),
                    GROCERY, CANADA, 0));

            Bitmap a = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
            dao.insertStore(new Store("Walmart",9,convertImageToByte(a),
                       GROCERY, CANADA, 0));

            Bitmap b = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizzahut);
            dao.insertStore(new Store("Pizza Hut",17,convertImageToByte(b),
                    DINING, CANADA, 0));

            Bitmap c = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
            dao.insertStore(new Store("McDonald's",23,convertImageToByte(c),
                    DINING, CANADA, 0));

            Bitmap d = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
            dao.insertStore(new Store("K.F.C",11,convertImageToByte(d),
                    DINING, CANADA, 0));


            // USA

            Bitmap f = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
            dao.insertStore(new Store("Walmart",19,convertImageToByte(f),
                       GROCERY, USA, 0));

            Bitmap g = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizzahut);
            dao.insertStore(new Store("Pizza Hut",8,convertImageToByte(g),
                    DINING, USA, 0));

            Bitmap h = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
            dao.insertStore(new Store("McDonald's",13,convertImageToByte(h),
                    DINING, USA, 0));

            Bitmap i = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
            dao.insertStore(new Store("K.F.C",12,convertImageToByte(i),
                    DINING, USA, 0));



        }

    }

}
