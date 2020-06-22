package org.crosa.android.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductionCompany {
    // Since I'm using lombok annotation @Value there is no need for private final.
    @SerializedName("id")
    @Expose
    Integer id;
    @SerializedName("logo_path")
    @Expose
    String logoPath;
    @SerializedName("name")
    @Expose
    String name;
    @SerializedName("origin_country")
    @Expose
    String originCountry;

}
