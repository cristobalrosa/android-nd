package org.crosa.android.popularmovies;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.crosa.android.popularmovies.adapters.MoviesAdapter;
import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.crosa.android.popularmovies.model.MovieSearchCriteria;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.ServiceLocator;
import org.crosa.android.popularmovies.utils.MoviesSummaryToMoviesEntityMapper;
import org.crosa.android.popularmovies.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {
    private IMoviesService mMoviesService;
    private static final String TAG = "MainActivity";
    public static final String EXTRA_SEARCH_CRITERIA = "searchCriteria";
    private RecyclerView mRecyclerView;
    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;
    private MoviesAdapter mMoviesAdapter;

    private List<MovieSummary> favoriteMovies;
    private MovieSearchCriteria searchCriteria = MovieSearchCriteria.MOST_POPULAR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesService = ServiceLocator.getInstance(getApplicationContext()).getMoviesService();
        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display);

        mRecyclerView = findViewById(R.id.movies_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), getNumberOfColumns());
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);
        mRecyclerView.setAdapter(mMoviesAdapter);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);

        if (savedInstanceState != null && savedInstanceState.containsKey(EXTRA_SEARCH_CRITERIA)) {
            searchCriteria = (MovieSearchCriteria) savedInstanceState.getSerializable(EXTRA_SEARCH_CRITERIA);
        }
        setupViewModel();
    }

    private void setupViewModel() {
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getFavoriteMovies().observe(this, new Observer<List<MovieEntity>>() {
            @Override
            public void onChanged(@Nullable List<MovieEntity> movieEntities) {
                Log.d(TAG, "List of movies changed.. Loading");
                // This is not very efficient but it will do it.
                favoriteMovies = new ArrayList<>();
                for (MovieEntity movieEntity : movieEntities) {
                    favoriteMovies.add(MoviesSummaryToMoviesEntityMapper.from(movieEntity));
                }
                loadMovies();
            }
        });
    }

    /**
     * Retrieve the number of columns to display.
     * <p>
     * NOTE: Suggestion in stage 1 review.
     *
     * @return The number of columns to display
     */
    private int getNumberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if (nColumns < 2) return 2; //to keep the grid aspect
        return nColumns;
    }

    private void loadMovies() {
        showMovieDataView();
        if (searchCriteria == MovieSearchCriteria.FAVORITES) {
            mMoviesAdapter.setMovieData(favoriteMovies);
        } else {
            new MoviesTask(mMoviesService, searchCriteria).execute();
        }
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
        Intent intentToStartDetailActivity = new Intent(context, MovieDetailActivity.class);
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
            searchCriteria = MovieSearchCriteria.MOST_POPULAR;
        }
        if (id == R.id.action_top_rated) {
            searchCriteria = MovieSearchCriteria.TOP_RATED;
        }
        if (id == R.id.action_favorites) {
            searchCriteria = MovieSearchCriteria.FAVORITES;
        }
        loadMovies();
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save the search criteria in a bundle so we can reuse it
        // when the screen is rotated.
        outState.putSerializable(EXTRA_SEARCH_CRITERIA, searchCriteria);
        super.onSaveInstanceState(outState);
    }
}