package org.crosa.android.popularmovies.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

// Not sure why lombok does not work properly with entities
@Entity(tableName = "movies")
public class MovieEntity {
    // class attributes.
    @PrimaryKey(autoGenerate = false)
    int id;
    @ColumnInfo(name = "original_title")
    String originalTitle;
    @ColumnInfo(name = "release_date")
    String releaseDate;
    @ColumnInfo(name = "overview")
    String overview;
    @ColumnInfo(name = "vote_average")
    double voteAverage;
    @ColumnInfo(name = "poster_path")
    String posterPath;
    @ColumnInfo(name = "title")
    String title;

    public MovieEntity(int id, String title, String originalTitle, String releaseDate, String overview, double voteAverage, String posterPath) {
        this.id = id;
        this.title = title;
        this.originalTitle = originalTitle;
        this.releaseDate = releaseDate;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOverview() {
        return overview;
    }
}
