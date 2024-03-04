package com.example.betterus_tutorial.user.dataObjects;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// Stores all fixed set of meals
public class MealsInfo {
    private static MealsInfo instance;
    private MealInfo meals [];

    // Converts all meals from JSON files to objects
    private MealsInfo(){
        Gson gson = new Gson();

        try(BufferedReader reader = new BufferedReader(new FileReader("json/meals.json"))){
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) jsonContent.append(line);

            this.meals = gson.fromJson(jsonContent.toString(), MealInfo[].class);

        } catch(JsonIOException | JsonSyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    // For singleton functionality
    public static  MealsInfo getInstance(){
        if(instance == null) instance = new MealsInfo();
        return instance;
    }

    // Returns a meal given the position pos
    public MealInfo getMeal(int pos){
        return this.meals[pos];
    }
}
