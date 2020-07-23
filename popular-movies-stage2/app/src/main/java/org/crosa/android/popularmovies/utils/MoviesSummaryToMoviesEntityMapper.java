package org.crosa.android.popularmovies.utils;

import android.graphics.Movie;

import org.crosa.android.popularmovies.database.entities.MovieEntity;
import org.crosa.android.popularmovies.model.MovieSummary;

/**
 * Mapper to convert movies summary in movies entities and viceversa.
 * We could use something like mapstruct to do this.
 */
public class MoviesSummaryToMoviesEntityMapper {
    public static MovieEntity to(MovieSummary summary) {
        return new MovieEntity(summary.getId(),
                summary.getTitle(),
                summary.getOriginalTitle(),
                summary.getReleaseDate(),
                summary.getOverview(),
                summary.getVoteAverage(),
                summary.getPosterPath());
    }

    public static MovieSummary from(MovieEntity entity) {
        return MovieSummary.builder()
                .id(entity.getId())
                .originalTitle(entity.getOriginalTitle())
                .title(entity.getTitle())
                .posterPath(entity.getPosterPath())
                .releaseDate(entity.getReleaseDate())
                .voteAverage(entity.getVoteAverage())
                .build();

    }
}
