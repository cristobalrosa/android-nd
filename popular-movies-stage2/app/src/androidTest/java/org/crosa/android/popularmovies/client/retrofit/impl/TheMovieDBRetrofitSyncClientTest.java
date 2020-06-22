package org.crosa.android.popularmovies.client.retrofit.impl;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.crosa.android.popularmovies.R;
import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieReview;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.MovieVideo;
import org.crosa.android.popularmovies.model.PosterSize;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TheMovieDBRetrofitSyncClientTest {
    @Test
    public void testSimpleE2EHappyPath() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBRetrofitSyncClient(apiKey);
        List<MovieSummary> movieSummaryList = m.getPopularMovies(1);
        // The default number of movies returned by the api is 20
        assertEquals(20, movieSummaryList.size());
    }

    @Test
    public void testGetTopRatedE2EHappyPath() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBRetrofitSyncClient(apiKey);
        List<MovieSummary> movieSummaryList = m.getTopRated(1);
        // The default number of movies returned by the api is 20
        assertEquals(20, movieSummaryList.size());
    }

    @Test
    public void testGetDetailsHappyPath() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBRetrofitSyncClient(apiKey);
        MovieDetails movieDetails = m.getMovieDetails(278);
        assertNotNull(movieDetails);
        assertEquals("The Shawshank Redemption", movieDetails.getOriginalTitle());
        assertEquals("http://image.tmdb.org/t/p/w185//5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg", movieDetails.getRealPosterPath(PosterSize.W_185));
    }

    @Test
    public void testGetReviewsHappyPath() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBRetrofitSyncClient(apiKey);
        List<MovieReview> movieReviews = m.getMovieReviews(278);
        assertNotNull(movieReviews);
        // This is a really lazy test but I just want to make sure it works assuming no one removes the current list of reviews (6)
        assertTrue(movieReviews.size() > 0);
    }

    @Test
    public void testGetVideosHappyPath() {
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBRetrofitSyncClient(apiKey);
        List<MovieVideo> movieVideos = m.getMovieVideos(278);
        assertNotNull(movieVideos);
        // This is a really lazy test but I just want to make sure it works assuming no one removes the current list of reviews (6)
        assertTrue(movieVideos.size() > 0);

    }
}