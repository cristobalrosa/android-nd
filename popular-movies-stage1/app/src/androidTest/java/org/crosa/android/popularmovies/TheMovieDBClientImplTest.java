package org.crosa.android.popularmovies;

import android.content.res.Resources;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.PosterSize;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TheMovieDBClientImplTest {
    @Test
    public void testSimpleE2EHappyPath(){
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBClientImpl(apiKey);
        List<MovieSummary> movieSummaryList = m.getPopularMovies(1);
        // The default number of movies returned by the api is 20
        assertEquals(20, movieSummaryList.size());
    }

    @Test
    public void testGetTopRatedE2EHappyPath(){
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBClientImpl(apiKey);
        List<MovieSummary> movieSummaryList = m.getTopRated(1);
        // The default number of movies returned by the api is 20
        assertEquals(20, movieSummaryList.size());
    }

    @Test
    public void testGetDetailsHappyPath(){
        Resources resources = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources();
        String apiKey = resources.getString(R.string.moviedb_secret);
        IMoviesDatabaseClient m = new TheMovieDBClientImpl(apiKey);
        MovieDetails movieDetails = m.getMovieDetails(278);
        assertNotNull(movieDetails);
        assertEquals("The Shawshank Redemption", movieDetails.getOriginalTitle());
        assertEquals("http://image.tmdb.org/t/p/w185//5KCVkau1HEl7ZzfPsKAPM0sMiKc.jpg", movieDetails.getRealPosterPath(PosterSize.W_185));
    }
}
