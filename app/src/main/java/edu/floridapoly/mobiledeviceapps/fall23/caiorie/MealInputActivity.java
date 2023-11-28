package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MealInputActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealinput);
        Spinner spinner = findViewById(R.id.spinnerDays);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        Spinner spinner2 = findViewById(R.id.spinnerMeals);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.Meals, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setOnItemSelectedListener(this);

        db = new DatabaseHelper(this);
    }

    public void onSaveButtonClicked(View view){
        String day;
        String food;
        int calories, mealNumber = 0;
        try{
            EditText editTextFood = findViewById(R.id.editTextFood);
            food = editTextFood.getText().toString();

            EditText editTextCalories = findViewById(R.id.editTextCalories);
            String caloriesText = editTextCalories.getText().toString();
            calories = Integer.parseInt(caloriesText);

            Spinner daySpinner = (Spinner) findViewById(R.id.spinnerDays);
            day = daySpinner.getSelectedItem().toString();

            Spinner mealSpinner = (Spinner) findViewById(R.id.spinnerMeals);
            String mealText = mealSpinner.getSelectedItem().toString();
            mealNumber = Integer.parseInt(mealText);

            db.insertData(food, calories, day, mealNumber);
            Toast.makeText(this, "Meal Entered.", Toast.LENGTH_SHORT).show();
        }
        catch(RuntimeException e){
            Toast.makeText(this, "You must fill out all fields in order to proceed.", Toast.LENGTH_SHORT).show();
        }

    }

    public void onShowDaysButtonClicked(View view){
        Intent intent = new Intent(this, DaysActivity.class);
        startActivity(intent);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}