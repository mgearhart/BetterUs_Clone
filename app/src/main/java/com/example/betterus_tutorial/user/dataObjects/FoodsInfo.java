package com.example.betterus_tutorial.user.dataObjects;

import java.util.HashMap;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FoodsInfo {
    private static FoodsInfo instance;
    private HashMap<String, FoodInfo> foods;

    private FoodsInfo(){ // GOOD
        Gson gson = new Gson();

        try(BufferedReader reader = new BufferedReader(new FileReader("json/foods.json"))){
            StringBuilder jsonContent = new StringBuilder();
            String line;
            FoodInfo temp[];

            while((line = reader.readLine()) != null) jsonContent.append(line);
            temp = gson.fromJson(jsonContent.toString(), FoodInfo[].class);

            for(FoodInfo food: temp) this.foods.put(food.getName(), food);
        }catch(JsonIOException | JsonSyntaxException | IOException e){
            e.printStackTrace();
        }
    }

    // For singleton functionality
    public static FoodsInfo getInstance(){ // GOOD
        if(instance == null) instance = new FoodsInfo();
        return instance;
    }

    public FoodInfo getFood(String foodName){ // GOOD
        return this.foods.get(foodName);
    }
}
