package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String SANDWICH_NAME_KEY = "name";
    private final static String SANDWICH_MAIN_NAME_KEY = "mainName";
    private final static String SANDWICH_ALSO_UNKNOWN_AS_KEY = "alsoKnownAs";
    private final static String SANDWICH_PLACE_OF_ORIGIN_KEY = "placeOfOrigin";
    private final static String SANDWICH_DESCRIPTION_KEY = "description";
    private final static String SANDWICH_IMAGE_KEY = "image";
    private final static String SANDWICH_INGREDIENTS_KEY = "ingredients";

    /**
     * Map the json's representation of a sandwich into a Sandwich object.
     * It is not allowed to use a mapping library.
     *
     * @param json Json String
     * @return Sandwich object
     */
    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.has(SANDWICH_NAME_KEY) ? jsonObject.getJSONObject(SANDWICH_NAME_KEY) : new JSONObject();
            String name = getOrDefaultWithPlaceHolder(jsonObject, SANDWICH_MAIN_NAME_KEY, "Name not available");
            List<String> aliases = getStringElementsInArray(nameObject, SANDWICH_ALSO_UNKNOWN_AS_KEY);
            String placeOfOrigin = getOrDefaultWithPlaceHolder(jsonObject, SANDWICH_PLACE_OF_ORIGIN_KEY, "Place of origin not available");
            String description = getOrDefaultWithPlaceHolder(jsonObject, SANDWICH_DESCRIPTION_KEY, "Description not available");
            String image = getOrDefaultWithPlaceHolder(jsonObject, SANDWICH_IMAGE_KEY, "Image not available");
            List<String> ingredients = getStringElementsInArray(jsonObject, SANDWICH_INGREDIENTS_KEY);
            sandwich = new Sandwich(name, aliases, placeOfOrigin, description, image, ingredients);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }

    /**
     * Retrieves a given property from the JSONObject using a place holder in case the property is
     * missing or empty.
     * @param jsonObject JSONObject
     * @param property Property where the value is placed
     * @param placeHolder The place holder string in case the property is missing or empty
     * @return String with the property value or the placeholder string.
     */
    private static String getOrDefaultWithPlaceHolder(JSONObject jsonObject, String property, String placeHolder) {
        String value = jsonObject.optString(property, placeHolder);
        if (value.equals("")) {
            value = placeHolder;
        }
        return value;
    }

    /**
     * Retrieves a list of element from an array. If the array is empty it uses a place holder
     * to let the user know there is no such value
     * @param jsonObject JSONObject
     * @param propertyName The property name where the array should be
     * @return a List Of Strings.
     * @throws JSONException
     */
    private static List<String> getStringElementsInArray(JSONObject jsonObject, String propertyName) throws JSONException {
        JSONArray array = jsonObject.has(propertyName) ? jsonObject.optJSONArray(propertyName) : new JSONArray();
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            elements.add(array.getString(i));
        }
        // Place holder in case it is empty
        if (elements.size() == 0) {
            elements.add("Unavailable");
        }
        return elements;
    }
}