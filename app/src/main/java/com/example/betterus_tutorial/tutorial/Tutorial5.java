package com.example.betterus_tutorial.tutorial;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.MainActivity;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.user.dataObjects.GoalInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tutorial5 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton, previosuButton;
    private EditText caloriesBurntInput, caloriesGainedInput, numMedInput, numExerInput;
    private Boolean continueEnabled;
    private DatabaseReference userRef;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        this.userRef.child("goalInfo").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()){
                    GoalInfo goalInfo = dataSnap.getValue(GoalInfo.class);

                    if(goalInfo.getCaloriesBurnt() > 0){
                        caloriesBurntInput.setText(String.valueOf(goalInfo.getCaloriesBurnt()));
                        caloriesGainedInput.setText(String.valueOf(goalInfo.getCaloriesGained()));
                        numMedInput.setText(String.valueOf(goalInfo.getNumMeditation()));
                        numExerInput.setText(String.valueOf(goalInfo.getNumExercise()));
                        continueButtonChange(true);
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
        this.caloriesBurntInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.caloriesGainedInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.numMedInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.numExerInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE)  checkAndEnableContinue();
                return false;
            }
        });

        this.previosuButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Tutorial4.class);

                userRef.child("tutorialInfo").child("tutorialPage")
                        .setValue(MainActivity.TutorialPage.EXERCISE);
                startActivity(intent);
                finish();
            }
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    Intent intent = new Intent(getApplicationContext(), Tutorial6.class);
                    GoalInfo goalInfo = new GoalInfo(
                            Integer.parseInt(caloriesBurntInput.getText().toString()),
                            Integer.parseInt(caloriesGainedInput.getText().toString()),
                            Integer.parseInt(numMedInput.getText().toString()),
                            Integer.parseInt(numExerInput.getText().toString())
                    );

                    userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(MainActivity.TutorialPage.FINISHED);
                    userRef.child("goalInfo").setValue(goalInfo);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void continueButtonChange(Boolean enable){ // GOOD
        if(enable){
            this.continueButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_soft_enabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this, R.color.white));
            this.continueEnabled = true;
        }
        else{
            this.continueButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_soft_disabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this, R.color.button_disabled_text));
            this.continueEnabled = false;
        }
    }

    private void checkAndEnableContinue(){ // GOOD
        this.continueButtonChange(!(caloriesBurntInput.getText().toString().isEmpty() ||
                caloriesGainedInput.getText().toString().isEmpty() ||
                numMedInput.getText().toString().isEmpty() ||
                numExerInput.getText().toString().isEmpty()
                ));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //  GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial5);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.previosuButton = this.findViewById(R.id.prevButton);
        this.caloriesBurntInput = this.findViewById(R.id.calBurntInput);
        this.caloriesGainedInput = this.findViewById(R.id.calGainedInput);
        this.numMedInput = this.findViewById(R.id.numMedActivitiesInput);
        this.numExerInput = this.findViewById(R.id.numExerciseActivitiesInput);
        this.setupAndLoadInfo();
        this.methodBindDo();

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = this.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.goal).into(gifImage);
    }
}