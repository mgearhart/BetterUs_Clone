package com.example.betterus_tutorial.tutorial;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.pages.MainActivity;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.ui.DialogManager;
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tutorial3 extends AppCompatActivity {
    private Button continueButton, previousButton, act1Button, act2Button, act3Button;
    private DatabaseReference userRef;
    private ActivityHolder meditationInfo;
    private Boolean continueEnabled;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        userRef.child("meditationInfo")
                .addListenerForSingleValueEvent(new ValueEventListener(){ // GOOD
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                meditationInfo = dataSnap.getValue(ActivityHolder.class);
                continueButtonChange(meditationInfo.activitiesFilled());
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
        this.previousButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Tutorial2.class);

                userRef.child("tutorialInfo").child("tutorialPage")
                        .setValue(MainActivity.TutorialPage.SLEEP);
                startActivity(intent);
                finish();
            }
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    Intent intent = new Intent(getApplicationContext(), Tutorial4.class);

                    userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(MainActivity.TutorialPage.EXERCISE);
                    userRef.child("meditationInfo").setValue(meditationInfo);
                    startActivity(intent);
                    finish();
                }
            }
        });

        this.act1Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(Tutorial3.this,
                        meditationInfo, 1, false,
                        ()-> continueButtonChange(meditationInfo.activitiesFilled()), false);
            }
        });

        this.act2Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(Tutorial3.this,
                        meditationInfo, 2, false,
                        ()-> continueButtonChange(meditationInfo.activitiesFilled()), false);
            }
        });

        this.act3Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(Tutorial3.this,
                        meditationInfo, 3, false,
                        ()-> continueButtonChange(meditationInfo.activitiesFilled()), false);
            }
        });
    }

    private void continueButtonChange(Boolean enable){ // GOOD
        if(enable){
            this.continueButton.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.button_soft_enabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this, R.color.white));
            this.continueEnabled = true;
        }
        else{
            this.continueButton.setBackground(ContextCompat.getDrawable(this,
                    R.drawable.button_soft_disabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this,
                    R.color.button_disabled_text));
            this.continueEnabled = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial3);
        this.continueEnabled = false;
        this.continueButton = this.findViewById(R.id.continueButton);
        this.previousButton = this.findViewById(R.id.previousButton);
        this.act1Button = this.findViewById(R.id.activity1Button);
        this.act2Button = this.findViewById(R.id.activity2Button);
        this.act3Button = this.findViewById(R.id.activity3Button);
        this.setupAndLoadInfo();
        this.methodBindDo();

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = this.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.meditation).into(gifImage);
    }
}
