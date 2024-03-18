import Activity_recommender, Meal_recommender
import json


def recommender(new_logs, goals, current_status):
    new_logs = json.dumps(testLogs) #Currently have these three hardcoded to practice data sets, remove these for Firebase data
    goals = json.dumps(goalJson)
    current_status = json.dumps(statusJson)


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

    # loading the appropriate logs
    new_logs = json.loads(new_logs)
    activities = new_logs['exampleList'][0]['0']['userLog']["exerciseLog"]
    print(activities)
    meals = new_logs['exampleList'][0]['0']['userLog']['mealLog']
    print(meals)
    activityRec = ""
    mealRec = ""
    meditationRec = ""
    sleepRec = ""

    if recommendation == 'activity':
        activityRec = Activity_recommender.Activity_recommender(activities)
    elif recommendation == 'meal':
        mealRec = Meal_recommender.Meal_recommender(meals)
    elif recommendation == 'meditation':
        return 'meditation'
    elif recommendation == 'sleep':
        return 'sleep'

    return json.dumps({
        "meal": str(mealRec),
        "meditationActivity": str(meditationRec),
        "exerciseActivity": str(activityRec)
    })




# local testing

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
            "caloriesBurnt": 100,
            "caloriesGained": 2000,
            "numMeditation": 2,
            "numExercise": 1}}
    ]
}
testMeals = [
    {
        "name": "Bagel & Cream Cheese",
        "food": ["<list of food names>"],
        "dateTime": "2024-03-14 08:05:05"
    },
    {
        "name": "Bagel & Cream Cheese",
        "food": ["<list of food names>"],
        "dateTime": "2024-03-15 08:05:05"
    },
    {
        "name": "Bagel & Cream Cheese",
        "food": ["<list of food names>"],
        "dateTime": "2024-03-16 08:05:05"
    },
    {
        "name": "Bagel & Cream Cheese",
        "food": ["<list of food names>"],
        "dateTime": "2024-03-17 08:05:05"
    },
    {
        "name": "Hamburger",
        "food": ["<list of food names>"],
        "dateTime": "2024-03-17 13:05:05"
    }
]
testMeditations = [{
    "activityName": "<name>",
    "caloriesPerHour": -1,
    "startTime": "2024-03-14 08:05:05",
    "heartRate": -1
}]
testActivities = [
    {
        "activityName": "Jogging",
        "caloriesPerHour": 100,
        "startTime": "2024-03-14 08:05:05",
        "heartRate": -1
    },
    {
        "activityName": "Running",
        "caloriesPerHour": 100,
        "startTime": "2024-03-15 09:05:05",
        "heartRate": -1
    },
    {
        "activityName": "Swimming",
        "caloriesPerHour": 100,
        "startTime": "2024-03-16 14:05:05",
        "heartRate": -1
    },
    {
        "activityName": "Swimming",
        "caloriesPerHour": 100,
        "startTime": "2024-03-17 13:05:05",
        "heartRate": -1
    }
]
testLogs = {"exampleList": [
    {"0": {
        "userLog": {
            "goalLog": goalJson,
            "mealLog": testMeals,
            "meditationLog": testMeditations,
            "exerciseLog": testActivities
        }
    }
    }
]}

# I was seeing the different results to understand
for i in range(1):
    print(recommender(json.dumps(testLogs), json.dumps(goalJson), json.dumps(statusJson)))

