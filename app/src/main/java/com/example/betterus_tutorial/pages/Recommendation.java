package com.example.betterus_tutorial.pages;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.user.dataObjects.RecommendationInfo;
import com.example.betterus_tutorial.user.dataObjects.RecommendationService;

public class Recommendation extends Fragment {
    // ---- VARIABLES ---- \\
    private Button recommendButton;
    private TextView medText, medRecoText, exerText, exerRecoText, mealText, mealRecoText;

    // ---- CONSTRUCTOR ---- \\
    public Recommendation(){}


    // ---- METHODS ---- \\
    @Override
    public void onCreate(Bundle savedInstanceState) { // WIP
        super.onCreate(savedInstanceState);
    }

    private void methodBindDo(Context context){ // GOOD
        this.recommendButton.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                RecommendationInfo recommendation = RecommendationService
                        .getInstance()
                        .getRecommendation(context);

                if(recommendation != null){
                    String mealTextGet = recommendation.getMeal(),
                            medTextGet = recommendation.getMeditationActivity(),
                            exerTextGet = recommendation.getExerciseActivity();

                    medText.setVisibility(View.VISIBLE);
                    exerText.setVisibility(View.VISIBLE);
                    mealText.setVisibility(View.VISIBLE);
                    medRecoText.setText(medTextGet.equals("") ? "None": medTextGet);
                    exerRecoText.setText(exerTextGet.equals("") ? "None": exerTextGet);
                    mealRecoText.setText(mealTextGet.equals("") ? "None": mealTextGet);
                }
                else Toast.makeText(context,
                        "Recommendation failed: Not enough data!", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) { // GOOD
        View viewRoot = inflater.inflate(R.layout.fragment_recommendation,
                container, false);
        this.recommendButton = viewRoot.findViewById(R.id.recommendButton);
        this.medText = viewRoot.findViewById(R.id.medText);
        this.medRecoText = viewRoot.findViewById(R.id.medRecoText);
        this.exerText = viewRoot.findViewById(R.id.exerText);
        this.exerRecoText = viewRoot.findViewById(R.id.exerRecoText);
        this.mealText = viewRoot.findViewById(R.id.mealText);
        this.mealRecoText = viewRoot.findViewById(R.id.mealRecoText);
        this.methodBindDo(viewRoot.getContext());

        // GIF setup
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView gifImage = viewRoot.findViewById(R.id.gifImage);
        Glide.with(this).load(R.drawable.recommend).into(gifImage);

        return viewRoot;
    }
}