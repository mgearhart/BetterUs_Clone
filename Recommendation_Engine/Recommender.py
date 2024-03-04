import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score


# this program takes in new logs and adds it to the array
# returns one suggestion (activity, meal, meditation, sleep)

def recommender():
    # Training data
    # here's how to read it, each key has an array, and each index of that array is a column

    # this is about the ideal goal states for each time
    data = {
        'heart_rate': [50, 70, 80, 190, 80],  # Heart rate in bpm
        'step_count': [0, 1000, 6000, 10000, 10000],  # Daily step count
        'calories': [0, 0, 500, 2000, 2000],  # Daily calorie intake
        'time_of_day': [6, 8, 12, 18, 20],  # Time of day
        'recommendation': ['activity', 'meal', 'meal', 'meditation', 'sleep']  # Target recommendation
    }

    df = pd.DataFrame(data)

    # Split data into train and test sets
    X = df[['heart_rate', 'step_count', 'calories', 'time_of_day']]
    y = df['recommendation']
    # setting random state to 0 because it's the ideal state, unsure if that is correct
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)

    # Initialize and train the decision tree classifier
    # no clue what this means
    clf = DecisionTreeClassifier()

    # no clue what this means
    clf.fit(X_train, y_train)
    # Make predictions
    y_pred = clf.predict(X_test)

    # Evaluate model accuracy
    # not needed for my use case?
    accuracy = accuracy_score(y_test, y_pred)
    print(f"Model accuracy: {accuracy:.2f}")

    # Now use the trained model to make recommendations for a new user
    # my understanding is I'm putting in the current state of the user, and I'm out putting a recommendation
    new_user_data = pd.DataFrame({
        'heart_rate': [200],
        'step_count': [6000],
        'calories': [2000],
        'time_of_day': [11]
    })

    recommendation = clf.predict(new_user_data)[0]
    print(f"Recommendation for the new user: {recommendation}")

# I was seeing the different results to understand
for i in range(1):
    recommender()