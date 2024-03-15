package com.example.betterus_tutorial.tutorial;

import androidx.annotation.NonNull;
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
import com.example.betterus_tutorial.pages.MainActivity;
import com.example.betterus_tutorial.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import com.example.betterus_tutorial.user.dataObjects.HealthInfo;

public class Tutorial1 extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private Button continueButton;
    private Spinner sexSelect;
    private EditText ageInput, heightInput, weightInput;
    private Boolean continueEnabled;
    private DatabaseReference userRef;

    // ---- METHODS ---- \\
    private void setupAndLoadInfo(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        this.userRef.child("healthInfo").addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()){
                    HealthInfo healthInfo = dataSnap.getValue(HealthInfo.class);

                    if(healthInfo.getAge() > 0){
                        ageInput.setText(String.valueOf(healthInfo.getAge()));
                        sexSelect.setSelection(healthInfo.getSex().ordinal());
                        heightInput.setText(String.valueOf(healthInfo.getHeight()));
                        weightInput.setText(String.valueOf(healthInfo.getWeight()));
                        continueButtonChange(true);
                    }
                }
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
        this.ageInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.heightInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.weightInput.setOnEditorActionListener(new TextView.OnEditorActionListener(){ // GOOD
            public boolean onEditorAction(TextView textView, int actionID, KeyEvent keyEvent){
                if(actionID == EditorInfo.IME_ACTION_DONE) checkAndEnableContinue();
                return false;
            }
        });

        this.sexSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){ // GOOD
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l){
                checkAndEnableContinue();
            }

            public void onNothingSelected(AdapterView<?> adapterView){}
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    HealthInfo healthInfo = new HealthInfo((HealthInfo.BioSex) HealthInfo.BioSex.values()[sexSelect.getSelectedItemPosition()],
                            Float.parseFloat(heightInput.getText().toString()), Float.parseFloat(weightInput.getText().toString()),
                            Integer.parseInt(ageInput.getText().toString()));
                    Intent intent = new Intent(getApplicationContext(), Tutorial2.class); // Change to next page!

                    userRef.child("tutorialInfo").child("tutorialPage")
                            .setValue(MainActivity.TutorialPage.SLEEP);
                    userRef.child("healthInfo").setValue(healthInfo);
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
            this.continueEnabled = true;
        }
        else{
            this.continueButton.setBackground(ContextCompat.getDrawable(this, R.drawable.button_soft_disabled));
            this.continueButton.setTextColor(ContextCompat.getColor(this, R.color.button_disabled_text));
            this.continueEnabled = false;
        }
    }

    private void checkAndEnableContinue(){ // GOOD
        this.continueButtonChange(!(ageInput.getText().toString().isEmpty() || heightInput.getText().toString().isEmpty() ||
                weightInput.getText().toString().isEmpty() || (sexSelect.getSelectedItemPosition() == 0)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_tutorial1);
        this.continueButton = this.findViewById(R.id.continueButton);
        this.sexSelect = this.findViewById(R.id.sexInput);
        this.ageInput = this.findViewById(R.id.ageInput);
        this.heightInput = this.findViewById(R.id.heightInput);
        this.weightInput = this.findViewById(R.id.weightInput);
        this.setupAndLoadInfo();
        this.methodBindDo();

        // Sex options setup
        ArrayList<String> genderOptions = new ArrayList<>();
        genderOptions.add("None");
        genderOptions.add("Female");
        genderOptions.add("Male");

        ArrayAdapter<String> genderOptionsArrayAdapter =
                new ArrayAdapter<>(this, R.layout.custom_dropdown_item, R.id.textView1, genderOptions);

        this.sexSelect.setAdapter(genderOptionsArrayAdapter);

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = this.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.medicine).into(gifImage);
    }
}