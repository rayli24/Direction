package com.techme.direction;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.renderscript.Allocation;

import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.techme.direction.ConvertImage.convertImageToByte;

@Database(entities = {Country.class,CreateNote.class,Note.class,Store.class},version = 1)
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

    public static class DirectionAsyncTask extends AsyncTask<Void,Void,Void>{

        private Dao dao;

        private DirectionAsyncTask(DatabaseRoom db) // the database class
        {
            dao = db.dao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        private void country()
        {
            dao.insertCountry(new Country("Canada"));
            dao.insertCountry(new Country("USA"));
        }

        private void store() throws IOException {

            // Canada

            Bitmap bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.sobey);
            dao.insertStore(new Store("Sobey's",12,convertImageToByte(bitmap),
                    "grocery", "Canada", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
            dao.insertStore(new Store("Walmart",9,convertImageToByte(bitmap),
                       "grocery", "Canada", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizzahut);
            dao.insertStore(new Store("Pizza Hut",17,convertImageToByte(bitmap),
                    "dinning", "Canada", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
            dao.insertStore(new Store("McDonald's",23,convertImageToByte(bitmap),
                    "dinning", "Canada", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
            dao.insertStore(new Store("K.F.C",11,convertImageToByte(bitmap),
                    "dinning", "Canada", false));


            // USA

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.walmart);
            dao.insertStore(new Store("Walmart",19,convertImageToByte(bitmap),
                       "grocery", "USA", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.pizzahut);
            dao.insertStore(new Store("Pizza Hut",8,convertImageToByte(bitmap),
                    "dinning", "USA", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.mcdonald);
            dao.insertStore(new Store("McDonald's",13,convertImageToByte(bitmap),
                    "dinning", "USA", false));

            bitmap = BitmapFactory.decodeResource(ContextHelper.Helper.getResource(), R.drawable.kfc);
            dao.insertStore(new Store("K.F.C",12,convertImageToByte(bitmap),
                    "dinning", "USA", false));



        }

    }

}
