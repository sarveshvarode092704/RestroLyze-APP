from flask import Flask, request, jsonify
import pandas as pd
import numpy as np
from sklearn.metrics.pairwise import cosine_similarity
import pickle
import logging

app = Flask(__name__)

# Set up logging
logging.basicConfig(level=logging.INFO)

# Load the pickled model
with open('Recommendation.pkl', 'rb') as f:
    model = pickle.load(f)

encoder = model['encoder']
encoded_features = model['encoded_features']
df = model['df']

# Define the categorical features
categorical_features = ['City', 'Locality', 'Cuisines', 'Has Table booking', 'Has Online delivery', 'Price range']

# Function to recommend restaurants based on user preferences
def recommend_restaurants(user_preferences, num_recommendations=5):
    # Convert user preferences to DataFrame with the same feature names
    user_input_df = pd.DataFrame([user_preferences], columns=categorical_features)
    
    # Transform user input using the fitted encoder
    user_vector = encoder.transform(user_input_df).toarray()
    
    # Calculate similarity
    similarity_scores = cosine_similarity(user_vector, encoded_features)
    
    # Recommend top N restaurants
    top_indices = np.argsort(similarity_scores[0])[-num_recommendations:][::-1]
    recommended_restaurants = df.iloc[top_indices][['Restaurant Name', 'Aggregate rating']]
    
    # Sort the recommended restaurants by their aggregate rating in descending order
    recommended_restaurants = recommended_restaurants.sort_values(by='Aggregate rating', ascending=False)

    if recommended_restaurants.empty:
        raise ValueError("No recommendations found for the given preferences.")
    
    # Format recommendations with ranking
    recommendations_list = [
        f"{rank}. {row['Restaurant Name']}, rating: {row['Aggregate rating']}"
        for rank, (idx, row) in enumerate(recommended_restaurants.iterrows(), start=1)
    ]
    
    return recommendations_list

@app.route('/recommendation', methods=['POST'])
def recommend():
    try:
        # Extract user preferences from JSON or form-data
        if request.is_json:
            user_preferences = request.json
        else:
            user_preferences = {feature: request.form.get(feature) for feature in categorical_features}
        
        # Convert price range to integer if necessary
        if 'Price range' in user_preferences:
            user_preferences['Price range'] = int(user_preferences['Price range'])

        # Validate the request
        for feature in categorical_features:
            if feature not in user_preferences or user_preferences[feature] is None:
                return jsonify({"status": "error", "message": f"Missing feature: {feature}"}), 400
        
        # Allow user to specify the number of recommendations
        num_recommendations = int(request.form.get('num_recommendations', 5))
        
        # Get recommendations
        recommendations = recommend_restaurants(user_preferences, num_recommendations)
        
        # Log successful recommendations
        logging.info(f"Recommendations generated for: {user_preferences}")
        
        # Return recommendations as a simple formatted string
        return jsonify({"status": "success", "data": recommendations})
    except ValueError as ve:
        logging.error(f"Value error: {ve}")
        return jsonify({"status": "error", "message": str(ve)}), 400
    except Exception as e:
        logging.error(f"An error occurred: {e}")
        return jsonify({"status": "error", "message": f"An error occurred: {e}"}), 500

if __name__ == '__main__':
    app.run(app.run(host='0.0.0.0', port=5001)
)
