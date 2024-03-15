package com.example.betterus_tutorial.user.dataObjects;

import com.example.betterus_tutorial.R;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.HashMap;

import android.content.Context;

// Stores all fixed set of meals
public class MealsInfo {
    private static MealsInfo instance;
    private HashMap<String, MealInfo> meals;

    // Converts all meals from JSON files to objects
    private MealsInfo(Context context){ // GOOD
        Gson gson = new Gson();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().
                openRawResource(R.raw.meals)))){
            StringBuilder jsonContent = new StringBuilder();
            MealInfo temp [];
            String line;

            while((line = reader.readLine()) != null) jsonContent.append(line);
            temp = gson.fromJson(jsonContent.toString(), MealInfo[].class);
            for(MealInfo meal: temp) this.meals.put(meal.getName(), meal);
        } catch(JsonIOException | JsonSyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    // For singleton functionality
    public static  MealsInfo getInstance(Context context){ // GOOD
        if(instance == null) instance = new MealsInfo(context);
        return instance;
    }

    public HashMap<String, MealInfo> getMeals(){return this.meals;} // GOOD

    public MealInfo getMeal(String name){ // GOOD
        return this.meals.get(name);
    }
}
