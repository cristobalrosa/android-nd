package org.crosa.android.popularmovies.services;

import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;

import java.util.List;

public interface IMoviesService {
    /**
     * Fetches a list of popular movies.
     * @return List of MovieSummary
     */
    List<MovieSummary> getMostPopularMovies();
    /**
     * Fetches a list of top rated movies.
     * @return List of MovieSummary
     */
    List<MovieSummary> getTopRatedMovies();
    /**
     * Fetches a list of movies given a criteria.
     * @param searchCriteria  Search Criteria
     * @return List of MovieSummary
     */
    List<MovieSummary> getMovies(MovieSearchCriteria searchCriteria);
}
