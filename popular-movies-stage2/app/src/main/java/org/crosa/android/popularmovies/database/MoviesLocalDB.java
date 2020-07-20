package org.crosa.android.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.util.Log;

import org.crosa.android.popularmovies.database.dao.MovieDao;
import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.crosa.android.popularmovies.database.typeconverters.DateConverter;

@Database(entities = {MovieEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class MoviesLocalDB extends RoomDatabase {

    private static final String LOG_TAG = MoviesLocalDB.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "moviedb";
    private static MoviesLocalDB sInstance;

    public static MoviesLocalDB getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MoviesLocalDB.class, MoviesLocalDB.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();

}