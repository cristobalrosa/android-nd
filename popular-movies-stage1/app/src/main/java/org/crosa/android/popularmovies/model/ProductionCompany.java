package org.crosa.android.popularmovies.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductionCompany {
    // Since I'm using lombok annotation @Value there is no need for private final.
    String name;
    int id;
    String logoPath;
    String originCountry;

    private static final String NAME_KEY = "name";
    private static final String ID_KEY = "id";
    private static final String LOGO_KEY = "logo_path";
    private static final String ORIGIN_COUNTRY_KEY = "originCountry";

    public static ProductionCompany fromJSON(JSONObject object) {
        return new ProductionCompany(
                object.optString(NAME_KEY, ""),
                object.optInt(ID_KEY, 0),
                object.optString(LOGO_KEY, ""),
                object.optString(ORIGIN_COUNTRY_KEY, "")
        );
    }
}
