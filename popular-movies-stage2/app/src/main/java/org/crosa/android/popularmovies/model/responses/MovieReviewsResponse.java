package org.crosa.android.popularmovies.model.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.crosa.android.popularmovies.model.MovieReview;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class MovieReviewsResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private List<MovieReview> results = null;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("total_results")
    @Expose
    private Integer totalResults;
}
