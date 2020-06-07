package org.crosa.android.popularmovies;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.crosa.android.popularmovies.client.IMoviesDatabaseClient;
import org.crosa.android.popularmovies.client.impl.TheMovieDBClientImpl;
import org.crosa.android.popularmovies.model.Movie;
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
        List<Movie> movieList = m.getPopularMovies();
        // The default number of movies returned by the api is 20
        assertEquals(20, movieList.size());
    }
}
