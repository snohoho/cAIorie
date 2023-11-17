package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URL;

public class MealPlanActivity extends AppCompatActivity {
    private Button saveButton;

    SharedPreferences sp;
    String age;
    String height;
    String weight;
    String dietaryRestriction;
    String responseText;
    String parsedText;
    StringBuffer response;
    URL url;
    String apiKey = "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";
    String model = "gpt-3.5-turbo-1106";
    String urlStr = "https://api.openai.com/v1/chat/completions";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);

        sp = getApplicationContext().getSharedPreferences("userInputs",Context.MODE_PRIVATE);
        sp.getString("age","");
        sp.getString("height","");
        sp.getString("weight","");
        sp.getString("dietaryRestriction","");

        saveButton = findViewById(R.id.buttonSavePlan);
        final Button generateButton = (Button) findViewById(R.id.buttonGeneratePlan);
        String initialPrompt = "I am " + age +
                " years old, and have a height of " + height +
                " , and currently weigh " + weight +
                ". I cannot eat " + dietaryRestriction +
                ". Find a meal plan for the week containing 3 meals per day, Monday through Sunday.";

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Generating meal plan...",Toast.LENGTH_LONG).show();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Saves the AI generated meal plan.",Toast.LENGTH_LONG).show();
            }
        });
    }
}