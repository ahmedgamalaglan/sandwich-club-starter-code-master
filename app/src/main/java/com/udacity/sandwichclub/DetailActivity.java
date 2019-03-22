package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView mainName, alsoKnownAs, ingredients, placeOfOrigin, description;
    ImageView ingredientsIv;
    Sandwich sandwich = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        ingredientsIv = findViewById(R.id.image_iv);
        mainName = findViewById(R.id.main_name_tv);
        alsoKnownAs = findViewById(R.id.also_known_as_tv);
        ingredients = findViewById(R.id.ingredients_tv);
        placeOfOrigin = findViewById(R.id.place_of_origin_tv);
        description = findViewById(R.id.description_tv);
    }


    private void populateUI() {

        mainName.setText(sandwich.getMainName());

        StringBuilder alsoKnownAsText = new StringBuilder();
        for (int i = 0; i < sandwich.getAlsoKnownAs().size(); i++) {
            alsoKnownAsText.append(sandwich.getAlsoKnownAs().get(i));
            if (i<sandwich.getAlsoKnownAs().size()-1)
                alsoKnownAsText.append(" , ");
        }
        alsoKnownAs.setText(alsoKnownAsText.toString());

        StringBuilder ingredientsText = new StringBuilder();
        for (int i=0;i< sandwich.getIngredients().size();i++) {
            ingredientsText.append(sandwich.getIngredients().get(i));
            if (i<sandwich.getIngredients().size()-1)
                ingredientsText.append(" , ");

        }
        ingredients.setText(ingredientsText.toString());
        placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        description.setText(sandwich.getDescription());
    }
}
