package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        String mainName;
        List<String> alsoKnownAs;
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients;

        JSONObject jsonObject = new JSONObject(json);
        JSONArray alsoKnownAsArray = jsonObject.getJSONObject("name").getJSONArray("alsoKnownAs");
        JSONArray ingredientsArray = jsonObject.getJSONArray("ingredients");

        mainName = jsonObject.getJSONObject("name").getString("mainName");

        alsoKnownAs = new ArrayList<>();
        for (int i = 0; i < alsoKnownAsArray.length(); i++) {
            alsoKnownAs.add(alsoKnownAsArray.getString(i));
        }

        placeOfOrigin = jsonObject.getString("placeOfOrigin");

        description = jsonObject.getString("description");

        image = jsonObject.getString("image");

        ingredients = new ArrayList<>();
        for (int i = 0; i < ingredientsArray.length(); i++) {
            ingredients.add(ingredientsArray.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
