package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {
    private Button homeButton;
    private Button calcIntakeButton;
    SharedPreferences sp;
    String name;
    TextView nameView;
    String age;
    TextView ageView;
    String height;
    TextView heightView;
    String weight;
    TextView weightView;
    String dietaryRestriction;
    TextView restrictView;
    String weightGoal;
    TextView weightGoalView;
    String calIntake;
    TextView calIntakeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameView = findViewById(R.id.namePlaceholder);
        ageView = findViewById(R.id.birthdayPlaceholder);
        heightView = findViewById(R.id.heightPlaceholder);
        weightView = findViewById(R.id.weightPlaceholder);
        restrictView = findViewById(R.id.restrictionPlaceholder);
        weightGoalView = findViewById(R.id.goalPlaceholder);
        calIntakeView = findViewById(R.id.calIntakePlaceholder);

        homeButton = findViewById(R.id.buttonHome);
        calcIntakeButton = findViewById(R.id.buttonCalcIntake);

        sp = getSharedPreferences("userInputs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if(sp.getInt("pressed",0) == 1) {
            nameView.setText(sp.getString("name",""));
            ageView.setText(sp.getString("age",""));
            heightView.setText(sp.getString("height",""));
            weightView.setText(sp.getString("weight",""));
            restrictView.setText(sp.getString("dietaryRestriction",""));
            weightGoalView.setText(sp.getString("weightGoal",""));
            calIntakeView.setText(sp.getString("calIntake",""));
        }

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = nameView.getText().toString();
                age = ageView.getText().toString();
                height = heightView.getText().toString();
                weight = weightView.getText().toString();
                dietaryRestriction = restrictView.getText().toString();
                weightGoal = weightGoalView.getText().toString();
                calIntake = calIntakeView.getText().toString();

                if (age.equals("") || height.equals("") || weight.equals(""))
                {
                    Toast.makeText(getBaseContext(), "Please add age, height, and weight!", Toast.LENGTH_SHORT).show();
                    return;
                }

                editor.putString("name",name);
                editor.putString("age",age);
                editor.putString("height",height);
                editor.putString("weight",weight);
                editor.putString("dietaryRestriction",dietaryRestriction);
                editor.putString("weightGoal",weightGoal);
                editor.putInt("pressed",1);
                editor.apply();

                Toast.makeText(getBaseContext(), "Profile information saved.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        calcIntakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = sp.getString("weight","");
                height = sp.getString("height","");
                age = sp.getString("age","");
                weightGoal = sp.getString("weightGoal", "");

                if (!age.equals("") && !height.equals("") && !weight.equals("")) {
                    float w, h, h1, a, g;
                    double y;
                    int rounded;

                    w = Float.parseFloat(weight);
                    w *= 0.45359237;

                    String[] hList = height.split("'");
                    h = Float.parseFloat(hList[1]);
                    h1 = Float.parseFloat(hList[0]);
                    h += h1 * 12;
                    h *= 2.54;

                    a = Float.parseFloat(age);

                    y = 66 + (13.7 * w) + (5 * h) + (6.8 * a);
                    rounded = (int) Math.round(y);

                    if(!weightGoal.equals("")) {
                        w = Float.parseFloat(weight);
                        g = Float.parseFloat(weightGoal);

                        if (w > g) {
                            rounded -= 500;
                        }
                        else if (w < g) {
                            rounded += 500;
                        }
                    }

                    calIntake = String.valueOf(rounded);
                }

                else {
                    calIntake = "Not Calculated";
                }

                editor.putString("calIntake",calIntake);
                editor.apply();
            }
        });
    }
}