import json
from datetime import datetime, timedelta
import random


def generate_HR_logs():
    entry_count = 10
    HR_logs = []
    for i in range(entry_count):
        heartRate = random.randrange(50, 180)
        year = 2024
        month = random.randrange(1, 13)
        day = random.randrange(1, 29)
        hour = random.randrange(1, 24)
        minute = random.randrange(1, 60)
        second = random.randrange(1, 60)
        log_time = datetime(year, month, day, hour, minute, second)

        # creating a dictionary for the heart rate log

        single_HR_log = {

            "heart_rate": heartRate,
            "start_time": log_time.strftime("%Y-%m-%d %H:%M:%S")

        }

        # adding the log to the list
        HR_logs.append(single_HR_log)

    # outputting to json file
    with open("Personicle_Populator\heart_rate_log.json", "w") as f:
        json.dump(HR_logs, f, indent=4)

generate_HR_logs()
