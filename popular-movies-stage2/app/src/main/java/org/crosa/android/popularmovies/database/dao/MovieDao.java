package org.crosa.android.popularmovies.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.crosa.android.popularmovies.database.entities.MovieEntity;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movies ORDER BY id")
    LiveData<List<MovieEntity>> loadAllFavouriteMovies();

    @Insert
    void insertMovie(MovieEntity movie);

    @Delete
    void deleteMovie(MovieEntity movie);

    @Query("SELECT * FROM movies WHERE id = :id")
    LiveData<MovieEntity> loadTaskById(int id);
}
