package com.example.betterus_tutorial.tutorial;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.MainActivity;
import com.example.betterus_tutorial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import com.example.betterus_tutorial.user.dataObjects.SleepInfo;
import com.example.betterus_tutorial.user.dataObjects.TimeInfo;

public class Tutorial_2 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton, previousButton;
    private Spinner wakeupAmPm, sleepTimeAmPm;
    private EditText wakeUpTimeInput, sleepTimeInput;
    private DatabaseReference userRef;
    private Boolean continueEnabled;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        this.userRef.child("sleepInfo").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()){
                    SleepInfo sleepInfo = dataSnap.getValue(SleepInfo.class);

                    if((sleepInfo.getWakeUpTime() != null) && (sleepInfo.getSleepTime() != null)){
                        TimeInfo wakeUpTime = sleepInfo.getWakeUpTime();
                        TimeInfo sleepTime = sleepInfo.getSleepTime();

                        if(wakeUpTime.getTime() > 0) wakeUpTimeInput.setText(String.valueOf(wakeUpTime.getTime()));
                        if(wakeUpTime.getAmPm() != TimeInfo.AmPm.NONE) wakeupAmPm.setSelection(wakeUpTime.getAmPm().ordinal());
                        if(sleepTime.getTime() > 0) sleepTimeInput.setText(String.valueOf(sleepTime.getTime()));
                        if(sleepTime.getAmPm() != TimeInfo.AmPm.NONE) sleepTimeAmPm.setSelection(sleepTime.getAmPm().ordinal());

                        checkAndEnableContinue();
                    }
                    else Log.e(TAG, "Something went wrong while attempting to grab user sleepInfo");
                }
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
        this.previousButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Tutorial_1.class);

                userRef.child("tutorialInfo").child("tutorialPage")
                        .setValue(MainActivity.TutorialPage.HEALTH);
                startActivity(intent);
                finish();
            }
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    Intent intent = new Intent(getApplicationContext(), Tutorial_3.class);
                    TimeInfo wakeUpTime = new TimeInfo();
                    TimeInfo sleepTime = new TimeInfo();
                    SleepInfo sleepInfo = new SleepInfo();

                    wakeUpTime.setTime(Integer.parseInt(wakeUpTimeInput.getText().toString()));
                    wakeUpTime.setAmPm(TimeInfo.AmPm.values()[wakeupAmPm.getSelectedItemPosition()]);

                    sleepTime.setTime(Integer.parseInt(sleepTimeInput.getText().toString()));
                    sleepTime.setAmPm(TimeInfo.AmPm.values()[sleepTimeAmPm.getSelectedItemPosition()]);

                    sleepInfo.setWakeUpTime(wakeUpTime);
                    sleepInfo.setSleepTime(sleepTime);

                    userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(MainActivity.TutorialPage.MEDITATION);
                    userRef.child("sleepInfo").setValue(sleepInfo);
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
        }
        else{
            this.continueButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_soft_disabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this, R.color.button_disabled_text));
        }
    }

    private void checkAndEnableContinue(){ // GOOD
        if(this.wakeUpTimeInput.getText().toString().isEmpty() || this.sleepTimeInput.getText().toString().isEmpty()){
            this.continueButtonChange(false);
            this.continueEnabled = false;
        }
        else{
            int wakeUpTime = Integer.parseInt(this.wakeUpTimeInput.getText().toString());
            int sleepTime = Integer.parseInt(this.sleepTimeInput.getText().toString());

            if((wakeUpTime > 12) || (sleepTime > 12)){
                this.continueButtonChange(false);
                this.continueEnabled = false;
            }
            else{
                this.continueButtonChange(true);
                this.continueEnabled = true;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial2);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.previousButton = this.findViewById(R.id.previousButton);
        this.wakeUpTimeInput = this.findViewById(R.id.wakeUpInput);
        this.sleepTimeInput = this.findViewById(R.id.sleepTimeInput);
        this.wakeupAmPm = this.findViewById(R.id.wakeUpAmPm);
        this.sleepTimeAmPm = this.findViewById(R.id.sleepTimeAmPm);
        this.setupAndLoadInfo();
        this.methodBindDo();

        // Wake up Time AM/PM
        ArrayList<String> wakeUpTimeAmPmOptions = new ArrayList<>();
        wakeUpTimeAmPmOptions.add("AM");
        wakeUpTimeAmPmOptions.add("PM");

        ArrayAdapter<String> wakeUpTimeAmPmAdapter =
                new ArrayAdapter<>(this, R.layout.custom_dropdown_item, R.id.textView1, wakeUpTimeAmPmOptions);

        this.wakeupAmPm.setAdapter(wakeUpTimeAmPmAdapter);

        // Sleep Time AM/PM
        ArrayList<String> sleepTimeAmPmOptions = new ArrayList<>();
        sleepTimeAmPmOptions.add("AM");
        sleepTimeAmPmOptions.add("PM");

        ArrayAdapter<String> sleepTimeAmPmAdapter =
                new ArrayAdapter<>(this, R.layout.custom_dropdown_item, R.id.textView1, sleepTimeAmPmOptions);

        this.sleepTimeAmPm.setAdapter(sleepTimeAmPmAdapter);
        this.sleepTimeAmPm.setSelection(1); // PM by default

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = this.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.sleep).into(gifImage);
    }
}