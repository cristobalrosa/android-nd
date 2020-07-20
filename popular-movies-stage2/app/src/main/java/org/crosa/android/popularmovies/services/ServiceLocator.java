package org.crosa.android.popularmovies.services;

import android.content.Context;
import android.content.res.Resources;

import org.crosa.android.popularmovies.R;
import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.retrofit.impl.TheMovieDBRetrofitSyncClient;
import org.crosa.android.popularmovies.database.MoviesLocalDB;
import org.crosa.android.popularmovies.services.impl.MoviesServiceImpl;

/**
 * Trying to work around dependency injections for the services and the database clients.
 * We could replace this using hilt.
 * https://developer.android.com/training/dependency-injection
 */
public class ServiceLocator {

    private static ServiceLocator instance = null;
    private static IMoviesDatabaseClient databaseClient;
    private static IMoviesService moviesService;

    private final Context context;

    private ServiceLocator(Context context) {
        this.context = context;
    }

    public static ServiceLocator getInstance(Context context) {
        if (instance == null) {
            synchronized (ServiceLocator.class) {
                instance = new ServiceLocator(context);
                // Create all the instances at the same time go make them singleton too.
                databaseClient = new TheMovieDBRetrofitSyncClient(context.getResources().getString(R.string.moviedb_secret));
                moviesService = new MoviesServiceImpl(databaseClient, MoviesLocalDB.getInstance(context));
            }
        }
        return instance;
    }

    public IMoviesService getMoviesService() {
        return moviesService;
    }

}
