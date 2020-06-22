package org.crosa.android.popularmovies.client.retrofit;


import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.responses.DiscoveryResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IMovieDatabaseAPI {
    /**
     * Retrieves a list of popular movies.
     *
     * @param page page number (starts with 1)
     * @return List of Movie.
     */
    @GET("discover/movie")
    Call<DiscoveryResponse> getPopularMovies(@Query("page") int page, @Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<DiscoveryResponse> getTopRated(@Query("page") int page, @Query("api_key") String api_key);

    @GET("movie/{movie_id}")
    Call<MovieDetails> getMovieDetail(@Path("movie_id") int movieId, @Query("api_key") String api_key);
}
