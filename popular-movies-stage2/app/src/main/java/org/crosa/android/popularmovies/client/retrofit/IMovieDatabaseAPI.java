package org.crosa.android.popularmovies.client.retrofit;


import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.responses.DiscoveryResponse;
import org.crosa.android.popularmovies.model.responses.MovieReviewsResponse;
import org.crosa.android.popularmovies.model.responses.MovieVideosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieDatabaseAPI {

    @GET("discover/movie")
    Call<DiscoveryResponse> getPopularMovies(@Query("page") int page, @Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<DiscoveryResponse> getTopRated(@Query("page") int page, @Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path("movie_id") int movieId, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/videos")
    Call<MovieVideosResponse> getMovieVideos(@Path("movie_id") int movieId, @Query("api_key") String api_key);
}
