package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    //View Injection using Butterknife
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAsTextView;
    @BindView(R.id.origin_tv)
    TextView mOriginTextView;
    @BindView(R.id.ingredients_tv)
    TextView mIngredientsTextVew;
    @BindView(R.id.description_tv)
    TextView mDescriptionTextView;
    @BindView(R.id.image_iv)
    ImageView mIngredientsImageView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //Binding views to target activity. Here activity is this i.e. DetailsActivity
        ButterKnife.bind(this);

        //Set Toolbar as the Action Bar
        setSupportActionBar(mToolbar);

        //Set the up button for back feature
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set intent to value passed by getIntent()
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

        //Get sandwiches details from string-array resource
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        //Populate the layout with sandwich object data from JsonUtils class
        populateUI(sandwich);
        //Picasso library for image loading
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mIngredientsImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> aliasNames = sandwich.getAlsoKnownAs();
        for (String s : aliasNames) {
            if(!s.equals(aliasNames.get(aliasNames.size() - 1)))
            mAlsoKnownAsTextView.append(s + "\n");
            else
                mAlsoKnownAsTextView.append(s);
        }

        mOriginTextView.setText(sandwich.getPlaceOfOrigin());
        mDescriptionTextView.setText(sandwich.getDescription());

        List<String> ingredientsList = sandwich.getIngredients();
        for(String s : ingredientsList) {
            if(!s.equals(ingredientsList.get(ingredientsList.size() - 1)))
            mIngredientsTextVew.append(s + "\n");
            else
                mIngredientsTextVew.append(s);
        }
    }
}
