package com.example.betterus_tutorial.tutorial;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.betterus_tutorial.pages.MainActivity;
import com.example.betterus_tutorial.R;

public class Tutorial6 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton;

    // ---- METHODS ---- \\
    private void methodBindDo(){ // GOOD
        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial6);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.methodBindDo();
    }
}