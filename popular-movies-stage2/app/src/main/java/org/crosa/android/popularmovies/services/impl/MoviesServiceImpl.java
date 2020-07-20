package org.crosa.android.popularmovies.services.impl;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.database.MoviesLocalDB;
import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.crosa.android.popularmovies.model.MovieReview;
import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.MovieVideo;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.utils.AppExecutors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MoviesServiceImpl implements IMoviesService {
    private final IMoviesDatabaseClient client;
    private final MoviesLocalDB moviesLocalDB;

    public MoviesServiceImpl(IMoviesDatabaseClient client, MoviesLocalDB localDB) {
        this.client = client;
        this.moviesLocalDB = localDB;
    }

    @Override
    public List<MovieSummary> getMostPopularMovies() {
        return client.getPopularMovies(1);
    }

    @Override
    public List<MovieSummary> getTopRatedMovies() {
        return client.getTopRated(1);
    }

    @SuppressWarnings("unused")
    @Override
    public List<MovieSummary> getMovies(MovieSearchCriteria searchCriteria) {
        switch (searchCriteria) {
            case TOP_RATED:
                return getTopRatedMovies();
            case MOST_POPULAR:
                return getMostPopularMovies();
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public List<MovieVideo> getMovieVideos(int movieId) {
        return client.getMovieVideos(movieId);
    }

    @Override
    public List<MovieReview> getMovieReviews(int movieId) {
        return client.getMovieReviews(movieId);
    }

    @Override
    public void favoriteMovie(final int movieId, final String originalTitle) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                MovieEntity movieEntity = new MovieEntity(movieId, originalTitle);
                if (moviesLocalDB.movieDao().loadMovieById(movieId) != null) {
                    moviesLocalDB.movieDao().update(movieEntity);
                } else {
                    moviesLocalDB.movieDao().insert(movieEntity);
                }
            }
        });
    }

    @Override
    public void unFavoriteMovie(final int movieId) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                moviesLocalDB.movieDao().deleteMovieById(movieId);
            }
        });
    }

    @Override
    public boolean isFavorite(final int movieId) {
        final AtomicBoolean favorite = new AtomicBoolean(false);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                favorite.getAndSet(moviesLocalDB.movieDao().loadMovieById(movieId) != null);
            }
        });
        return favorite.get();
    }

    @Override
    public LiveData<List<MovieEntity>> getAllFavoriteMovies() {
        return moviesLocalDB.movieDao().loadAllFavouriteMovies();
    }

}
