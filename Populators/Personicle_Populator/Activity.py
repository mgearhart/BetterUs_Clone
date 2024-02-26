import json
from datetime import datetime, timedelta
import random


def generate_activity_log():
    activityJson = open('activities.json', 'r')

    activityDictionary = json.load(activityJson)
    activity_type = [i_activity["name"] for i_activity in activityDictionary["activities"]]
    activityJson.close()

    # Define the start date and time of the activity log
    start_date = datetime(2024, 1, 1, 0, 0, 0)

    # Define the end date and time of the activity log
    end_date = datetime(2024, 1, 31, 23, 59, 59)

    # Define the duration of each activity in minutes




    # Generate the activity log
    activity_log = []
    current_date = start_date
    while current_date <= end_date:
        # Generate a random heart rate between 1 and 10
        duration = random.randrange(1, 6)
        # Generate a random location
        exercise = random.choice(activity_type)
        # Calculate the end time of the activity
        end_time = current_date + timedelta(hours=duration)
        # Create a dictionary for the activity
        activity = {
            "activity_type": exercise,
            "start_time": current_date.strftime("%Y-%m-%d %H:%M:%S"),
            "duration": duration
        }

        # Add the activity to the activity log
        activity_log.append(activity)

        # Move to the next activity
        current_date = end_time + timedelta(hours=random.randrange(5, 72))

    # Save the activity log to a JSON file
    with open("activity_log.json", "w") as f:
        json.dump(activity_log, f, indent=4)


generate_activity_log()
