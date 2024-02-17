package com.example.betterus_tutorial;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button tutorialButton;
    private enum tutorialPage{
        WELCOME,
        HEALTH,
        MENTAL_HEALTH,
        SLEEP,
        MEDITATION,
        EXERCISE,
        FOOD
    };
    private Button logoutButton;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase fireDB;
    private DatabaseReference userRef;
    private FirebaseUser firebaseUser;
    private String userID;

    // ---- METHODS ---- \\
    private void checkLogin(){ // GOOD
        // -- Getting all Firebase resources -- \\
        this.fireDB = FirebaseDatabase.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){ // User is not logged in, move to login page!
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{ // User is logged in, load their data!
            this.userID = this.firebaseUser.getUid();
            this.userRef = this.fireDB.getReference("users").child(this.userID);
        }
    }

    private void checkAndLoadData(){ // GOOD
        this.userRef.addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot data){
                if(!data.exists()){
                    // -- Creating user nodes -- \\
                    // -- Tutorial info -- \\
                    MainActivity.this.userRef.child("tutorialInfo").child("finishedTutorial").setValue(false);
                    MainActivity.this.userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(tutorialPage.WELCOME);

                    // -- Health info -- \\
                    MainActivity.this.userRef.child("healthInfo").child("weight");
                    MainActivity.this.userRef.child("healthInfo").child("height");
                    MainActivity.this.userRef.child("healthInfo").child("age");
                    MainActivity.this.userRef.child("healthInfo").child("gender");

                    // -- Sleep info -- \\
                    MainActivity.this.userRef.child("sleepInfo").child("wakeUpTime").child("time");
                    MainActivity.this.userRef.child("sleepInfo").child("wakeUpTime").child("amPm");
                    MainActivity.this.userRef.child("sleepInfo").child("sleepTime").child("time");
                    MainActivity.this.userRef.child("sleepInfo").child("sleepTIme").child("amPm");

                    // -- Meditation info -- \\
                    // -- Activity 1 -- \\
                    MainActivity.this.userRef.child("meditationInfo").child("activity1").child("name");
                    MainActivity.this.userRef.child("meditationInfo").child("activity1")
                            .child("schedTime").child("time");
                    MainActivity.this.userRef.child("meditationInfo").child("activity1")
                            .child("schedTime").child("amPm");
                    MainActivity.this.userRef.child("meditationInfo").child("activity1")
                            .child("goal").child("currentDays");
                    MainActivity.this.userRef.child("meditationInfo").child("activity1")
                            .child("goal").child("totalDays");

                    // -- Activity 2 -- \\
                    MainActivity.this.userRef.child("meditationInfo").child("activity2").child("name");
                    MainActivity.this.userRef.child("meditationInfo").child("activity2")
                            .child("schedTime").child("time");
                    MainActivity.this.userRef.child("meditationInfo").child("activity2")
                            .child("schedTime").child("amPm");
                    MainActivity.this.userRef.child("meditationInfo").child("activity2")
                            .child("goal").child("currentDays");
                    MainActivity.this.userRef.child("meditationInfo").child("activity2")
                            .child("goal").child("totalDays");

                    // -- Activity 3 -- \\
                    MainActivity.this.userRef.child("meditationInfo").child("activity3").child("name");
                    MainActivity.this.userRef.child("meditationInfo").child("activity3")
                            .child("schedTime").child("time");
                    MainActivity.this.userRef.child("meditationInfo").child("activity3")
                            .child("schedTime").child("amPm");
                    MainActivity.this.userRef.child("meditationInfo").child("activity3")
                            .child("goal").child("currentDays");
                    MainActivity.this.userRef.child("meditationInfo").child("activity3")
                            .child("goal").child("totalDays");

                    // -- Exercise info -- \\
                    // -- Activity 1 -- \\
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1").child("name");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("schedTime").child("time");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("schedTime").child("amPm");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("goal").child("currentDays");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("goal").child("totalDays");

                    // -- Activity 2 -- \\
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2").child("name");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("schedTime").child("time");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("schedTime").child("amPm");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("goal").child("currentDays");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("goal").child("totalDays");

                    // -- Food info -- \\
                    MainActivity.this.userRef.child("foodInfo"); // Not sure what to put here .~.
                }
            }

            public void onCancelled(@NonNull DatabaseError dbError){
                Log.e(TAG, "Something happened while attempting to load user data!");
            }
        });
    }

    private void methodBindDo(){ // GOOD
        this.logoutButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        this.tutorialButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), tutorial_1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkUserTutorial(){ // WIP
//        if(checkIfNotFinishedTutorial){
//            switch(getTutorialPage){
//                case tutorialPage.WELCOME:
//                    goToWelcome;
//                    break;
//                case tutorialPage.HEALTH:
//                    goToHealth;
//                    break;
//                case tutorialPage.MENTAL_HEALTH:
//                    goToMentalHealth;
//                    break;
//                case tutorialPage.SLEEP:
//                    goToSleep;
//                    break;
//                case tutorialPage.MEDITATION:
//                    goToMeditation;
//                    break;
//                case tutorialPage.EXERCISE:
//                    goToExercise;
//                    break;
//                case tutorialPage.FOOD:
//                    goToFood;
//                    break;
//                default:
//                    Log.e(TAG, "Something went wrong while trying to go a tutorial page!");
//                    break;
//            }
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // WIP
        // -- Initializations -- \\
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.tutorialButton = this.findViewById(R.id.buttonToTutorial);
        this.logoutButton = this.findViewById(R.id.logoutButton);
        this.checkLogin();
        this.methodBindDo();
        this.checkAndLoadData();
        this.checkUserTutorial();


    }
}