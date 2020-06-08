package org.crosa.android.popularmovies.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ProductionCompany {
    private final String name;
    private final int id;
    private final String logoPath;
    private final String originCountry;

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
