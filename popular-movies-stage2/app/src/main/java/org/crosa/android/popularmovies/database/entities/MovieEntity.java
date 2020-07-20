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

    public MovieEntity(int id, String originalTitle) {
        this.id = id;
        this.originalTitle = originalTitle;
    }

    public int getId() {
        return this.id;
    }

    public String getOriginalTitle() {
        return this.originalTitle;
    }
}
