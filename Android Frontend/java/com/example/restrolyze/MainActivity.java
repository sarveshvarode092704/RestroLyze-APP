package com.example.restrolyze;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the button by its ID
        Button ratingPredictorButton = findViewById(R.id.ratingPredictorButton);

        // Set an onClick listener on the button
        ratingPredictorButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, second_page.class);
            startActivity(intent);
        });

        // Find the recommendation button by its ID
        Button recommendationButton = findViewById(R.id.recommendationButton);

        // Set an onClick listener on the button
        recommendationButton.setOnClickListener(v -> {
            Intent in = new Intent(MainActivity.this, third_page.class);
            startActivity(in);
        });
    }
}
