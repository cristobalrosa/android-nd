package org.crosa.android.popularmovies.services.impl;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;

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
}
