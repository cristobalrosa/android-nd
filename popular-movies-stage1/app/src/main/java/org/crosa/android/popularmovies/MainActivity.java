package org.crosa.android.popularmovies;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.crosa.android.popularmovies.adapters.MoviesAdapter;
import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.impl.MoviesServiceImpl;
import org.crosa.android.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {
    private IMoviesDatabaseClient mMoviesDatabaseClient;
    private IMoviesService mMoviesService;

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesDatabaseClient = new TheMovieDBClientImpl(getString(R.string.moviedb_secret));
        mMoviesService = new MoviesServiceImpl(mMoviesDatabaseClient);
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mRecyclerView = findViewById(R.id.movies_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        loadMovies();
    }

    private void loadMovies() {
        showMovieDataView();
        new MoviesTask(mMoviesService).execute();
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(MovieSummary movieSummary) {
        Context context = this;
        Toast.makeText(context, movieSummary.getTitle(), Toast.LENGTH_SHORT)
                .show();
    }

    public class MoviesTask extends AsyncTask<Void, Void, List<MovieSummary>> {
        private final String TAG = MoviesTask.class.getSimpleName();
        private final IMoviesService moviesService;

        public MoviesTask(IMoviesService moviesService) {
            this.moviesService = moviesService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<MovieSummary> doInBackground(Void... voids) {
            if (!NetworkUtils.isOnline()) {
                Log.e(TAG, "No internet connection");
                return null;
            }
            return moviesService.getMostPopularMovies();
        }

        @Override
        protected void onPostExecute(List<MovieSummary> summaries) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (summaries != null) {
                showMovieDataView();
                mMoviesAdapter.setMovieData(summaries);
            } else {
                showErrorMessage();
            }
        }
    }
}