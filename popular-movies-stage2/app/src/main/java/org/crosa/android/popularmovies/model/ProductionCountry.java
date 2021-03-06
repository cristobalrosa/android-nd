package org.crosa.android.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductionCountry {
    //Since I'm using lombok annotation @Value there is no need for private final.
    @SerializedName("iso_3166_1")
    @Expose
    String iso31661;
    @SerializedName("name")
    @Expose
    String name;
}
