package org.crosa.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.PosterSize;

import java.util.Locale;

public class MovieDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        TextView mMovieTitle = findViewById(R.id.tv_detail_movie_title);
        ImageView mMoviePoster = findViewById(R.id.iv_detail_movie_poster);
        TextView mReleaseDate = findViewById(R.id.tv_details_release_date);
        TextView mMovieOverview = findViewById(R.id.tv_detail_overview);
        TextView mRating = findViewById(R.id.tv_movie_rating);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movieDetail")) {
                MovieSummary mMovieDetails = (MovieSummary) intentThatStartedThisActivity.getSerializableExtra("movieDetail");
                mMovieTitle.setText(mMovieDetails.getTitle());
                mReleaseDate.setText(mMovieDetails.getReleaseDate());
                mMovieOverview.setText(mMovieDetails.getOverview());
                mRating.setText(String.format(Locale.getDefault(), "%.2f/10", mMovieDetails.getVoteAverage()));
                Picasso.get().load(mMovieDetails.getRealPosterPath(PosterSize.W_185)).into(mMoviePoster);
            }
        }
    }
}