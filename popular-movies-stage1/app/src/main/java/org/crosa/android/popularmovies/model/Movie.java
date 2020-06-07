package org.crosa.android.popularmovies.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

/**
 * POJO class to store a movie data
 * Schema from https://developers.themoviedb.org/3/discover/movie-discover
 */
@Value
@AllArgsConstructor
public class Movie {
    // Movie json object keys.
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String ADULT_KEY = "adult";
    private static final String OVERVIEW_KEY = "overview";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String GENRE_IDS_KEY = "genre_ids";
    private static final String ID_KEY = "id";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String ORIGINAL_LANGUAGE_KEY = "original_language";
    private static final String TITLE_KEY = "title";
    private static final String BACKDROP_PATH_KEY = "backdrop_pathd";
    private static final String POPULARITY_KEY = "popularity";
    private static final String VOTE_COUNT_KEY = "vote_count";
    private static final String VIDEO_KEY = "video";
    private static final String VOTE_AVERAGE_KEY = "vote_average";

    // class attributes.
    String posterPath;
    boolean adult;
    String overview;
    String releaseDate;
    Integer[] genresIds;
    int id;
    String originalTitle;
    String originalLanguage;
    String title;
    String backdropPath;
    double popularity;
    int voteCount;
    boolean video;
    double voteAverage;

    /**
     * Parses JSONObject into a Movie object
     * <p>
     * NOTE: If we can use gson instead of this json library it would be much easier.
     *
     * @param object JSONObject
     * @return Movie.
     */
    public static Movie fromJSON(JSONObject object) {
        List<Integer> genres = new ArrayList<>();
        JSONArray genresArray = object.optJSONArray(GENRE_IDS_KEY);
        if (genresArray != null) {
            for (int i = 0; i < genresArray.length(); i++) {
                genres.add(genresArray.optInt(i, 0));
            }
        }

        Movie movie = new Movie(
                object.optString(POSTER_PATH_KEY, ""),
                object.optBoolean(ADULT_KEY, true),
                object.optString(OVERVIEW_KEY, ""),
                object.optString(RELEASE_DATE_KEY, ""),
                genres.toArray(new Integer[genres.size()]),
                object.optInt(ID_KEY, 0),
                object.optString(ORIGINAL_TITLE_KEY, ""),
                object.optString(ORIGINAL_LANGUAGE_KEY, ""),
                object.optString(TITLE_KEY, ""),
                object.optString(BACKDROP_PATH_KEY, ""),
                object.optDouble(POPULARITY_KEY, 0.0),
                object.optInt(VOTE_COUNT_KEY, 0),
                object.optBoolean(VIDEO_KEY, false),
                object.optDouble(VOTE_AVERAGE_KEY, 0.0)
        );
        return movie;
    }
}

