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

    @Update
    void update(MovieEntity movie);

    @Insert
    void insert(MovieEntity movie);

    @Query("Delete from movies where id = :movieId")
    void deleteMovieById(int movieId);

    @Query("SELECT * FROM movies WHERE id = :id")
    MovieEntity loadMovieById(int id);
}
