package com.example.betterus_tutorial.user.dataObjects;

// Used to store current user's goal status
public class CurrentGoalStatus extends GoalInfo {
    public CurrentGoalStatus(int caloriesBurnt, int caloriesGained,
                             int numMeditation, int numExercise){
        super(caloriesBurnt, caloriesGained, numMeditation, numExercise);
    }

    public CurrentGoalStatus(GoalInfo goalInfo){
        super(goalInfo.getCaloriesBurnt(),
                goalInfo.getCaloriesGained(),
                goalInfo.getNumMeditation(),
                goalInfo.getNumExercise());
    }

    public CurrentGoalStatus(){}
}
