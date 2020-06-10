package org.crosa.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.crosa.android.popularmovies.model.MovieDetails;
import org.crosa.android.popularmovies.model.MovieSummary;
import org.crosa.android.popularmovies.model.PosterSize;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView mMoviePoster;
    private TextView mMovieTitle;
    private TextView mReleaseDate;
    private TextView mMovieOverview;
    private MovieSummary mMovieDetails;
    private RatingBar mRatingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        mMovieTitle = findViewById(R.id.tv_detail_movie_title);
        mMoviePoster = findViewById(R.id.iv_detail_movie_poster);
        mReleaseDate = findViewById(R.id.tv_details_release_date);
        mMovieOverview = findViewById(R.id.tv_detail_overview);
        mRatingBar = findViewById(R.id.rb_detail_vote_average);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movieDetail")) {
                mMovieDetails = (MovieSummary) intentThatStartedThisActivity.getSerializableExtra("movieDetail");
                mMovieTitle.setText(mMovieDetails.getTitle());
                mReleaseDate.setText(mMovieDetails.getReleaseDate());
                mMovieOverview.setText(mMovieDetails.getOverview());
                //TODO change this.
                mRatingBar.setRating((float) mMovieDetails.getVoteAverage());
                Picasso.get().load(mMovieDetails.getRealPosterPath(PosterSize.W_185)).into(mMoviePoster);
            }
        }
    }
}