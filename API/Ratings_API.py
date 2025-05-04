from flask import Flask, request, jsonify
import pickle
import pandas as pd

# Load the model from the pickle file
model = pickle.load(open('ratings.pkl', 'rb'))
app = Flask(__name__)

@app.route('/predict_rating', methods=['POST'])
def predict_rating():
    try:
        # Get the input data from the POST request
        AVG_COST_OF_TWO = request.form.get('average_cost_for_two')
        PRICE_RANGE = request.form.get('price_range')
        CUISINE = request.form.get('cuisines')
        TIMINGS = request.form.get('timings')
        HIGHLIGHTS = request.form.get('highlights')

        # Input validation
        if not all([AVG_COST_OF_TWO, PRICE_RANGE, CUISINE, TIMINGS, HIGHLIGHTS]):
            return jsonify({'error': 'Missing input fields'}), 400

        # Convert numeric inputs to their appropriate types
        try:
            AVG_COST_OF_TWO = float(AVG_COST_OF_TWO)
            PRICE_RANGE = int(PRICE_RANGE)
        except ValueError:
            return jsonify({'error': 'Invalid input types for numeric fields'}), 400

        # Create a DataFrame with appropriate column names
        input_data = pd.DataFrame([[AVG_COST_OF_TWO, PRICE_RANGE, CUISINE, TIMINGS, HIGHLIGHTS]],
                                  columns=['average_cost_for_two', 'price_range', 'cuisines', 'timings', 'highlights'])

        # Make a prediction
        rating = model.predict(input_data)[0]
        
        return jsonify({'Predicted Rating': str(rating)})

    except Exception as e:
        # Catch any other errors and return a generic error message
        return jsonify({'error': 'An error occurred during prediction: ' + str(e)}), 500

if __name__ == "__main__":
    app.run(host='0.0.0.0', port=5000)
