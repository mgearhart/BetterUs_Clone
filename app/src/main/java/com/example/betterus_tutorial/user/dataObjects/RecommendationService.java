package com.example.betterus_tutorial.user.dataObjects;

import android.content.Context;
import androidx.annotation.NonNull;
import com.chaquo.python.PyObject;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.google.gson.Gson;

public class RecommendationService {
    private static RecommendationService instance;
    private DatabaseReference userRef;
    private String userLog, currentGoalStatus, userGoalInfo;

    private RecommendationService(){ // GOOD
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        this.userRef = fireDB.getReference("users").child(firebaseUser.getUid());
    }

    private void getUserLog(){ // GOOD
        this.userRef.child("userLog")
                .addListenerForSingleValueEvent(new ValueEventListener(){ // GOOD
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()) userLog = dataSnap.getValue().toString();
                else userLog = null;
            }

            public void onCancelled(@NonNull DatabaseError dbError){userLog = null;}
        });
    }

    private void getCurrentGoalStatus(){ // GOOD
        this.userRef.child("currentGoalStatus").child("currentStatus")
                .addListenerForSingleValueEvent(new ValueEventListener(){ // GOOD
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()) currentGoalStatus = dataSnap.getValue().toString();
                else currentGoalStatus = null;
            }

            public void onCancelled(@NonNull DatabaseError dbError){currentGoalStatus = null;}
        });
    }

    private void getUserGoalInfo(){ // GOOD
        this.userRef.child("goalInfo")
        .addListenerForSingleValueEvent(new ValueEventListener(){ // GOOD
            public void onDataChange(@NonNull DataSnapshot dataSnap){
                if(dataSnap.exists()) userGoalInfo = dataSnap.getValue().toString();
                else userGoalInfo = null;
            }

            public void onCancelled(@NonNull DatabaseError dbError){userGoalInfo = null;}
        });
    }

    public static RecommendationService getInstance(){ // GOOD
        if(instance == null) instance = new RecommendationService();
        return instance;
    }

    public RecommendationInfo getRecommendation(Context context){ // GOOD
        this.getUserLog();
        this.getUserGoalInfo();
        this.getCurrentGoalStatus();

        if(!((userLog == null) || (currentGoalStatus == null) || (userGoalInfo == null))) {
            Gson gson = new Gson();

            if (!Python.isStarted()) Python.start(new AndroidPlatform(context));

            Python python = Python.getInstance();
            PyObject script = python.getModule("Recommender");
            String jsonString = script.callAttr("recommender", this.userLog,
                            this.currentGoalStatus, this.userGoalInfo).toString();

            return gson.fromJson(jsonString, RecommendationInfo.class);
        }
        else return null;
    }
}
