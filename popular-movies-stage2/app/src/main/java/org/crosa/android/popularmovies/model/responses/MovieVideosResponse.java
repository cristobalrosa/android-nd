package org.crosa.android.popularmovies.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.crosa.android.popularmovies.model.MovieVideo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MovieVideosResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<MovieVideo> results = null;
}
