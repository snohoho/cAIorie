package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.List;
import android.view.ViewGroup.LayoutParams;
public class DaysActivity extends AppCompatActivity {
    private DatabaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days);
        db = new DatabaseHelper(this);


        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
        Button showPopupBtnMonday = (Button) findViewById(R.id.mondayButton);
        Button showPopupBtnTuesday = (Button) findViewById(R.id.tuesdayButton);
        Button showPopupBtnWednesday = (Button) findViewById(R.id.wednesdayButton);
        Button showPopupBtnThursday = (Button) findViewById(R.id.thursdayButton);
        Button showPopupBtnFriday = (Button) findViewById(R.id.fridayButton);
        Button showPopupBtnSaturday = (Button) findViewById(R.id.saturdayButton);
        Button showPopupBtnSunday = (Button) findViewById(R.id.sundayButton);

        showPopupBtnMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Monday's Food");
                List<String> mondayFoodList = db.getAllFood("Monday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Monday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));
                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Tuesday's Food");
                List<String> mondayFoodList = db.getAllFood("Tuesday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Tuesday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnWednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Wednesday's Food");
                List<String> mondayFoodList = db.getAllFood("Wednesday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Wednesday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnThursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Thursday's Food");
                List<String> mondayFoodList = db.getAllFood("Thursday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Thursday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Friday's Food");

                List<String> mondayFoodList = db.getAllFood("Friday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);

                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Friday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnSaturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Saturday's Food");
                List<String> mondayFoodList = db.getAllFood("Saturday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Saturday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });
        showPopupBtnSunday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //instantiate the popup.xml layout file
                LayoutInflater layoutInflater = (LayoutInflater) DaysActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customView = layoutInflater.inflate(R.layout.activity_popup,null);

                Button closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);


                ListView mondayListView = (ListView) customView.findViewById(R.id.dayListView);
                TextView mondayTextView = (TextView) customView.findViewById(R.id.textViewMonday);
                mondayTextView.setText("Sunday's Food");
                List<String> mondayFoodList = db.getAllFood("Sunday");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(DaysActivity.this,android.R.layout.simple_list_item_1, mondayFoodList);
                mondayListView.setAdapter(adapter);
                TextView totalCalories = (TextView) customView.findViewById(R.id.textViewTotalCalories);
                int totalCal = db.getTotalCalories("Sunday");
                totalCalories.setText("Total Calories: " + Integer.toString(totalCal));

                //instantiate popup window
                PopupWindow popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

                //display the popup window
                popupWindow.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);

                //close the popup window on button click
                closePopupBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

            }
        });


    }




}