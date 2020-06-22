package org.crosa.android.popularmovies;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.crosa.android.popularmovies.adapters.MoviesAdapter;
import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.impl.MoviesServiceImpl;
import org.crosa.android.popularmovies.utils.NetworkUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {
    private IMoviesService mMoviesService;

    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MoviesAdapter mMoviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        IMoviesDatabaseClient mMoviesDatabaseClient = new TheMovieDBClientImpl(getString(R.string.moviedb_secret));
        mMoviesService = new MoviesServiceImpl(mMoviesDatabaseClient);
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        mRecyclerView = findViewById(R.id.movies_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        loadMovies(MovieSearchCriteria.MOST_POPULAR);
    }

    private void loadMovies(MovieSearchCriteria movieSearchCriteria) {
        showMovieDataView();
        new MoviesTask(mMoviesService, movieSearchCriteria).execute();
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
        Intent intentToStartDetailActivity = new Intent(context,  MovieDetailActivity.class);
        intentToStartDetailActivity.putExtra("movieDetail", movieSummary);
        startActivity(intentToStartDetailActivity);
    }

    public class MoviesTask extends AsyncTask<Void, Void, List<MovieSummary>> {
        private final String TAG = MoviesTask.class.getSimpleName();
        private final IMoviesService moviesService;
        private final MovieSearchCriteria searchCriteria;

        public MoviesTask(IMoviesService moviesService, MovieSearchCriteria searchCriteria) {
            this.moviesService = moviesService;
            this.searchCriteria = searchCriteria;
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
            return moviesService.getMovies(searchCriteria);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_most_popular) {
            loadMovies(MovieSearchCriteria.MOST_POPULAR);
            return true;
        }
        if (id == R.id.action_top_rated) {
            loadMovies(MovieSearchCriteria.TOP_RATED);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}