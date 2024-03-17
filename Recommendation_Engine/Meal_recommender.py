import copy

import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
from datetime import datetime


def mutateData(datum):
    newDatum = copy.copy(datum)
    del newDatum["food"]
    timeStr = newDatum["dateTime"]
    time = datetime.strptime(timeStr, '%Y-%m-%d %H:%M:%S')

    # TODO: talk to aaron about how to scale the distances (dist between tuesday - wed =1, noon - 1pm = 60 ???)
    newDatum["dayOfWeek"] = time.weekday()
    newDatum["isWeekday"] = 1 if time.weekday() < 5 else 0
    newDatum["timeOfDay"] = time.hour * 60 + time.minute
    return newDatum


# this program takes in new logs, and goals
# returns one suggestion (activity, meal, meditation, sleep)
def Meal_recommender(data):
    # get raw data and make it usable (datapoints)
    newData = list(map(mutateData, data))

    df = pd.DataFrame(newData)

    # Split data into train and test sets
    X = df[['dayOfWeek', 'isWeekday', 'timeOfDay']]
    y = df['name']
    # test_size
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=0)

    # Initialize and train with the split data set to gauge accuracy of DTC
    clf = DecisionTreeClassifier()
    clf.fit(X_train, y_train)

    # Make predictions and test against split test y array
    # y_pred = clf.predict(X_test)
    # print("accuracy: " + accuracy_score(y_test, y_pred))

    # initialize and train with whole dataset
    clf = DecisionTreeClassifier()
    clf.fit(X, y)

    # Now use the trained model to make recommendations for a new user

    today = datetime.today()
    currentTime = datetime.now()

    new_user_data = pd.DataFrame({
        "dayOfWeek": [today.weekday()],
        "isWeekday": [1 if today.weekday() < 5 else 0],
        "timeOfDay": [currentTime.hour * 60 + currentTime.minute]
    })

    recommendation = clf.predict(new_user_data)[0]
    # print(f"Recommendation for the new user: {recommendation}")
    return recommendation
