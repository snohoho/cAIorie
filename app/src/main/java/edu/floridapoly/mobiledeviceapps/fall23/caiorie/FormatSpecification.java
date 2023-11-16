package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class FormatSpecification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.format_specification);

        Button myButton = findViewById(R.id.To_MI_ScreenButton);
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start the SecondActivity
                Intent intent = new Intent(FormatSpecification.this, MealInputActivity.class);
                startActivity(intent);
            }
        });
    }
}