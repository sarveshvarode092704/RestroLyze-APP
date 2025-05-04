package com.example.restrolyze;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class third_page extends AppCompatActivity {

    private static final String TAG = "third_page"; // For logging
    private EditText editTextCity, editTextLocality, editTextCuisines;
    private RadioGroup radioGroupTableBooking, radioGroupOnlineDelivery, radioGroupPriceRange;
    private TextView textViewResult;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_page);

        // Initialize UI elements
        editTextCity = findViewById(R.id.editTextText1);
        editTextLocality = findViewById(R.id.editTextText2);
        editTextCuisines = findViewById(R.id.editTextText3);
        textViewResult = findViewById(R.id.textViewResult);

        radioGroupTableBooking = findViewById(R.id.radioGroupTableBooking);
        radioGroupOnlineDelivery = findViewById(R.id.radioGroupOnlineDelivery);
        radioGroupPriceRange = findViewById(R.id.radioGroupPriceRange1);

        Button buttonSubmit = findViewById(R.id.button);

        buttonSubmit.setOnClickListener(v -> {
            // Collect user input
            String city = editTextCity.getText().toString().trim();
            String locality = editTextLocality.getText().toString().trim();
            String cuisines = editTextCuisines.getText().toString().trim();
            String tableBooking = ((RadioButton) findViewById(radioGroupTableBooking.getCheckedRadioButtonId())).getText().toString();
            String onlineDelivery = ((RadioButton) findViewById(radioGroupOnlineDelivery.getCheckedRadioButtonId())).getText().toString();
            String priceRange = ((RadioButton) findViewById(radioGroupPriceRange.getCheckedRadioButtonId())).getText().toString();

            if (city.isEmpty() || locality.isEmpty() || cuisines.isEmpty() || tableBooking.isEmpty() || onlineDelivery.isEmpty() || priceRange.isEmpty()) {
                textViewResult.setText("Please enter all features.");
                return; // Stop further execution
            }

            // Prepare request payload
            JSONObject jsonRequest = new JSONObject();
            try {
                jsonRequest.put("City", city);
                jsonRequest.put("Locality", locality);
                jsonRequest.put("Cuisines", cuisines);
                jsonRequest.put("Has Table booking", tableBooking);
                jsonRequest.put("Has Online delivery", onlineDelivery);
                jsonRequest.put("Price range", priceRange);
            } catch (JSONException e) {
                Log.e(TAG, "JSON Exception", e); // Use Log.e() instead of printStackTrace()
            }

            // Create a request queue
            RequestQueue queue = Volley.newRequestQueue(third_page.this);

            // Create and add the request to the RequestQueue
            queue.add(createJsonObjectRequest(jsonRequest));
        });
    }

    // Extracted method for creating JsonObjectRequest
    @SuppressLint("SetTextI18n")
    private JsonObjectRequest createJsonObjectRequest(JSONObject jsonRequest) {
        String url = "http://10.0.2.2:5001/recommendation"; // Use 10.0.2.2 for localhost on Android Emulator
        return new JsonObjectRequest(
                Request.Method.POST, url, jsonRequest,
                response -> {
                    try {
                        if (response.has("status") && response.getString("status").equals("success")) {
                            // Update the result TextView with recommendations
                            StringBuilder result = new StringBuilder();
                            JSONArray recommendations = response.getJSONArray("data");
                            for (int i = 0; i < recommendations.length(); i++) {
                                result.append(recommendations.getString(i)).append("\n");
                            }
                            textViewResult.setText(result.toString().trim()); // Set formatted result to TextView
                        } else {
                            textViewResult.setText(response.getString("message"));
                        }
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON Exception", e); // Use Log.e() instead of printStackTrace()
                    }
                },
                error -> textViewResult.setText("Error: " + error.getMessage())
        );
    }
}
