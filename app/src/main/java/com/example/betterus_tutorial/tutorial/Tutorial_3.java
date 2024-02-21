package com.example.betterus_tutorial.tutorial;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.MainActivity;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.user.dataObjects.ActivityInfo;
import com.example.betterus_tutorial.user.dataObjects.TimeInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.betterus_tutorial.user.dataObjects.MeditationInfo;

public class Tutorial_3 extends AppCompatActivity {
    private Button continueButton, previousButton, act1Button, act2Button, act3Button;
    private DatabaseReference userRef;
    private MeditationInfo meditationInfo;
    private Boolean continueEnabled;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        checkAndEnableContinue();
    }

    private void createEditActivityDialog(int activity){ // [Almost done]
        Dialog activityDialog = new Dialog(getApplicationContext());
        Button submit, cancel;
        TextView titleText;
        EditText activityNameInput, activityTimeInput;
        Spinner activityTimeAMPM;

        activityDialog.setContentView(R.layout.activity_edit_activity);
        activityDialog.setCancelable(false);

        submit = this.findViewById(R.id.submitButton);
        cancel = this.findViewById(R.id.cancelButton);
        titleText = this.findViewById(R.id.activityTitle);
        activityNameInput = this.findViewById(R.id.activityNameInput);
        activityTimeInput = this.findViewById(R.id.activityTimeInput);
        activityTimeAMPM = this.findViewById(R.id.activityAmPm);

        // Add the spinner setup thing here!

        /*
            TODO:
            ~Add the spinner
            ~Check to see if the all array entries are non-null to enable continue button!
            ~Save info to Firebase database!!
         */

        titleText.setText(this.getString(R.string.activityTitle, activity));

        submit.setOnClickListener(new View.OnClickListener(){ // GOOD
           public void onClick(View v){
               if(!activityNameInput.getText().toString().isEmpty() && !activityTimeInput.getText().toString().isEmpty()){
                   String activityName = activityNameInput.getText().toString();
                   TimeInfo actTimeInfo = new TimeInfo(TimeInfo.AmPm.values()[activityTimeAMPM.getSelectedItemPosition()],
                           Integer.parseInt(activityTimeInput.getText().toString()));
                   meditationInfo.setActivity(new ActivityInfo(activityName, actTimeInfo), activity);
               }
           }
        });

        cancel.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                activityDialog.dismiss();
            }
        });
    }

    private void methodBindDo(){ // GOOD
        this.previousButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Tutorial_2.class);

                userRef.child("tutorialInfo").child("tutorialPage")
                        .setValue(MainActivity.TutorialPage.SLEEP);
                startActivity(intent);
                finish();
            }
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    Intent intent = new Intent(getApplicationContext(), Tutorial_4.class);

                    userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(MainActivity.TutorialPage.EXERCISE);
                    userRef.child("sleepInfo").setValue(meditationInfo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        this.act1Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                createEditActivityDialog(1);
            }
        });

        this.act2Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                createEditActivityDialog(2);
            }
        });

        this.act3Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                createEditActivityDialog(3);
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
//        if(wakeUpTimeInput.getText().toString().isEmpty() || sleepTimeInput.getText().toString().isEmpty()){
//            this.continueButtonChange(false);
//            this.continueEnabled = false;
//        }
//        else{
//            int wakeUpTime = Integer.parseInt(wakeUpTimeInput.getText().toString());
//            int sleepTime = Integer.parseInt(sleepTimeInput.getText().toString());
//
//            if((wakeUpTime > 12) || (sleepTime > 12)){
//                this.continueButtonChange(false);
//                this.continueEnabled = false;
//            }
//            else{
//                this.continueButtonChange(true);
//                this.continueEnabled = true;
//            }
//        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial3);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.previousButton = this.findViewById(R.id.previousButton);
        this.act1Button = this.findViewById(R.id.activity1Button);
        this.act2Button = this.findViewById(R.id.activity2Button);
        this.act3Button = this.findViewById(R.id.activity3Button);
        this.meditationInfo = new MeditationInfo(3);
        this.setupAndLoadInfo();
        this.methodBindDo();

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = this.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.meditation).into(gifImage);
    }
}
