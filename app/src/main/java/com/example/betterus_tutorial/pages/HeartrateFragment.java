package com.example.betterus_tutorial.pages;

import android.os.Bundle;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.DialogFragment;
import androidx.annotation.Nullable;
import android.view.Gravity;
import androidx.annotation.NonNull;

import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.databinding.FragmentHeartrateBinding;
import androidx.navigation.fragment.NavHostFragment;
import android.widget.TextView;
import android.widget.ImageView;
import java.util.Random;



public class HeartrateFragment extends DialogFragment {

    private FragmentHeartrateBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHeartrateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to another fragment when the button is clicked

                Random random = new Random(); //Have heartrate set to Random for now
                int heartRate = random.nextInt(161) + 40;

                if(heartRate <= 150) { //GOOD HEART RATE -> delete old text, then update with new text
                    TextView heartRateCheck = view.findViewById(R.id.heartRateCheck);
                    heartRateCheck.setVisibility(View.GONE);//delete previous message
                    ImageView heart = view.findViewById(R.id.heart);
                    heart.setVisibility(View.GONE);//delete heart image

                    TextView heartRateResult = view.findViewById(R.id.heartRateResult);
                    heartRateResult.setText("Heart Rate: " + heartRate + " bpm");
                    heartRateResult.setVisibility(View.VISIBLE);

                    TextView goodHeartRate = view.findViewById(R.id.goodHeartRate);
                    goodHeartRate.setVisibility(View.VISIBLE);

                    Button firstButton = view.findViewById(R.id.continueButton);
                    firstButton.setVisibility(View.INVISIBLE);

                    Button secondButton = view.findViewById(R.id.continueButton2);
                    secondButton.setVisibility(View.VISIBLE);

                    secondButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Dismiss the dialog
                            dismiss();
                        }
                    });


                }else{//BAD HEART RATE
                    TextView heartRateCheck = view.findViewById(R.id.heartRateCheck);
                    heartRateCheck.setVisibility(View.GONE);//delete previous message
                    ImageView heart = view.findViewById(R.id.heart);
                    heart.setVisibility(View.GONE);//delete heart image

                    TextView heartRateResult = view.findViewById(R.id.heartRateResult);
                    heartRateResult.setText("Heart Rate: " + heartRate + " bpm");
                    heartRateResult.setVisibility(View.VISIBLE);

                    TextView badHeartRate = view.findViewById(R.id.badHeartRate);
                    badHeartRate.setVisibility(View.VISIBLE);

                    Button firstButton = view.findViewById(R.id.continueButton);
                    firstButton.setVisibility(View.INVISIBLE);

                    Button secondButton = view.findViewById(R.id.continueButton2);
                    secondButton.setVisibility(View.VISIBLE);

                    secondButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Dismiss the dialog
                            dismiss();
                        }
                    });
                }

                // Dismiss the dialog after navigation
                //dismiss();
            }
        });

        // Customize your popup here
        getDialog().getWindow().setLayout(800, 800);
        getDialog().getWindow().setGravity(Gravity.CENTER);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
