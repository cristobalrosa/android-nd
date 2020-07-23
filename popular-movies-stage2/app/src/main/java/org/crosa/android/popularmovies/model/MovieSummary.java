package org.crosa.android.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

/**
 * POJO class to store a movie data
 * Schema from https://developers.themoviedb.org/3/discover/movie-discover
 */
@Value
@AllArgsConstructor
@Builder
public class MovieSummary implements Serializable {
    // class attributes.
    @SerializedName("poster_path")
    String posterPath;
    @SerializedName("adult")
    boolean adult;
    @SerializedName("overview")
    String overview;
    @SerializedName("release_date")
    String releaseDate;
    @SerializedName("genre_ids")
    Integer[] genresIds;
    @SerializedName("id")
    int id;
    @SerializedName("original_title")
    String originalTitle;
    @SerializedName("original_language")
    String originalLanguage;
    @SerializedName("title")
    String title;
    @SerializedName("backdrop_pathd")
    String backdropPath;
    @SerializedName("popularity")
    double popularity;
    @SerializedName("vote_count")
    int voteCount;
    @SerializedName("video")
    boolean video;
    @SerializedName("vote_average")
    double voteAverage;
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

