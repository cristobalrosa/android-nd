package org.crosa.android.popularmovies.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Genre {
    // Since I'm using lombok annotation @Value there is no need for private final.
    int id;
    String name;

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";

    public static Genre fromJSON(JSONObject object) {
        return new Genre(object.optInt(ID_KEY, 0),
                object.optString(NAME_KEY, ""));
    }
}