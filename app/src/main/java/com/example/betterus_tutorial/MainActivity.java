package com.example.betterus_tutorial;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import com.example.betterus_tutorial.tutorial.Tutorial0;
import com.example.betterus_tutorial.tutorial.Tutorial1;
import com.example.betterus_tutorial.tutorial.Tutorial2;
import com.example.betterus_tutorial.tutorial.Tutorial3;
import com.example.betterus_tutorial.tutorial.Tutorial4;
import com.example.betterus_tutorial.user.authentication.Login;
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder;
import com.example.betterus_tutorial.user.dataObjects.ActivityInfo;
import com.example.betterus_tutorial.user.dataObjects.GoalInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.betterus_tutorial.user.dataObjects.HealthInfo;
import com.example.betterus_tutorial.user.dataObjects.SleepInfo;
import com.example.betterus_tutorial.user.dataObjects.TimeInfo;
import androidx.navigation.Navigation;
import androidx.navigation.NavController;

public class MainActivity extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    public enum TutorialPage{
        WELCOME,
        HEALTH,
        SLEEP,
        MEDITATION,
        EXERCISE,
        FINISHED
    };
    private Button tutorialButton;
    private Button logoutButton;
    private DatabaseReference userRef;

    // ---- METHODS ---- \\
    private void checkLogin(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser == null){ // User is not logged in, move to login page!
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else // User is logged in, load their data!
            this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());
    }

    private void checkAndLoadData(){ // GOOD
        this.userRef.addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot data){
                if(!data.exists()){ // Sets use's nodes to default values if they're new
                    // -- Creating user nodes -- \\
                    // -- Tutorial info -- \\
                    MainActivity.this.userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(TutorialPage.WELCOME);

                    // -- Health info -- \\
                    HealthInfo healthInfo = new HealthInfo(HealthInfo.BioSex.NONE, -1, -1, -1);
                    MainActivity.this.userRef.child("healthInfo").setValue(healthInfo);

                    // -- Sleep info -- \\
                    TimeInfo wakeUpTime = new TimeInfo(TimeInfo.AmPm.NONE, -1);
                    TimeInfo sleepTime = new TimeInfo(TimeInfo.AmPm.NONE, -1);
                    SleepInfo sleepInfo = new SleepInfo(wakeUpTime, sleepTime);
                    MainActivity.this.userRef.child("sleepInfo").setValue(sleepInfo);

                    // -- Meditation info -- \\
                    ActivityHolder meditationInfo = new ActivityHolder();

                    // Setting user's activities (defaults)
                    for(int i = 0; i < ActivityHolder.NUM_ACTIVITIES; i++){
                        ActivityInfo activity = new ActivityInfo();
                        TimeInfo timeInfo = new TimeInfo(TimeInfo.AmPm.AM, -1);
                        GoalInfo goalInfo = new GoalInfo(-1, -1);

                        activity.setCalPerHour(-1);
                        activity.setActivityName("");
                        activity.setActivityTime(timeInfo);
                        activity.setGoalInfo(goalInfo);
                        meditationInfo.setActivity("activity" + (i+1), activity);
                    }

                    MainActivity.this.userRef.child("meditationInfo").setValue(meditationInfo);

                    // -- Exercise info -- \\
                    // -- Activity 1 -- \\
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1").child("name").setValue("none");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("schedTime").child("time").setValue(-1);
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("schedTime").child("amPm").setValue("none");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("goal").child("currentDays").setValue(-1);
                    MainActivity.this.userRef.child("exerciseInfo").child("activity1")
                            .child("goal").child("totalDays").setValue(-1);

                    // -- Activity 2 -- \\
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2").child("name").setValue("none");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("schedTime").child("time").setValue(-1);
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("schedTime").child("amPm").setValue("none");
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("goal").child("currentDays").setValue(-1);
                    MainActivity.this.userRef.child("exerciseInfo").child("activity2")
                            .child("goal").child("totalDays").setValue(-1);

                    // -- Food info -- \\
                    MainActivity.this.userRef.child("foodInfo").setValue("none"); // Not sure what to put here .~.

                    // Sending user to tutorial page!
                    Intent intent = new Intent(getApplicationContext(), Tutorial0.class);
                    startActivity(intent);
                    finish();
                }
                else checkUserTutorial();
            }

            public void onCancelled(@NonNull DatabaseError dbError){
                Log.e(TAG, "Something happened while attempting to load user data!");
            }
        });
    }

    private void checkUserTutorial(){ // GOOD
        this.userRef.child("tutorialInfo").child("tutorialPage").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()){
                    TutorialPage pageValue = dataSnap.getValue(TutorialPage.class);

                    if(pageValue!= null){
                        if(pageValue != TutorialPage.FINISHED){
                            Intent newIntent = null;

                            switch(pageValue){
                                case WELCOME:
                                    newIntent = new Intent(getApplicationContext(), Tutorial0.class);
                                    break;
                                case HEALTH:
                                    newIntent = new Intent(getApplicationContext(), Tutorial1.class);
                                    break;
                                case SLEEP:
                                    newIntent = new Intent(getApplicationContext(), Tutorial2.class);
                                    break;
                                case MEDITATION:
                                    newIntent = new Intent(getApplicationContext(), Tutorial3.class);
                                    break;
                                case EXERCISE:
                                    newIntent = new Intent(getApplicationContext(), Tutorial4.class);
                                    break;
                            }

                            startActivity(newIntent);
                            finish();
                        }
                    }
                    else Log.e(TAG, "User 'tutorialPage' was found null!");
                }
                else Log.e(TAG, "Did not find user's 'tutorialPage'!");
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.checkLogin();
        this.checkAndLoadData();

        // Move on to fragments
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.FirstFragment); // Navigate to the desired destination fragment
    }
}
