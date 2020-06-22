package org.crosa.android.popularmovies.services.impl;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;

import java.util.ArrayList;
import java.util.List;

public class MoviesServiceImpl implements IMoviesService {
    private final IMoviesDatabaseClient client;

    public MoviesServiceImpl(IMoviesDatabaseClient client) {
        this.client = client;
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
}
