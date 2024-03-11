package com.example.betterus_tutorial.user.dataObjects;

import com.example.betterus_tutorial.R;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;

// Stores all fixed set of meals
public class MealsInfo {
    private static MealsInfo instance;
    private MealInfo meals [];

    // Converts all meals from JSON files to objects
    private MealsInfo(Context context){ // GOOD
        Gson gson = new Gson();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().
                openRawResource(R.raw.meals)))){
            StringBuilder jsonContent = new StringBuilder();
            String line;

            while((line = reader.readLine()) != null) jsonContent.append(line);
            this.meals = gson.fromJson(jsonContent.toString(), MealInfo[].class);
        } catch(JsonIOException | JsonSyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    // For singleton functionality
    public static  MealsInfo getInstance(Context context){ // GOOD
        if(instance == null) instance = new MealsInfo(context);
        return instance;
    }

    public MealInfo [] getMeals(){return this.meals;} // GOOD
}
