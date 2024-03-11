package com.example.betterus_tutorial.user.dataObjects;

import java.io.InputStreamReader;
import java.util.HashMap;
import com.example.betterus_tutorial.R;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import android.content.Context;

public class FoodsInfo {
    private static FoodsInfo instance;
    private final HashMap<String, FoodInfo> foods;

    private FoodsInfo(Context context){ // GOOD
        Gson gson = new Gson();
        this.foods = new HashMap<>();

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(context.getResources().
                openRawResource(R.raw.foods)))){
            StringBuilder jsonContent = new StringBuilder();
            String line;
            FoodInfo temp[];

            while((line = reader.readLine()) != null) jsonContent.append(line);
            temp = gson.fromJson(jsonContent.toString(), FoodInfo[].class);

            for(FoodInfo food: temp) this.foods.put(food.getName(), food);
        } catch(JsonIOException | JsonSyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    // For singleton functionality
    public static FoodsInfo getInstance(Context context){ // GOOD
        if(instance == null) instance = new FoodsInfo(context);
        return instance;
    }

    public FoodInfo getFood(String foodName){ // GOOD
        return this.foods.get(foodName);
    }
}
