package com.example.betterus_tutorial.pages;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.tutorial.Tutorial0;
import com.example.betterus_tutorial.tutorial.Tutorial1;
import com.example.betterus_tutorial.tutorial.Tutorial2;
import com.example.betterus_tutorial.tutorial.Tutorial3;
import com.example.betterus_tutorial.tutorial.Tutorial4;
import com.example.betterus_tutorial.tutorial.Tutorial5;
import com.example.betterus_tutorial.user.authentication.Login;
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder;
import com.example.betterus_tutorial.user.dataObjects.GoalInfo;
import com.example.betterus_tutorial.user.settings.Settings;
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
import androidx.core.content.ContextCompat;
import androidx.core.widget.ImageViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    public enum TutorialPage{WELCOME, HEALTH, SLEEP, MEDITATION, EXERCISE, GOALS, FINISHED};
    private enum ButtonWhich {RECOMMEND, EXERCISE, HOME, FOOD, SETTINGS};
    private FrameLayout recommendButton, exerciseButton, homeButton, foodButton, settingsButton;
    private ImageView recommendImage, exerciseImage, homeImage, foodImage, settingsImage;
    private TextView recommendText, exerciseText, homeText, foodText, settingsText;
    private int PRESSED_COLOR_TINT, UNPRESSED_COLOR_TINT;
    private ButtonWhich prevPageButton;
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

    protected void onResume(){ // GOOD
        super.onResume();
        colorChangeNavDo(prevPageButton);
    }

    private void checkAndLoadData(){ // GOOD
        this.userRef.addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot data){
                if(!data.exists()){ // Sets the user's node profile info to default values if they're new
                    // -- Tutorial info -- \\
                    MainActivity.this.userRef.child("tutorialInfo")
                            .child("tutorialPage")
                            .setValue(TutorialPage.WELCOME);

                    // -- Health info -- \\
                    HealthInfo healthInfo = new HealthInfo(HealthInfo.BioSex.NONE,
                            -1, -1, -1);
                    MainActivity.this.userRef.child("healthInfo").setValue(healthInfo);

                    // -- Sleep info -- \\
                    TimeInfo wakeUpTime = new TimeInfo(TimeInfo.AmPm.NONE, -1);
                    TimeInfo sleepTime = new TimeInfo(TimeInfo.AmPm.NONE, -1);
                    SleepInfo sleepInfo = new SleepInfo(wakeUpTime, sleepTime);
                    MainActivity.this.userRef.child("sleepInfo").setValue(sleepInfo);

                    // -- Meditation info -- \\
                    MainActivity.this.userRef.child("meditationInfo")
                            .setValue(new ActivityHolder());

                    // -- Exercise info -- \\
                    MainActivity.this.userRef.child("exerciseInfo")
                            .setValue(new ActivityHolder());

                    // -- Goal info -- \\
                    MainActivity.this.userRef.child("goalInfo")
                                    .setValue(new GoalInfo(-1, -1,
                                            -1, -1));

                    // -- Meal log -- \\
                    // N/A


                    // -- Heart rate log -- \\
                    // N/A

                    // Sending user to tutorial page!
                    startActivity(new Intent(getApplicationContext(), Tutorial0.class));
                    finish();
                }
                else checkUserTutorial();
            }

            public void onCancelled(@NonNull DatabaseError dbError){
                Log.e(TAG, "Something happened while attempting to load user data!");
            }
        });
    }

    private void replaceFragmentAndChangeColor(Fragment frag, ButtonWhich color){ // GOOD
        FragmentManager fragManager = getSupportFragmentManager();
        fragManager.beginTransaction()
                .replace(R.id.fragmentSection, frag)
                .addToBackStack(null)
                .commit();
        this.colorChangeNavDo(color);
//        this.removeFragBackStack();
    }

    public void colorChangeNavDo(ButtonWhich color){ // GOOD
        ImageViewCompat.setImageTintList(recommendImage,
                ColorStateList.valueOf((color == ButtonWhich.RECOMMEND)
                        ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT));
        ImageViewCompat.setImageTintList(exerciseImage,
                ColorStateList.valueOf((color == ButtonWhich.EXERCISE)
                        ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT));
        ImageViewCompat.setImageTintList(homeImage,
                ColorStateList.valueOf((color == ButtonWhich.HOME)
                        ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT));
        ImageViewCompat.setImageTintList(foodImage,
                ColorStateList.valueOf((color == ButtonWhich.FOOD)
                        ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT));
        ImageViewCompat.setImageTintList(settingsImage,
                ColorStateList.valueOf((color == ButtonWhich.SETTINGS)
                        ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT));

        // Text
        recommendText.setTextColor((color == ButtonWhich.RECOMMEND)
                ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT);
        exerciseText.setTextColor((color == ButtonWhich.EXERCISE)
                ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT);
        homeText.setTextColor((color == ButtonWhich.HOME)
                ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT);
        foodText.setTextColor((color == ButtonWhich.FOOD)
                ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT);
        settingsText.setTextColor((color == ButtonWhich.SETTINGS)
                ? PRESSED_COLOR_TINT:UNPRESSED_COLOR_TINT);
    }

    public void removeFragBackStack(){ // FIX
        FragmentManager fragManager = getSupportFragmentManager();
        if(fragManager.getBackStackEntryCount() > 0) fragManager.popBackStack();
    }

    private void checkUserTutorial(){ // GOOD
        this.userRef.child("tutorialInfo").child("tutorialPage").
                addListenerForSingleValueEvent(new ValueEventListener(){
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
                                case GOALS:
                                    newIntent = new Intent(getApplicationContext(), Tutorial5.class);
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

    private void methodBindDo(){ // GOOD
        this.recommendButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View view){
                replaceFragmentAndChangeColor(new SeventhFragment(),
                        prevPageButton = ButtonWhich.RECOMMEND);
            }
        });

        this.exerciseButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View view){
                replaceFragmentAndChangeColor(new SecondFragment(),
                        prevPageButton = ButtonWhich.EXERCISE);
            }
        });

        this.homeButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View view){
                replaceFragmentAndChangeColor(new FirstFragment(),
                        prevPageButton = ButtonWhich.HOME);
            }
        });

        this.foodButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View view){
                replaceFragmentAndChangeColor(new ThirdFragment(),
                        prevPageButton = ButtonWhich.FOOD);
            }
        });

        this.settingsButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View view){
                colorChangeNavDo(ButtonWhich.SETTINGS);
                startActivity(new Intent(getApplicationContext(), Settings.class));
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        this.PRESSED_COLOR_TINT = ContextCompat
                .getColor(getApplicationContext(), R.color.navBarButtonPressed);
        this.UNPRESSED_COLOR_TINT = ContextCompat
                .getColor(getApplicationContext(), R.color.navBarButtonUnPressed);
        this.setContentView(R.layout.activity_main);
        this.recommendButton = this.findViewById(R.id.recommendationButton);
        this.homeButton = this.findViewById(R.id.homeButton);
        this.exerciseButton = this.findViewById(R.id.exerciseButton);
        this.foodButton = this.findViewById(R.id.foodButton);
        this.settingsButton = this.findViewById(R.id.settingsButton);
        this.recommendImage = this.findViewById(R.id.recoImage);
        this.exerciseImage = this.findViewById(R.id.actImage);
        this.homeImage = this.findViewById(R.id.homeImage);
        this.foodImage = this.findViewById(R.id.foodImage);
        this.settingsImage = this.findViewById(R.id.settingsImage);
        this.recommendText = this.findViewById(R.id.recoText);
        this.exerciseText = this.findViewById(R.id.actText);
        this.homeText = this.findViewById(R.id.homeText);
        this.foodText = this.findViewById(R.id.foodText);
        this.settingsText = this.findViewById(R.id.settingsText);
        this.checkLogin();
        this.checkAndLoadData();
        this.methodBindDo();
        this.replaceFragmentAndChangeColor(new FirstFragment(), ButtonWhich.HOME);
    }
}
