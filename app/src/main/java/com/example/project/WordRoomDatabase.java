package com.example.project;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Word.class}, version = 1)
public abstract class WordRoomDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
    private static volatile WordRoomDatabase INSTANCE;
    static WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            synchronized (WordRoomDatabase.class){
                if (INSTANCE==null)
                {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class,"word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();//craete
                }
            }
        }
        return INSTANCE;
    }
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        PopulateDbAsync(WordRoomDatabase db) {

            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();
            Word word = new Word("Wax Museum");
            mDao.insert(word);
            word = new Word("Chamundi Hills");
            mDao.insert(word);
//            Word image =new Word("@drawable/ic_add_black_24dp");
//            mDao.insert(image);
            word = new Word("Mysuru Palace");
            mDao.insert(word);
            word = new Word("st.philomena church");
            mDao.insert(word);
            word = new Word("Lalith mahal");
            mDao.insert(word);
            word = new Word("Jagamohan palace");
            mDao.insert(word);
            return null;
        }
    }
}

