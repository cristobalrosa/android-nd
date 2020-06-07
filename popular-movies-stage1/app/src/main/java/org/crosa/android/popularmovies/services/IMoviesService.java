package org.crosa.android.popularmovies.services;

import org.crosa.android.popularmovies.model.Movie;

import java.util.List;

public interface IMoviesService {
    List<Movie> getMostPopularMovies();
}
