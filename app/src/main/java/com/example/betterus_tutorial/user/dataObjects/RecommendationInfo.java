package com.example.betterus_tutorial.user.dataObjects;

// Used to store JSON object returned after calling the recommender function
public class RecommendationInfo {
    private String meal, meditationActivity, exerciseActivity;

    public RecommendationInfo(String meal, String medActivity, String exerActivity){
        this.meal = meal;
        this.meditationActivity = medActivity;
        this.exerciseActivity = exerActivity;
    }

    public RecommendationInfo(){}

    public void setMeal(String meal){ // GOOD
        this.meal = meal;
    }

    public void setMeditationActivity(String meditationActivity){ // GOOD
        this.meditationActivity = meditationActivity;
    }

    public void setExerciseActivity(String exerciseActivity){ // GOOD
        this.exerciseActivity = exerciseActivity;
    }

    public String getMeal(){ // GOOD
        return this.meal;
    }

    public String getMeditationActivity(){ // GOOD
        return this.meditationActivity;
    }

    public String getExerciseActivity(){ // GOOD
        return this.exerciseActivity;
    }
}
