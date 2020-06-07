package org.crosa.android.popularmovies.client.impl;

import android.net.Uri;
import android.util.Log;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.model.Movie;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TheMovieDBClientImpl implements IMoviesDatabaseClient {
    private static final String TAG = TheMovieDBClientImpl.class.getSimpleName();
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String DISCOVER_PATH = "discover/movie";
    private static final String API_KEY_PARAM = "api_key";
    private static final String SORT_BY_PARAM = "sort_by";
    private final String IMAGE_API_BASE_URL = "http://image.tmdb.org/t/p/";
    private final String apiKey;

    public TheMovieDBClientImpl(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Movie> getPopularMovies() {
        List<Movie> movies = new LinkedList<>();
        Map<String, String> params = new HashMap<>();
        params.put(SORT_BY_PARAM, "popularity.desc");
        URL url = buildUrl(DISCOVER_PATH, params);
        try {
            String response = getResponseFromHttpUrl(url);
            if (response != null) {
                JSONObject jsonResponse = new JSONObject(response);
                movies = parseMovies(jsonResponse);
            } else {
                Log.e(TAG, "Null reponse from the movie api.");
            }
        } catch (Exception e) {
            // Maybe for some of the exceptions we could do something about it, but so far we are
            // just going to print the error message.
            Log.e(TAG, e.getMessage());
        }
        return movies;
    }

    private List<Movie> parseMovies(JSONObject jsonObject) throws JSONException {
        List<Movie> movies = new LinkedList<>();
        if (jsonObject.has("status_code")) {
            // Something is wrong with the request.
            Log.e(TAG, "The movie api return an error" + jsonObject.toString());
        } else {
            JSONArray movieArray = jsonObject.getJSONArray("results");
            for (int i = 0; i < movieArray.length(); i++) {
                movies.add(Movie.fromJSON(movieArray.getJSONObject(i)));
            }
        }
        return movies;
    }

    /**
     * Builds the URL used to talk to the weather server using a location. This location is based
     * on the query capabilities of the weather provider that we are using.
     *
     * @param path The location that will be queried for.
     * @return The URL to use to query the weather server.
     */
    public URL buildUrl(String path, Map<String, String> params) {
        Uri.Builder builder = Uri.parse(BASE_URL + path).buildUpon()
                .appendQueryParameter(API_KEY_PARAM, apiKey);
        for (String key : params.keySet()) {
            builder.appendQueryParameter(key, params.get(key));
        }
        Uri builtUri = builder.build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    private String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
