import json
from datetime import datetime, timedelta
import random
import re


def generate_meal_log():
    entry_count = 10
    foodsJson = open('foods.json', 'r')

    food = json.load(foodsJson)
    foodsJson.close()
    # getting all food names from  foods.json
    food_names = [food_item["name"] for food_item in food["food"]]

    meal_log = []
    for i in range(entry_count):
        # class datetime.datetime(year, month, day, hour=0, minute=0, second=0, microsecond=0, tz info=None, *, fold=0)

        # not going for accuracy just trying to get minimum viable product
        # not inclusive so max will be 2024
        year = 2024
        month = random.randrange(1, 13)
        day = random.randrange(1, 29)
        hour = random.randrange(1, 24)
        minute = random.randrange(1, 60)
        second = random.randrange(1, 60)
        log_time = datetime(year, month, day, hour, minute, second)

        curr_meal = random.sample(food_names, random.randrange(3, 7))

        random_food_names = [re.sub(r'\([^)]*\)', '', food_item).strip() for food_item in curr_meal]
        random_meal_name= random.sample(random_food_names, 2)

        meals = {
            "name": "".join(" ".join(random_meal_name)),
            "food": curr_meal,
            "start_time": log_time.strftime("%Y-%m-%d %H:%M:%S")
        }

        # Add the activity to the activity log
        meal_log.append(meals)

        # Move to the next activity

    # Save the activity log to a JSON file
    with open("meal_log.json", "w") as f:
        json.dump(meal_log, f, indent=4)


generate_meal_log()
