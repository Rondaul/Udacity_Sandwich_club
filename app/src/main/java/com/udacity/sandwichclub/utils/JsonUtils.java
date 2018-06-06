package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //static final variable for keys of JSONObject
    private static final String NAME = "name";
    private static final String MAIN_NAME = "mainName";
    private static final String ALSO_KNOWN_AS = "alsoKnownAs";
    private static final String PLACE_OF_ORIGIN = "placeOfOrigin";
    private static final String DESCRIPTION = "description";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        //Initialize null Sandwich object
        Sandwich sandwich = null;
        try {
            //Create a jsonObject to convert JSON string to JSON object and get all the values to
            //create a sandwich object
            JSONObject mainJSONObject = new JSONObject(json);
            JSONObject nameJSONObject = mainJSONObject.getJSONObject(NAME);
            String mainName = nameJSONObject.getString(MAIN_NAME);
            List<String> alsoKnownAs = new ArrayList<>();
            JSONArray alsoKnownAsJSONArray = nameJSONObject.getJSONArray(ALSO_KNOWN_AS);
            for(int i = 0 ; i < alsoKnownAsJSONArray.length() ; i++) {
                alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
            }
            String placeOfOrigin = mainJSONObject.getString(PLACE_OF_ORIGIN);
            String description = mainJSONObject.getString(DESCRIPTION);
            String image = mainJSONObject.getString(IMAGE);
            List<String> ingredients = new ArrayList<>();
            JSONArray ingredientsJSONArray = mainJSONObject.getJSONArray(INGREDIENTS);
            for(int i = 0 ; i < ingredientsJSONArray.length(); i++) {
                ingredients.add(ingredientsJSONArray.getString(i));
            }
            //create sandwich object with the values retrieved from jsonObject
            sandwich = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //return sandwich object
        return sandwich;
    }
}
