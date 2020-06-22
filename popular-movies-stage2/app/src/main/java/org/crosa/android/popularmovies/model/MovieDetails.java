package org.crosa.android.popularmovies.model;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * POJO class to store a movie data
 * Schema from https://developers.themoviedb.org/3/movies/get-movie-details
 */
@Value
@AllArgsConstructor
public class MovieDetails implements Serializable {
    private static final String TAG = MovieDetails.class.getSimpleName();

    // class attributes. Since I'm using lombok annotation @Value there is no need for private final.
    @SerializedName("adult")
    @Expose
    Boolean adult;
    @SerializedName("backdrop_path")
    @Expose
    String backdropPath;
    @SerializedName("belongs_to_collection")
    @Expose
    Object belongsToCollection;
    @SerializedName("budget")
    @Expose
    Integer budget;
    @SerializedName("genres")
    @Expose
    List<Genre> genres = null;
    @SerializedName("homepage")
    @Expose
    String homepage;
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("imdb_id")
    @Expose
    String imdbId;
    @SerializedName("original_language")
    @Expose
    String originalLanguage;
    @SerializedName("original_title")
    @Expose
    String originalTitle;
    @SerializedName("overview")
    @Expose
    String overview;
    @SerializedName("popularity")
    @Expose
    Double popularity;
    @SerializedName("poster_path")
    @Expose
    Object posterPath;
    @SerializedName("production_companies")
    @Expose
    List<ProductionCompany> productionCompanies = null;
    @SerializedName("production_countries")
    @Expose
    List<ProductionCountry> productionCountries = null;
    @SerializedName("release_date")
    @Expose
    String releaseDate;
    @SerializedName("revenue")
    @Expose
    Integer revenue;
    @SerializedName("runtime")
    @Expose
    Integer runtime;
    @SerializedName("spoken_languages")
    @Expose
    List<SpokenLanguage> spokenLanguages = null;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("tagline")
    @Expose
    String tagline;
    @SerializedName("title")
    @Expose
    String title;
    @SerializedName("video")
    @Expose
    Boolean video;
    @SerializedName("vote_average")
    @Expose
    Double voteAverage;
    @SerializedName("vote_count")
    @Expose
    Integer voteCount;

    String IMAGE_API_PATH = "http://image.tmdb.org/t/p/%s/%s";

    /**
     * Builds the real url to retrieve the poster's path
     *
     * @param posterSize Poster size
     * @return String with the final url.
     */
    public String getRealPosterPath(PosterSize posterSize) {
        return String.format(IMAGE_API_PATH, posterSize.getValue(), this.posterPath);
    }
}
