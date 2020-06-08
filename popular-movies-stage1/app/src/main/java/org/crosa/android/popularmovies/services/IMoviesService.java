package org.crosa.android.popularmovies.services;

import org.crosa.android.popularmovies.model.MovieSummary;

import java.util.List;

public interface IMoviesService {
    List<MovieSummary> getMostPopularMovies();
}
