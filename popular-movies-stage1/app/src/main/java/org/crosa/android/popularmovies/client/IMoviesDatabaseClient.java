package org.crosa.android.popularmovies.client;

import org.crosa.android.popularmovies.model.Movie;

import java.util.List;

public interface IMoviesDatabaseClient {
    /**
     * Retrieves a list of popular movies.
     * @return List of Movie.
     */
    List<Movie> getPopularMovies();
}
