package org.crosa.android.popularmovies.services;

import org.crosa.android.popularmovies.model.MovieReview;
import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.MovieVideo;

import java.util.List;

@SuppressWarnings("unused")
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

    /**
     * Fetch a list of movie's videos
     *
     * @param movieId Movie Id
     * @return List of videos
     */
    List<MovieVideo> getMovieVideos(int movieId);

    /**
     * Fetch a list of movie's reviews
     *
     * @param movieId Movie Id
     * @return List of videos
     */
    List<MovieReview> getMovieReviews(int movieId);
}
