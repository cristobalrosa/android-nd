package org.crosa.android.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.crosa.android.popularmovies.R;
import org.crosa.android.popularmovies.model.MovieVideo;

import java.util.List;

public class MoviesVideoAdapter extends RecyclerView.Adapter<MoviesVideoAdapter.MoviesVideoAdapterViewHolder> {

    private List<MovieVideo> movieVideos;
    private final MoviesVideoAdapter.MoviesVideoAdapterOnClickHandler mClickHandler;

    public MoviesVideoAdapter(MoviesVideoAdapterOnClickHandler handler) {
        this.mClickHandler = handler;
    }

    @Override
    public MoviesVideoAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_video_item, parent, false);
        return new MoviesVideoAdapterViewHolder(view);
    }


    @Override
    public void onBindViewHolder(MoviesVideoAdapterViewHolder holder, int position) {
        MovieVideo movieVideo = movieVideos.get(position);
        holder.mMovieVideoLink.setText(movieVideo.getName());
    }

    @Override
    public int getItemCount() {
        return movieVideos != null ? movieVideos.size() : 0;
    }

    public void setMovieVideos(List<MovieVideo> videos) {
        this.movieVideos = videos;
    }

    public interface MoviesVideoAdapterOnClickHandler {
        void onClick(MovieVideo movie);
    }

    public class MoviesVideoAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // movie_video_item.xml
        private final TextView mMovieVideoLink;
        private final ImageView mMoviePlayButton;

        public MoviesVideoAdapterViewHolder(View itemView) {
            super(itemView);
            mMoviePlayButton = itemView.findViewById(R.id.play_icon);
            mMovieVideoLink = itemView.findViewById(R.id.movie_video_url);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mClickHandler.onClick(movieVideos.get(adapterPosition));
        }
    }
}
