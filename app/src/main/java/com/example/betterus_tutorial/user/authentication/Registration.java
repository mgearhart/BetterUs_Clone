package com.example.betterus_tutorial.user.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.betterus_tutorial.pages.MainActivity;
import com.example.betterus_tutorial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    // ---- VARIABLES ---- \\
    private TextInputEditText emailInput, passwordInput;
    private Button registerButton;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progBar;
    private TextView loginButton;

    // ---- METHODS ---- \\
    private void methodBindDo(){ // GOOD
        this.loginButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        this.registerButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();
                progBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Empty email!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Empty password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // GOOD
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Account created!", Toast.LENGTH_SHORT).show();
                            // Sign in and go to main page
                            firebaseAuth.signInWithEmailAndPassword(email, password);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_registration);
        this.emailInput = this.findViewById(R.id.emailInput);
        this.passwordInput = this.findViewById(R.id.passwordInput);
        this.registerButton = this.findViewById(R.id.registerButton);
        this.progBar = this.findViewById(R.id.progressBar);
        this.loginButton = this.findViewById(R.id.loginButton);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.methodBindDo();
    }
}