package com.example.betterus_tutorial.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.user.dataObjects.FoodInfo;
import com.example.betterus_tutorial.user.dataObjects.FoodsInfo;
import com.example.betterus_tutorial.user.dataObjects.MealInfo;
import com.example.betterus_tutorial.user.dataObjects.MealsInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Food extends Fragment {
    // ---- VARIABLES ---- \\
    private Button submitButton;
    private Spinner mealSelect;
    private TextView ingredientsText, ingredientsTextInfo;
    private MealInfo chosenMeal;
    private Boolean submitEnabled;
    private ArrayList<MealInfo> obtainedMealLog;
    private DatabaseReference userRef;

    // ---- CONSTRUCTOR ---- \\
    public Food() {} // GOOD

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        this.userRef.child("userLog")
                .child("mealLog").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                GenericTypeIndicator<ArrayList<MealInfo>>
                        typeIndicator = new GenericTypeIndicator<ArrayList<MealInfo>>() {};
                obtainedMealLog = dataSnap.getValue(typeIndicator);
            }

            public void onCancelled(@NonNull DatabaseError dbError){obtainedMealLog = null;}
        });
    }

    private void submitButtonChange(Boolean enable, Context context){ // GOOD
        this.submitEnabled = enable;
        this.submitButton.setBackground(ContextCompat.getDrawable(context, enable ?
                R.drawable.button_soft_enabled_2 :
                R.drawable.button_soft_disabled));
        this.submitButton.setTextColor(ContextCompat.getColor(context, enable ?
                R.color.white
                :R.color.button_disabled_text));
    }

    private void changeInfo(boolean show, String ingredientsInfo){ // GODO
        this.ingredientsText.setVisibility(show ? View.VISIBLE: View.GONE);
        this.ingredientsTextInfo.setText(show ? ingredientsInfo : "");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
    }

    private void methodBindDo(Context context){ // GOOD
        this.submitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(submitEnabled){
                    if(obtainedMealLog == null) obtainedMealLog =  new ArrayList<>();

                    MealInfo chosenMealCopy = new MealInfo(chosenMeal);
                    chosenMealCopy.setDateTime((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                            .format(new Date()));

                    obtainedMealLog.add(chosenMealCopy);
                    userRef.child("userLog").child("mealLog")
                            .setValue(obtainedMealLog);
                }
            }
        });

        this.mealSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){ // GOOD
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l){
                if(pos != 0){
                    chosenMeal = MealsInfo.getInstance(context)
                            .getMeal((String) adapterView.getItemAtPosition(pos));

                    String ingredientsStr = "";

                    for(String foodName: chosenMeal.getFoods()){
                        FoodInfo food = FoodsInfo.getInstance(context).getFood(foodName);
                        ingredientsStr +=
                                "- " + food.getName() + ": cal.: " + food.getCalories() + "; pro.:"
                                + food.getProtein() + "; fat: " + food.getFat() + "; carbon: "
                                + food.getCarbohydrates() + "; fiber: " + food.getFiber() + "\n";
                    }

                    changeInfo(true, ingredientsStr);
                    submitButtonChange(true, context);
                    return;
                }

                changeInfo(false, "");
                submitButtonChange(false, context);
            }

            public void onNothingSelected(AdapterView<?> adapterView){}
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // GOOD
        View viewRoot = inflater.inflate(R.layout.fragment_food, container, false);
        this.submitButton = viewRoot.findViewById(R.id.submitButton);
        this.ingredientsText = viewRoot.findViewById(R.id.ingredients);
        this.ingredientsTextInfo = viewRoot.findViewById(R.id.ingredientsText);
        this.mealSelect = viewRoot.findViewById(R.id.mealSelect);
        this.submitEnabled = false;
        this.setupAndLoadInfo();
        this.methodBindDo(viewRoot.getContext());

        // Meal options setup
        ArrayList<String> mealOptions = new ArrayList<>();
        mealOptions.add("None");

        for(MealInfo meal: MealsInfo.getInstance(viewRoot.getContext()).getMeals().values()){
            mealOptions.add(meal.getName());
        }

        ArrayAdapter<String> mealOptionsArrayAdapter =
                new ArrayAdapter<>(viewRoot.getContext(), R.layout.custom_dropdown_item,
                        R.id.textView1,
                        mealOptions);

        this.mealSelect.setAdapter(mealOptionsArrayAdapter);

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = viewRoot.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.food).into(gifImage);

        return viewRoot;
    }
}