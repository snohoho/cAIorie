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
    private Button logoutButton;
    SharedPreferences sp;
    String age;
    TextView ageView;
    String height;
    TextView heightView;
    String weight;
    TextView weightView;
    String dietaryRestriction;
    TextView restrictView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        /*
        ageView = findViewById(R.id.birthdayPlaceholder);
        age = ageView.getText().toString();
        heightView = findViewById(R.id.heightPlaceholder);
        height = heightView.getText().toString();
        weightView = findViewById(R.id.weightPlaceholder);
        weight = weightView.getText().toString();
        restrictView = findViewById(R.id.restrictionsPlaceholder);
        dietaryRestriction = restrictView.getText().toString();

        sp = getSharedPreferences("userInputs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("age",age);
        editor.putString("height",height);
        editor.putString("weight",weight);
        editor.putString("dietaryRestriction",dietaryRestriction);
        editor.commit();
         */

        homeButton = findViewById(R.id.buttonHome);
        logoutButton = findViewById(R.id.buttonLogout);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Logs user out",Toast.LENGTH_LONG).show();
            }
        });
    }
}