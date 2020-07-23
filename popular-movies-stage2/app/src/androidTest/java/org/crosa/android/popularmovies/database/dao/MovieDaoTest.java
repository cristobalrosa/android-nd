package org.crosa.android.popularmovies.database.dao;

import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class MovieDaoTest extends AbstractDBTest {

    @Test
    public void testInsert() throws InterruptedException {
        // Arrange
        MovieEntity entity = new MovieEntity(1, "original title 1", "original title", "2017-05-06", "overview", 2, "path");

        // Act
        movieDao.insert(entity);
        // Assert
        List<MovieEntity> movies = getOrAwaitValue(movieDao.loadAllFavouriteMovies());
        assertNotNull(movies);
        assertEquals(1, movies.size());
    }

    @Test
    public void testAndDelete() throws InterruptedException {
        // Arrange
        List<Integer> movieIds = new ArrayList<>();
        movieIds.add(1);
        movieIds.add(2);
        Map<Integer, MovieEntity> originalList = preloadDb(movieIds);

        // Act
        movieDao.deleteMovieById(originalList.get(1).getId());

        // Assert
        List<MovieEntity> movies = getOrAwaitValue(movieDao.loadAllFavouriteMovies());
        assertNotNull(movies);
        assertEquals(1, movies.size());

        //
    }

    private Map<Integer, MovieEntity> preloadDb(List<Integer> movieIds) throws InterruptedException {

        Map<Integer, MovieEntity> movies = new HashMap<>();
        for (Integer movieId : movieIds) {
            MovieEntity entity = new MovieEntity(movieId, "original title 1", "original title", "2017-05-06", "overview", 2, "path");
            movies.put(movieId, entity);
            movieDao.insert(entity);
        }

        List<MovieEntity> moviesFromDB = getOrAwaitValue(movieDao.loadAllFavouriteMovies());
        assertNotNull(moviesFromDB);
        assertEquals(movieIds.size(), moviesFromDB.size());

        return movies;
    }


}
