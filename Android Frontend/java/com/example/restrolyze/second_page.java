package com.example.restrolyze;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class second_page extends AppCompatActivity {

    // Define variables for UI elements
    EditText editTextText1, editTextText3, editTextText4, editTextText5;
    RadioGroup radioGroup;
    Button buttonSubmit;
    TextView resultField;
    private static final String TAG = "SecondPageActivity";

    @SuppressLint({"WrongViewCast", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_page);

        // Initialize UI elements
        editTextText1 = findViewById(R.id.editTextAverageCost);
        radioGroup = findViewById(R.id.radioGroupPriceRange2);
        editTextText3 = findViewById(R.id.editTextCuisines);
        editTextText4 = findViewById(R.id.editTextTimings);
        editTextText5 = findViewById(R.id.editTextHighlights);
        buttonSubmit = findViewById(R.id.buttonSubmit);
        resultField = findViewById(R.id.textViewResult);

        buttonSubmit.setOnClickListener(v -> {
            String avgCost = editTextText1.getText().toString().trim();
            String cuisines = editTextText3.getText().toString().trim();
            String timings = editTextText4.getText().toString().trim();
            String highlights = editTextText5.getText().toString().trim();

            int selectedId = radioGroup.getCheckedRadioButtonId();
            RadioButton selectedRadioButton = findViewById(selectedId);
            String priceRange = selectedRadioButton != null ? selectedRadioButton.getText().toString() : null;

            if (TextUtils.isEmpty(avgCost) || TextUtils.isEmpty(cuisines) || TextUtils.isEmpty(timings) || TextUtils.isEmpty(highlights) || priceRange == null) {
                resultField.setText("Please fill in all fields.");
                return;
            }

            sendPredictionRequest(avgCost, priceRange, cuisines, timings, highlights);
        });
    }

    @SuppressLint("SetTextI18n")
    private void sendPredictionRequest(String avgCost, String priceRange, String cuisines, String timings, String highlights) {
        String url = "http://10.0.2.2:5000/predict_rating";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        String predictedRating = jsonResponse.getString("Predicted Rating");
                        resultField.setText("Predicted Rating: " + predictedRating);
                    } catch (JSONException e) {
                        resultField.setText("Error parsing response.");
                        Log.e(TAG, "JSON Exception during response parsing", e);
                    }
                },
                error -> {
                    resultField.setText("Error: " + error.getMessage());
                    Log.e(TAG, "Volley error during API request", error);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("average_cost_for_two", avgCost);
                params.put("price_range", priceRange);
                params.put("cuisines", cuisines);
                params.put("timings", timings);
                params.put("highlights", highlights);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
