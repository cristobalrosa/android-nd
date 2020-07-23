package org.crosa.android.popularmovies.services;

import android.arch.lifecycle.LiveData;

import org.crosa.android.popularmovies.database.entities.MovieEntity;
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

    /**
     * Sets a given movie as favorite
     * @param movieSummary movie summary
     */
    void favoriteMovie(MovieSummary movieSummary);
    /**
     * Removes the given movie from the favorites database.
     * @param movieId Movie Id
     */
    void unFavoriteMovie(int movieId);

    /**
     * Checks whether a movie is favorite or not.
     * @param movieId The movie id.
     * @return True or False
     */
    boolean isFavorite(int movieId);

    /**
     * Return a livedata list of favorite movies.
     * @return LiveData
     */
    LiveData<List<MovieEntity>> getAllFavoriteMovies();
}
