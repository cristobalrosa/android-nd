package org.crosa.android.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.crosa.android.popularmovies.adapters.MoviesReviewAdapter;
import org.crosa.android.popularmovies.adapters.MoviesVideoAdapter;
import org.crosa.android.popularmovies.model.MovieReview;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.MovieVideo;
import org.crosa.android.popularmovies.model.PosterSize;
import org.crosa.android.popularmovies.services.IMoviesService;
import org.crosa.android.popularmovies.services.ServiceLocator;
import org.crosa.android.popularmovies.utils.NetworkUtils;

import java.util.List;
import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity implements MoviesVideoAdapter.MoviesVideoAdapterOnClickHandler {
    private MoviesVideoAdapter moviesVideoAdapter;
    private MoviesReviewAdapter moviesReviewAdapter;

    private RecyclerView mVideosRV;
    private RecyclerView mReviewsRV;
    private IMoviesService mMoviesService;
    private RecyclerView.LayoutManager videosLayoutManager;
    private RecyclerView.LayoutManager reviewsLayoutManager;
    private final static String YOUTUBE_BASE_URL = "http://www.youtube.com/watch?v=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView mMovieTitle = findViewById(R.id.tv_detail_movie_title);
        ImageView mMoviePoster = findViewById(R.id.iv_detail_movie_poster);
        TextView mReleaseDate = findViewById(R.id.tv_details_release_date);
        TextView mMovieOverview = findViewById(R.id.tv_detail_overview);
        TextView mRating = findViewById(R.id.tv_movie_rating);

        // Videos Recycler View set up.
        mVideosRV = findViewById(R.id.recyclerview_trailers);
        videosLayoutManager = new LinearLayoutManager(this);
        mVideosRV.setLayoutManager(videosLayoutManager);

        moviesVideoAdapter = new MoviesVideoAdapter(this);
        mVideosRV.setAdapter(moviesVideoAdapter);
        mVideosRV.setHasFixedSize(true);

        mMoviesService = ServiceLocator.getInstance(getApplicationContext()).getMoviesService();

        // Reviews Recycler View set up.
        mReviewsRV = findViewById(R.id.recyclerview_reviews);
        reviewsLayoutManager = new LinearLayoutManager(this);
        mReviewsRV.setLayoutManager(reviewsLayoutManager);

        moviesReviewAdapter = new MoviesReviewAdapter();
        mReviewsRV.setAdapter(moviesReviewAdapter);
        mReviewsRV.setHasFixedSize(true);



        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movieDetail")) {
                MovieSummary mMovieDetails = (MovieSummary) intentThatStartedThisActivity.getSerializableExtra("movieDetail");
                mMovieTitle.setText(mMovieDetails.getTitle());
                mReleaseDate.setText(mMovieDetails.getReleaseDate());
                mMovieOverview.setText(mMovieDetails.getOverview());
                mRating.setText(String.format(Locale.getDefault(), "%.2f/10", mMovieDetails.getVoteAverage()));
                Picasso.get().load(mMovieDetails.getRealPosterPath(PosterSize.W_185)).into(mMoviePoster);
                loadMovieVideos(mMovieDetails.getId());
                loadMovieReviews(mMovieDetails.getId());
            }
        }

    }

    private void loadMovieReviews(int movieId) {
        new MoviesReviewsTask(mMoviesService, movieId).execute();
    }

    private void loadMovieVideos(int movieId) {
        new MoviesVideosTask(mMoviesService, movieId).execute();
    }

    public class MoviesVideosTask extends AsyncTask<Void, Void, List<MovieVideo>> {
        private final String TAG = MainActivity.MoviesTask.class.getSimpleName();
        private final IMoviesService moviesService;
        private final int movieId;

        public MoviesVideosTask(IMoviesService moviesService, int movieId) {
            this.moviesService = moviesService;
            this.movieId = movieId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MovieVideo> doInBackground(Void... voids) {
            if (!NetworkUtils.isOnline()) {
                Log.e(TAG, "No internet connection");
                return null;
            }
            return moviesService.getMovieVideos(movieId);
        }

        @Override
        protected void onPostExecute(List<MovieVideo> videos) {
            if (videos != null) {
                moviesVideoAdapter.setMovieVideos(videos);
            }
        }
    }

    public class MoviesReviewsTask extends AsyncTask<Void, Void, List<MovieReview>> {
        private final String TAG = MainActivity.MoviesTask.class.getSimpleName();
        private final IMoviesService moviesService;
        private final int movieId;

        public MoviesReviewsTask(IMoviesService moviesService, int movieId) {
            this.moviesService = moviesService;
            this.movieId = movieId;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<MovieReview> doInBackground(Void... voids) {
            if (!NetworkUtils.isOnline()) {
                Log.e(TAG, "No internet connection");
                return null;
            }
            return moviesService.getMovieReviews(movieId);
        }

        @Override
        protected void onPostExecute(List<MovieReview> reviews) {
            if (reviews != null) {
                moviesReviewAdapter.setReviews(reviews);
            }
        }
    }

    @Override
    public void onClick(MovieVideo movie) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(YOUTUBE_BASE_URL + movie.getKey()));
        startActivity(browserIntent);
    }
}