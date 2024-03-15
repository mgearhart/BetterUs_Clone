package com.example.betterus_tutorial.user.settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class ExerciseSettings extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    Button activity1Button, activity2Button, activity3Button;
    ImageView returnButton;
    private DatabaseReference userRef;
    private ActivityHolder exerciseInfo;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        userRef.child("exerciseInfo")
                .addListenerForSingleValueEvent(new ValueEventListener(){ // GOOD
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                exerciseInfo = dataSnap.getValue(ActivityHolder.class);
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
        activity1Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(ExerciseSettings.this,
                        exerciseInfo, 1, true,
                        ()-> {}, true);
            }
        });

        activity2Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(ExerciseSettings.this,
                        exerciseInfo, 2, true,
                        ()-> {}, true);
            }
        });

        activity3Button.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                DialogManager.getInstance().createActivityDialog(ExerciseSettings.this,
                        exerciseInfo, 3, true,
                        ()-> {}, true);
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                finish();
                userRef.child("exerciseInfo").setValue(exerciseInfo);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_exercise_settings);
        this.activity1Button = this.findViewById(R.id.activity1Button);
        this.activity2Button = this.findViewById(R.id.activity2Button);
        this.activity3Button = this.findViewById(R.id.activity3Button);
        this.returnButton = this.findViewById(R.id.returnButton);
        this.setupAndLoadInfo();
        this.methodBindDo();
    }
}