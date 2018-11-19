package com.example.mmmalthaf.momsmenu.RecipeFeed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mmmalthaf.momsmenu.R;
import com.example.mmmalthaf.momsmenu.ViewRecipe.RecipeDetailActivity;

public class vegOrNonVeg extends AppCompatActivity {
    Boolean isVeg=false;
    Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg_or_non_veg);

        b1 = (Button)findViewById(R.id.vegBtn);
        b2 = (Button)findViewById(R.id.nonVegBtn);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVeg = true;
                Intent intent = new Intent(getApplicationContext(),PostTabActivity.class);
                intent.putExtra("veg?",isVeg);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVeg = false;
                Intent intent = new Intent(getApplicationContext(),PostTabActivity.class);
                intent.putExtra("veg?",isVeg);
                startActivity(intent);
            }

        });

    }
}
