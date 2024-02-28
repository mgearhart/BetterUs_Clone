package com.example.betterus_tutorial.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.betterus_tutorial.R;
import com.example.betterus_tutorial.method.MethodArg;
import com.example.betterus_tutorial.user.dataObjects.ActivityHolder;
import com.example.betterus_tutorial.user.dataObjects.ActivityInfo;
import com.example.betterus_tutorial.user.dataObjects.GoalInfo;
import com.example.betterus_tutorial.user.dataObjects.TimeInfo;
import java.util.ArrayList;

public class DialogManager {
    private static DialogManager instance;

    private DialogManager(){}

    // For singleton functionality
    public static DialogManager getInstance(){ // GOOD
        if(instance == null) instance = new DialogManager();
        return instance;
    }

    public void createActivityDialog(Context context, ActivityHolder actHolder, int actNum,
                                     boolean caloriesInput, MethodArg continueCheck){ // GOOD
        EditText activityNameInput, activityTimeInput, duration, calPerHourInput;
        Button submit, cancel;
        TextView titleText;
        Spinner activityTimeAMPM;
        Dialog activityDialog = new Dialog(context);
        ActivityInfo activity = actHolder.getActivity("activity" + actNum);
        ArrayList<String> activityAmPmOptions = new ArrayList<>();
        ArrayAdapter<String> activityAmPmAdapter =
                new ArrayAdapter<>(context, R.layout.custom_dropdown_item, R.id.textView1, activityAmPmOptions);

        if(caloriesInput){ // If the activity holder is for exercises
            activityDialog.setContentView(R.layout.activity_edit2_activity);
            calPerHourInput = activityDialog.findViewById(R.id.calPerHourInput);
        }
        else {
            activityDialog.setContentView(R.layout.activity_edit_activity);
            calPerHourInput = null; // Just remove the error message
        }

        activityDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        activityDialog.setCancelable(false);
        activityDialog.show();
        activityAmPmOptions.add("AM");
        activityAmPmOptions.add("PM");

        submit = activityDialog.findViewById(R.id.submitButton);
        cancel = activityDialog.findViewById(R.id.cancelButton);
        titleText = activityDialog.findViewById(R.id.activityTitle);
        activityNameInput = activityDialog.findViewById(R.id.activityNameInput);
        activityTimeInput = activityDialog.findViewById(R.id.activityTimeInput);
        activityTimeAMPM = activityDialog.findViewById(R.id.activityAmPm);
        duration = activityDialog.findViewById(R.id.durationInput);
        activityTimeAMPM.setAdapter(activityAmPmAdapter);
        titleText.setText(context.getString(R.string.activityTitle, actNum));

        if(!activity.getActivityName().equals("")){ // Executes if there is existing user data
            activityNameInput.setText(activity.getActivityName());
            activityTimeInput.setText(String.valueOf(activity.getActivityTime().getTime()));
            duration.setText(String.valueOf(activity.getGoalInfo().getTotalDays()));
            activityTimeAMPM.setSelection(activity.getActivityTime().getAmPm().ordinal()-1);

            if(caloriesInput && (activity.getCalPerHour() > 0)) // If the activity holder is for exercises
                calPerHourInput.setText(String.valueOf(activity.getCalPerHour()));
        }

        submit.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                String actName     = activityNameInput.getText().toString();
                String actTime     = activityTimeInput.getText().toString();
                String actDuration = duration.getText().toString();

                if(!(actName.isEmpty() || actTime.isEmpty() || actDuration.isEmpty())){
                    int calPerHour = -1;

                    if(caloriesInput){
                        String calPerHourStr = calPerHourInput.getText().toString();

                        if(!calPerHourStr.isEmpty()) calPerHour = Integer.parseInt(calPerHourStr);
                        else return;
                    }

                    TimeInfo actTimeInfo = new TimeInfo(TimeInfo.AmPm.values()[activityTimeAMPM.getSelectedItemPosition() + 1],
                            Integer.parseInt(actTime));
                    GoalInfo goalInfo = new GoalInfo(0, Integer.parseInt(actDuration));

                    actHolder.setActivity("activity" + actNum, new ActivityInfo(actName, actTimeInfo, goalInfo, calPerHour));
                    continueCheck.execute();
                    activityDialog.dismiss(); // Simply closes the dialog
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener(){ // GOOD
            public void onClick(View v){
                activityDialog.dismiss(); // Simply closes the dialog
            }
        });
    }

    public void createHeartRateDialog(){ // WIP

    }

    public void createMessageDialog(){ // WIP

    }
}
