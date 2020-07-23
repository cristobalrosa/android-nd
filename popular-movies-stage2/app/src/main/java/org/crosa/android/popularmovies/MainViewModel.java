package org.crosa.android.popularmovies;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.ServiceLocator;
import org.w3c.dom.Entity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    // Constant for logging
    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<MovieEntity>> favoriteMovies;

    public LiveData<List<MovieEntity>> getFavoriteMovies() {
        return favoriteMovies;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        IMoviesService moviesService = ServiceLocator.getInstance(application.getApplicationContext()).getMoviesService();
        Log.d(TAG, "Actively retrieving the favorite movies  from the DataBase");
        favoriteMovies = moviesService.getAllFavoriteMovies();
    }

}
