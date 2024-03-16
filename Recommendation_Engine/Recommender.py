import pandas as pd
from sklearn.tree import DecisionTreeClassifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score
# import Meal_recommender
import json


def recommender(new_logs, goals, current_status):
    goals = json.loads(goals)
    current_status = json.loads(current_status)

    # loading the data into variables that are more managable to deal with.
    goalCaloriesBurnt = goals['exampleList'][0]['0']['caloriesBurnt']
    goalCaloriesGained = goals['exampleList'][0]['0']['caloriesGained']
    goalNumMeditation = goals['exampleList'][0]['0']['numMeditation']
    goalNumExercise = goals['exampleList'][0]['0']['numExercise']
    currCaloriesBurnt = current_status['exampleList'][0]['0']['caloriesBurnt']
    currCaloriesGained = current_status['exampleList'][0]['0']['caloriesGained']
    currNumMeditation = current_status['exampleList'][0]['0']['numMeditation']
    currNumExercise = current_status['exampleList'][0]['0']['numExercise']

    # Calculate deltas
    deltaCaloriesBurnt = (goalCaloriesBurnt - currCaloriesBurnt) / goalCaloriesBurnt
    deltaCaloriesGained = (goalCaloriesGained - currCaloriesGained) / goalCaloriesGained
    deltaNumMeditation = (goalNumMeditation - currNumMeditation) / goalNumMeditation
    deltaNumExercise = (goalNumExercise - currNumExercise) / goalNumExercise

    # Find the biggest delta and its corresponding recommendation
    biggest_delta = deltaCaloriesBurnt
    recommendation = 'activity'
    if biggest_delta < deltaCaloriesGained:
        recommendation = "meal"
        biggest_delta = deltaCaloriesGained
    if biggest_delta < deltaNumMeditation:
        recommendation = 'meditation'
        biggest_delta = deltaNumMeditation
    if biggest_delta < deltaNumExercise:
        recommendation = 'activity'
        biggest_delta = deltaNumExercise
    if biggest_delta <= 0:
        recommendation = 'sleep'

    match recommendation:
        case 'activity':
            print("Activity_recommender()")
        case 'meal':
            print("meal_recommender()")  # meal_name = Meal_recommender()
        case 'meditation':
            print('meditation')
        case 'sleep':
            print('sleep')


# pack the json
# json.dump
# jsonReturn = {
#  "meal": meal_name,
#  "meditationActivity": {"activityName": "meditation", "caloriesPerHour": 0},
#  "exerciseActivity": {"activityName": <name>, "caloriesPerHour": <cal. per hour>}
# }


goalJson = {"exampleList":
    [
        {"0": {
            "caloriesBurnt": 1000,
            "caloriesGained": 2000,
            "numMeditation": 3,
            "numExercise": 2}}
    ]
}

statusJson = {"exampleList":
    [
        {"0": {
            "caloriesBurnt": 1000,
            "caloriesGained": 1500,
            "numMeditation": 2,
            "numExercise": 1}}
    ]
}

# I was seeing the different results to understand
for i in range(1):
    recommender({}, json.dumps(goalJson), json.dumps(statusJson))
