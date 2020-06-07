package org.crosa.android.popularmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.impl.MoviesServiceImpl;

public class MainActivity extends AppCompatActivity {
    private IMoviesDatabaseClient mMoviesDatabaseClient;
    private IMoviesService mMoviesService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesDatabaseClient = new TheMovieDBClientImpl(getString(R.string.moviedb_secret));
        mMoviesService = new MoviesServiceImpl(mMoviesDatabaseClient);
    }
}