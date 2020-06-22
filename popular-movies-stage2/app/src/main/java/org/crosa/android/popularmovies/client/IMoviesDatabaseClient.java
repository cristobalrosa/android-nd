package org.crosa.android.popularmovies.client;

import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieSummary;

import java.util.List;

public interface IMoviesDatabaseClient {
    /**
     * Retrieves a list of popular movies.
     *
     * @param page page number (starts with 1)
     * @return List of Movie.
     */
    List<MovieSummary> getPopularMovies(int page);

    /**
     * Retrieve the top rated movies.
     *
     * @param page page number (starts with 1)
     * @return List of Movie
     */
    List<MovieSummary> getTopRated(int page);

    /**
     * Fetch movie details
     * @param movieId Movie Id
     * @return Movie
     */
    MovieDetails getMovieDetails(int movieId);
}
