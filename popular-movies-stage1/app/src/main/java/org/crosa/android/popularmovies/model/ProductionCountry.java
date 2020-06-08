package org.crosa.android.popularmovies.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductionCountry {
    private final String iso3166_1;
    private final String name;

    private static final String ISO_KEY = "iso_3166_1";
    private static final String NAME_KEY = "name";

    public static ProductionCountry fromJSON(JSONObject object) {
        return new ProductionCountry(object.optString(ISO_KEY, ""),
                object.optString(NAME_KEY, ""));
    }
}
