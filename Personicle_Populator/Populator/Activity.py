import json
from datetime import datetime, timedelta
import random


def generate_activity_log():
    # Later we can implement a call to a file with a large list of activities
    activity_type = ["Running", "Walking", "Weight Lifting", "Swimming", "Yoga"]

    # Define the start date and time of the activity log
    start_date = datetime(2022, 1, 1, 0, 0, 0)

    # Define the end date and time of the activity log
    end_date = datetime(2022, 1, 31, 23, 59, 59)

    # Define the duration of each activity in minutes
    duration = 30

    # Define the possible locations for the activities
    locations = ["gym", "park", "home", "office"]

    # Generate the activity log
    activity_log = []
    current_date = start_date
    while current_date <= end_date:
        # Generate a random heart rate between 1 and 10
        heart_rate = random.randint(1, 10)

        # Generate a random location
        location = random.choice(locations)
        exercise = random.choice(activity_type)
        # Calculate the end time of the activity
        end_time = current_date + timedelta(minutes=duration)

        # Create a dictionary for the activity
        activity = {
            "activity_type": exercise,
            "heart_rate": heart_rate,
            "start_time": current_date.strftime("%Y-%m-%d %H:%M:%S"),
            "end_time": end_time.strftime("%Y-%m-%d %H:%M:%S"),
            "duration": duration,
            "location": location
        }

        # Add the activity to the activity log
        activity_log.append(activity)

        # Move to the next activity
        current_date = end_time

    # Save the activity log to a JSON file
    with open("activity_log.json", "w") as f:
        json.dump(activity_log, f, indent=4)


generate_activity_log()
