package org.crosa.android.popularmovies.model;

import org.json.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class SpokenLanguage {
    private final String iso639_1;
    private final String name;

    private static final String ISO_KEY = "iso_639_1";
    private static final String NAME_KEY = "name";

    public static SpokenLanguage fromJSON(JSONObject object) {
        return new SpokenLanguage(object.optString(ISO_KEY, ""),
                object.optString(NAME_KEY, ""));
    }
}
