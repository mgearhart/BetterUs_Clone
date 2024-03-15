package com.example.betterus_tutorial.user.dataObjects;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

/*
    This stores all the activities (meditation and exercise) that the user has made from tutorial

    @Deprecated THIS CLASS HAS SOME ERRORS AND SHOULD NOT BE USED; CONSIDER GETTING ACTIVITIES VIA
    FIREBASE FUNCTIONS!
 */

@Deprecated
public class ActivitiesInfo {
    public enum Activity{MEDITATION, EXERCISE};

    private static ActivitiesInfo instance;
    private ActivityHolder meditationActivities, exerciseActivities;

    // Sets the activities for both activity types by getting info from user's Firebase db
    private ActivitiesInfo(){ // FIX
        FirebaseDatabase fireDB = FirebaseDatabase.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        DatabaseReference userRef = fireDB.getReference("users").child(firebaseUser.getUid());

        userRef.child("meditationInfo")
                .addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot data){
                meditationActivities = data.getValue(ActivityHolder.class);
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });

        userRef.child("exerciseInfo")
                .addListenerForSingleValueEvent(new ValueEventListener(){
            public void onDataChange(@NonNull DataSnapshot data){
                exerciseActivities = data.getValue(ActivityHolder.class);
            }

            public void onCancelled(@NonNull DatabaseError dbError){}
        });
    }

    // For singleton functionality
    public static ActivitiesInfo getInstance(){ // GOOD
        if(instance == null) instance = new ActivitiesInfo();
        return instance;
    }

    // Get all the activities for the corresponding activity type (EXERCISE or MEDITATION)
    public ActivityHolder getActivities(Activity actType){ // GOOD
        if(actType == Activity.MEDITATION) return this.meditationActivities;
        return this.exerciseActivities;
    }

    public ActivityInfo getActivity(Activity actType, int actNum){ // GOOD
        if(actType == Activity.MEDITATION) return this.meditationActivities.getActivity(actNum);
        return this.exerciseActivities.getActivity(actNum);
    }
}
