package org.crosa.android.popularmovies.client.retrofit.impl;

import android.util.Log;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.retrofit.IMovieDatabaseAPI;
import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieReview;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.MovieVideo;
import org.crosa.android.popularmovies.model.responses.DiscoveryResponse;
import org.crosa.android.popularmovies.model.responses.MovieReviewsResponse;
import org.crosa.android.popularmovies.model.responses.MovieVideosResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDBRetrofitSyncClient implements IMoviesDatabaseClient {
    private IMovieDatabaseAPI service;
    private static final String TAG = TheMovieDBRetrofitSyncClient.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String DISCOVER_PATH = "discover/movie";
    private static final String TOP_RATED_PATH = "movie/top_rated";
    private static final String MOVIE_DETAILS_PATH = "movie/";
    private static final String API_KEY_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";
    private static final String LANGUAGE_BY_PARAM = "language";
    private static final String PAGE_PARAM = "page";
    private static final String DEFAULT_LANGUAGE = "en-US";
    private final String apiKey;

    public TheMovieDBRetrofitSyncClient(String apiKey) {
        this.apiKey = apiKey;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(IMovieDatabaseAPI.class);
    }


    @Override
    public List<MovieSummary> getPopularMovies(int page) {
        return getMovies(page, service.getPopularMovies(page, this.apiKey));
    }

    @Override
    public List<MovieSummary> getTopRated(int page) {
        return getMovies(page, service.getTopRated(page, this.apiKey));
    }

    @Override
    public MovieDetails getMovieDetails(int movieId) {
        Call<MovieDetails> call = service.getMovieDetail(movieId, this.apiKey);
        try {
            Response<MovieDetails> response = call.execute();
            if (response.isSuccessful()) {
                return response.body();
            } else {
                Log.e(TAG, response.errorBody().string());
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to parse movie details", e);
        }
        return null;
    }

    @Override
    public List<MovieReview> getMovieReviews(int movieId) {
        Call<MovieReviewsResponse> call = service.getMovieReviews(movieId, this.apiKey);
        try {
            Response<MovieReviewsResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getResults();
            } else {
                Log.e(TAG, response.errorBody().string());
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to parse movie details", e);
        }
        return new ArrayList<>();
    }

    @Override
    public List<MovieVideo> getMovieVideos(int movieId) {
        Call<MovieVideosResponse> call = service.getMovieVideos(movieId, this.apiKey);
        try {
            Response<MovieVideosResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getResults();
            } else {
                Log.e(TAG, response.errorBody().string());
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to parse movie details", e);
        }
        return new ArrayList<>();
    }

    private List<MovieSummary> getMovies(int page, Call<DiscoveryResponse> call) {
        try {
            Response<DiscoveryResponse> response = call.execute();
            if (response.isSuccessful()) {
                return response.body().getResults();
            } else {
                Log.e(TAG, response.errorBody().string());
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to parse movie details", e);
        }
        return new ArrayList<>();
    }
}
