package org.crosa.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.crosa.android.popularmovies.R;
import org.crosa.android.popularmovies.model.MovieReview;

import java.util.List;

public class MoviesReviewAdapter extends RecyclerView.Adapter<MoviesReviewAdapter.MoviesReviewAdapterViewHolder> {

    private List<MovieReview> movieReviews;

    public MoviesReviewAdapter() {
    }

    @Override
    public MoviesReviewAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_reivew_item, parent, false);
        return new MoviesReviewAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MoviesReviewAdapterViewHolder holder, int position) {
        MovieReview movieReview = movieReviews.get(position);
        holder.mReview.setText(movieReview.getContent());
        holder.mAuthor.setText(movieReview.getAuthor());
    }

    @Override
    public int getItemCount() {
        return movieReviews != null ? movieReviews.size() : 0;
    }


    public void setReviews(List<MovieReview> reviews) {
        this.movieReviews = reviews;
    }

    public class MoviesReviewAdapterViewHolder extends RecyclerView.ViewHolder {

        // movie_review_item.xml
        private final TextView mAuthor;
        private final TextView mReview;

        public MoviesReviewAdapterViewHolder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.movie_review_author);
            mReview = itemView.findViewById(R.id.movie_review);
        }
    }
}
