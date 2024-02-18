package com.example.betterus_tutorial;

import static android.content.ContentValues.TAG;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import user.dataObjects.HealthInfo;

public class Tutorial_1 extends AppCompatActivity {
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

                    if(healthInfo != null){
                        if(healthInfo.getAge() > 0) ageInput.setText(healthInfo.getAge());
                        if(healthInfo.getSex() != 0) sexSelect.setSelection(healthInfo.getSex());
                        if(healthInfo.getHeight() > 0) heightInput.setText(String.valueOf(healthInfo.getHeight()));
                        if(healthInfo.getWeight() > 0) weightInput.setText(String.valueOf(healthInfo.getWeight()));

                        checkAndEnableContinue();
                    }
                    else Log.e(TAG, "Something went wrong while attempting to grab user healthInfo");
                }
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    private void methodBindDo(){ // GOOD
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
                checkAndEnableContinue();
            }

            public void onNothingSelected(AdapterView<?> adapterView){}
        });

        this.continueButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                if(continueEnabled){
                    HealthInfo healthInfo = new HealthInfo(sexSelect.getSelectedItemPosition(),
                            Float.parseFloat(heightInput.getText().toString()), Float.parseFloat(weightInput.getText().toString()),
                            Integer.parseInt(ageInput.getText().toString()));
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class); // Change to next page!

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
        else if(sexSelect.getSelectedItemPosition() != 0){ // 0 = None
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