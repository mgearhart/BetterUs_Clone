package com.example.betterus_tutorial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;

public class tutorial_1 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton;
    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    private ImageView gifImage;
    private Spinner sexSelect;
    private EditText ageInput, heightInput, weightInput;
    private String bioSex;
    private Boolean continueEnabled;

    // ---- METHODS ---- \\
    private void methodBindDo(){ // WIP
        this.ageInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE)
                    checkAndEnableContinue();
                return false;
            }
        });

        this.heightInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE)
                    checkAndEnableContinue();
                return false;
            }
        });

        this.weightInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE)
                    checkAndEnableContinue();
                return false;
            }
        });

        this.sexSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){ // GOOD
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l){
                bioSex = adapterView.getItemAtPosition(pos).toString();
                checkAndEnableContinue();
            }

            public void onNothingSelected(AdapterView<?> adapterView){}
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // WIP
            public void onClick(View v){
                if(continueEnabled){
                    // Save to Firebase db
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Change to next page!
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
        if(ageInput.getText().toString().isEmpty()){
            this.continueButtonChange(false);
            this.continueEnabled = false;
        }
        else if(heightInput.getText().toString().isEmpty()){
            this.continueButtonChange(false);
            this.continueEnabled = false;
        }
        else if(weightInput.getText().toString().isEmpty()){
            this.continueButtonChange(false);
            this.continueEnabled = false;
        }
        else if(bioSex.equals("None")){
            this.continueButtonChange(false);
            this.continueEnabled = false;
        }
        else{
            this.continueButtonChange(true);
            this.continueEnabled = true;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // [Almost done]
        // -- Initializations -- \\
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial1);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.gifImage = this.findViewById(R.id.gifImage);
        this.sexSelect = this.findViewById(R.id.sexInput);
        this.ageInput = this.findViewById(R.id.ageInput);
        this.heightInput = this.findViewById(R.id.heightInput);
        this.weightInput = this.findViewById(R.id.weightInput);
        this.methodBindDo();

//        if(CheckFirebaseDBforStuff){
//            // Load stuff into appropriate parts
//        }

//        if(CheckIfAllFilled){
//            // Continue enabled
//        }

        // -- Sex Options Setup -- \\
        ArrayList<String> genderOptions = new ArrayList<>();
        genderOptions.add("None");
        genderOptions.add("Female");
        genderOptions.add("Male");

        ArrayAdapter<String> genderOptionsArrayAdapter =
                new ArrayAdapter<>(this, R.layout.custom_dropdown_item, R.id.textView1, genderOptions);

        this.sexSelect.setAdapter(genderOptionsArrayAdapter);

        Glide.with(this).load(R.drawable.medicine).into(this.gifImage);
    }
}