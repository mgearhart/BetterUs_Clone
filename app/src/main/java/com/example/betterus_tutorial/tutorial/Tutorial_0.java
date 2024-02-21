package com.example.betterus_tutorial.tutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.betterus_tutorial.MainActivity;
import com.example.betterus_tutorial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Tutorial_0 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton;

    // ---- METHODS ---- \\
    private void methodBindDo(){ // GOOD
        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                DatabaseReference userRef = fireDB.getReference("users").child(firebaseUser.getUid());
                Intent intent = new Intent(getApplicationContext(), Tutorial_1.class);

                userRef.child("tutorialInfo").child("tutorialPage").setValue(MainActivity.TutorialPage.HEALTH);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial0);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.methodBindDo();
    }
}