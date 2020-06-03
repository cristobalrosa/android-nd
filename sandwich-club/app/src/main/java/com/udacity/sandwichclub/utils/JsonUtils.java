package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    /**
     * @param json
     * @return
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.has("name") ? jsonObject.getJSONObject("name") : new JSONObject();
            String name = getOrDefault(nameObject, "mainName", "unknown");
            List<String> aliases = getStringElementsInArray(nameObject, "alsoKnownAs");
            String placeOfOrigin =  getOrDefault(jsonObject, "placeOfOrigin", "unknown");
            String description = jsonObject.getString("description");
            String image = getOrDefault(jsonObject, "image", "unknown");
            List<String> ingredients = getStringElementsInArray(jsonObject, "ingredients");
            sandwich = new Sandwich(name, aliases, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    private static String getOrDefault(JSONObject jsonObject, String propertyValue, String defaultValue) throws JSONException {
        return jsonObject.has(propertyValue) ? jsonObject.getString(propertyValue) : defaultValue;
    }
    private static List<String> getStringElementsInArray(JSONObject jsonObject, String propertyName) throws JSONException {
        JSONArray array = jsonObject.has(propertyName) ? jsonObject.getJSONArray(propertyName) : new JSONArray();
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            elements.add(array.getString(i));
        }
        return elements;
    }
}