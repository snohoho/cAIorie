package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MealPlanActivity extends AppCompatActivity {
    private Button saveButton;
    String responseText;
    String parsedText;
    String[] parsedDay = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};

    String changedView;
    StringBuffer response;
    URL url;
    String apiKey = "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";
    String model = "gpt-3.5-turbo-1106";
    String urlStr = "https://api.openai.com/v1/chat/completions";
    SharedPreferences sp;
    String age;
    String height;
    String weight;
    String dietaryRestriction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);

        saveButton = findViewById(R.id.buttonSavePlan);
        final Button buttonSendToAI = (Button) findViewById(R.id.buttonGeneratePlan);

        /*
        sp = getApplicationContext().getSharedPreferences("userInputs", Context.MODE_PRIVATE);
        sp.getString("age","");
        sp.getString("height","");
        sp.getString("weight","");
        sp.getString("dietaryRestriction","");
        */

        age = "25";
        height = "5'10";
        weight = "180 lbs";
        dietaryRestriction = "Nuts";

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "Saves the AI generated meal plan.",Toast.LENGTH_LONG).show();
            }
        });
        buttonSendToAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String prompt = "I am " + age +
                        " years old, and have a height of " + height +
                        " , and currently weigh " + weight +
                        ". I cannot eat " + dietaryRestriction +
                        ". Generate a meal plan for the week containing 3 meals per day, Monday through Sunday. Output in JSON format as meal_plan";
                Log.i("WebService", "WebService URL: " + prompt);
                // Use AsyncTask execute Method To Prevent ANR Problem
                new MealPlanActivity.GetServerData().execute(prompt);
            }
        });
    }

    class GetServerData extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData((String) objects[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            TextView textResponse = findViewById(R.id.textViewMonday);
            textResponse.setText(responseText);

            TextView textParsed = findViewById(R.id.textViewMonday);;
            /*
            if(changedView == "Monday") {
                textParsed = findViewById(R.id.textViewMonday);
            }
            else if(changedView == "Tuesday") {
                textParsed = findViewById(R.id.textViewTuesday);
            }
            else if(changedView == "Wednesday") {
                textParsed = findViewById(R.id.textViewWednesday);
            }
            else if(changedView == "Thursday") {
                textParsed = findViewById(R.id.textViewThursday);
            }
            else if(changedView == "Friday") {
                textParsed = findViewById(R.id.textViewThursday);
            }
            else if(changedView == "Saturday") {
                textParsed = findViewById(R.id.textViewSaturday);
            }
            else {
                textParsed = findViewById(R.id.textViewSunday);
            }
             */

            textParsed.setText(parsedText);
        }
    }

    protected String getWebServiceResponseData(String prompt) {

        try {
            url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            //connection.setReadTimeout(5000);
            //connection.setConnectTimeout(5000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // Form the request body
            String requestBody = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(requestBody);
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();

            Log.d("WebService", "Response code: " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Reading response from input Stream
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));
                String output;
                response = new StringBuffer();

                while ((output = br.readLine()) != null) {
                    response.append(output);
                }
                br.close();
                responseText = response.toString();
                Log.i("WebService", responseText);

                parsedText = extractMessageFromJSONResponse(responseText);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String extractMessageFromJSONResponse(String response) {
        String contentStr = "Generating...";
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArrayList = jsonResponse.getJSONArray("choices");
            JSONObject firstItem = jsonArrayList.getJSONObject(0);
            JSONObject message = firstItem.getJSONObject("message");
            String content = message.getString("content");

            JSONObject mealPlan = new JSONObject(content);
            JSONObject planStart = mealPlan.getJSONObject("meal_plan");

            JSONObject day = planStart.getJSONObject("Monday");
            String breakfast = day.getString("breakfast");
            String lunch = day.getString("lunch");
            String dinner = day.getString("dinner");

            contentStr = "Breakfast: " + breakfast + "\n\n" +
                    "Lunch: " + lunch + "\n\n" +
                    "Dinner: " + dinner;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentStr;
    }
}