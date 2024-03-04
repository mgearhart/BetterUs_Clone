import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
# this file takes in and new meal logs adds it to the array
# returns one suggestion for a meal


# Training data
# here's how to read it, each key has an array, and each index of that array is a column
data = {
    'heart_rate': [70, 80, 190, 190, 200, 100],  # Heart rate in bpm
    'step_count': [5000, 8000, 10000, 0, 0, 6000],  # Daily step count
    'calories': [1500, 1800, 2000, 0, 0, 1000],  # Daily calorie intake
    'time_of_day': [9, 13, 20, 1, 12, 14],  # Time of day
    'recommendation': ['activity', 'meal', 'meditation', 'meditation', 'meditation', 'meal']  # Target recommendation
}

df = pd.DataFrame(data)

# Split data into train and test sets
X = df[['heart_rate', 'step_count', 'calories', 'time_of_day']]
y = df['recommendation']
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)

# Initialize and train the decision tree classifier
clf = DecisionTreeClassifier()
clf.fit(X_train, y_train)

# Make predictions
y_pred = clf.predict(X_test)

# Evaluate model accuracy
accuracy = accuracy_score(y_test, y_pred)
print(f"Model accuracy: {accuracy:.2f}")

# Now use the trained model to make recommendations for a new user
new_user_data = pd.DataFrame({
    'heart_rate': [190],
    'step_count': [6000],
    'calories': [2000],
    'time_of_day': [11]
})

recommendation = clf.predict(new_user_data)[0]
print(f"Recommendation for the new user: {recommendation}")
