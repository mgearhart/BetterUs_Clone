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
import com.example.betterus_tutorial.MainActivity;
import com.example.betterus_tutorial.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    // --- VARIABLES --- \\
    private TextInputEditText emailInput, passwordInput;
    private Button login;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progBar;
    private TextView signUp;

    // ---- METHODS ---- \\
    public void onStart() { // GOOD
        super.onStart();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void methodBindDo(){ // GOOD
        this.login.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                String password = passwordInput.getText().toString();
                String email = emailInput.getText().toString();
                progBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "Empty email!", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.GONE);
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Empty password!", Toast.LENGTH_SHORT).show();
                    progBar.setVisibility(View.GONE);
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() { // GOOD
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
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

        this.signUp.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                progBar.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(), Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    protected void onCreate(Bundle savedInstanceState) { // GOOD
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);
        this.emailInput = this.findViewById(R.id.emailInput);
        this.passwordInput = this.findViewById(R.id.passwordInput);
        this.login = this.findViewById(R.id.loginButton);
        this.progBar = this.findViewById(R.id.progressBar);
        this.signUp = this.findViewById(R.id.signUpNowTextButton);
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.methodBindDo();
    }
}