package com.example.betterus_tutorial.user.settings;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import com.example.betterus_tutorial.R;

public class Settings extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    Button exerciseButton, meditationButton, goalsButton, healthButton, sleepButton;
    ImageView returnButton;

    // ---- METHODS ---- \\
    private void methodBindDo(){ // WIP
        exerciseButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), ExerciseSettings.class));
            }
        });

        meditationButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){

            }
        });

        goalsButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){

            }
        });

        healthButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){

            }
        });

        sleepButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){

            }
        });

        returnButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){finish();}
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_settings);
        this.exerciseButton = this.findViewById(R.id.exerciseButton);
        this.meditationButton = this.findViewById(R.id.meditationButton);
        this.goalsButton = this.findViewById(R.id.goalsButton);
        this.healthButton = this.findViewById(R.id.healthButton);
        this.sleepButton = this.findViewById(R.id.sleepButton);
        this.returnButton = this.findViewById(R.id.returnButton);
        this.methodBindDo();
    }
}
