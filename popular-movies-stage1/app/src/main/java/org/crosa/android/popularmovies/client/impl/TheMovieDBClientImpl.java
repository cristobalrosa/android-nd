package org.crosa.android.popularmovies.client.impl;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;

public class TheMovieDBClientImpl implements IMoviesDatabaseClient {
    private final String BASE_URL ="https://api.themoviedb.org/3/";
    private final String IMAGE_API_BASE_URL ="http://image.tmdb.org/t/p/";
    private final String apiKey;

    public TheMovieDBClientImpl(String apiKey){
        this.apiKey = apiKey;
    }
}
