package org.crosa.android.popularmovies.model;

import android.util.Log;

import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * POJO class to store a movie data
 * Schema from https://developers.themoviedb.org/3/movies/get-movie-details
 */
@Value
@AllArgsConstructor
public class MovieDetails {
    private static final String TAG = MovieDetails.class.getSimpleName();
    // Movie json object keys.
    private static final String ADULT_KEY = "adult";
    private static final String BACKDROP_PATH_KEY = "backdrop_path";
    private static final String BELONGS_TO_COLLECTION_PATH_KEY = "belongs_to_collection";
    private static final String BUDGET_KEY = "budget";
    private static final String GENRE_IDS_KEY = "genre_ids";
    private static final String HOMEPAGE_KEY = "homepage";
    private static final String ID_KEY = "id";
    private static final String IMDB_ID_KEY = "imdb_id";
    private static final String ORIGINAL_LANGUAGE_KEY = "original_language";
    private static final String ORIGINAL_TITLE_KEY = "original_title";
    private static final String OVERVIEW_KEY = "overview";
    private static final String POPULARITY_KEY = "popularity";
    private static final String POSTER_PATH_KEY = "poster_path";
    private static final String PRODUCTION_COMPANIES_PATH_KEY = "production_companies";
    private static final String PRODUCTION_COUNTRIES_PATH_KEY = "production_countries";
    private static final String RELEASE_DATE_KEY = "release_date";
    private static final String REVENUE_KEY = "revenue";
    private static final String RUNTIME_KEY = "runtime";
    private static final String SPOKEN_LANGUAGES_KEY = "spoken_languages";
    private static final String STATUS_KEY = "status";
    private static final String TAG_LINE_KEY = "tagline";
    private static final String TITLE_KEY = "title";
    private static final String VIDEO_KEY = "video";
    private static final String VOTE_AVERAGE_KEY = "vote_average";
    private static final String VOTE_COUNT_KEY = "vote_count";

    // class attributes.
    private final boolean adult;
    private final String backdropPath;
    private final String belongsToCollection; // TODO not sure what kind of data.
    private final int budget;
    private final List<Genre> genres;
    private final String homepage;
    private final int id;
    private final int imdbId;
    private final String originalLanguage;
    private final String originalTitle;
    private final String overview;
    private final double popularity;
    private final String posterPath;
    private final List<ProductionCompany> productionCompanies;
    private final List<ProductionCountry> productionCountries;
    private final String releaseDate;
    private final int revenue;
    private final int runtime;
    private final List<SpokenLanguage> spokenLanguages;
    private final String status;
    private final String tagline;
    private final String title;
    private final boolean video;
    private final double voteAverage;
    private final int voteCount;

    private final String IMAGE_API_PATH = "http://image.tmdb.org/t/p/%s/%s";

    /**
     * Builds the real url to retrieve the poster's path
     *
     * @param posterSize Poster size
     * @return String with the final url.
     */
    public String getRealPosterPath(PosterSize posterSize) {
        return String.format(IMAGE_API_PATH, posterSize.getValue(), this.posterPath);
    }

    /**
     * Parses JSONObject into a Movie object
     * <p>
     * NOTE: If we can use gson instead of this json library it would be much easier.
     *
     * @param object JSONObject
     * @return Movie.
     */
    public static MovieDetails fromJSON(JSONObject object) {
        List<Genre> genres = new ArrayList<>();
        List<ProductionCompany> productionCompanies = new ArrayList<>();
        List<ProductionCountry> productionCountries = new ArrayList<>();
        List<SpokenLanguage> spokenLanguages = new ArrayList<>();
        MovieDetails movieDetails = null;
        try {
            JSONArray genresArray = object.optJSONArray(GENRE_IDS_KEY);
            if (genresArray != null) {
                for (int i = 0; i < genresArray.length(); i++) {
                    genres.add(Genre.fromJSON(genresArray.getJSONObject(i)));
                }
            }
            JSONArray productionCompaniesArray = object.optJSONArray(PRODUCTION_COMPANIES_PATH_KEY);
            if (productionCompaniesArray != null) {
                for (int i = 0; i < productionCompaniesArray.length(); i++) {
                    productionCompanies.add(ProductionCompany.fromJSON(productionCompaniesArray.getJSONObject(i)));
                }
            }

            JSONArray productionCountriesArray = object.optJSONArray(PRODUCTION_COUNTRIES_PATH_KEY);
            if (productionCountriesArray != null) {
                for (int i = 0; i < productionCountriesArray.length(); i++) {
                    productionCountries.add(ProductionCountry.fromJSON(productionCountriesArray.getJSONObject(i)));
                }
            }

            JSONArray spokenLanguagesArray = object.optJSONArray(SPOKEN_LANGUAGES_KEY);
            if (spokenLanguagesArray != null) {
                for (int i = 0; i < spokenLanguagesArray.length(); i++) {
                    spokenLanguages.add(SpokenLanguage.fromJSON(spokenLanguagesArray.getJSONObject(i)));
                }
            }

            movieDetails = new MovieDetails(
                    object.optBoolean(ADULT_KEY, true),
                    object.optString(BACKDROP_PATH_KEY, ""),
                    object.optString(BELONGS_TO_COLLECTION_PATH_KEY, ""),
                    object.optInt(BUDGET_KEY, 0),
                    genres,
                    object.optString(HOMEPAGE_KEY, ""),
                    object.optInt(ID_KEY, 0),
                    object.optInt(IMDB_ID_KEY, 0),
                    object.optString(ORIGINAL_LANGUAGE_KEY, ""),
                    object.optString(ORIGINAL_TITLE_KEY, ""),
                    object.optString(OVERVIEW_KEY, ""),
                    object.optDouble(POPULARITY_KEY, 0.0),
                    object.optString(POSTER_PATH_KEY, ""),
                    productionCompanies,
                    productionCountries,
                    object.optString(RELEASE_DATE_KEY, ""),
                    object.optInt(REVENUE_KEY, 0),
                    object.optInt(RUNTIME_KEY, 0),
                    spokenLanguages,
                    object.optString(STATUS_KEY, ""),
                    object.optString(TAG_LINE_KEY, ""),
                    object.optString(TITLE_KEY, ""),
                    object.optBoolean(VIDEO_KEY, true),
                    object.optDouble(VOTE_AVERAGE_KEY, 0.0),
                    object.optInt(VOTE_COUNT_KEY, 0)
            );

        } catch (Exception e) {
            Log.e(TAG, "Invalid json", e);
        }
        return movieDetails;
    }
}
