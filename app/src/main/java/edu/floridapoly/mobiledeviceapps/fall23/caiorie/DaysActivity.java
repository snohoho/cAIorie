package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DaysActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        db = new DatabaseHelper(this);
        ListView mondayListView = (ListView) findViewById(R.id.mondayMealPlan);
        List<String> mondayFoodList = db.getMondayFood();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mondayFoodList);
        mondayListView.setAdapter(adapter);

    }




}