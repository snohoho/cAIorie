package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MealPlanActivity extends AppCompatActivity {
    String responseText;
    String parsedText;

    StringBuffer response;
    URL url;
    String apiKey = "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";
    String model = "gpt-3.5-turbo-1106";
    String urlStr = "https://api.openai.com/v1/chat/completions";
    SharedPreferences spMealPlanIn;
    SharedPreferences spMealPlanOut;
    String age;
    String height;
    String weight;
    String dietaryRestriction;
    ProgressDialog progressDialog;
    private Button buttonSendToAI;
    private Button buttonHome;
    ScrollView scrollView;
    TextView textViewNoPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mealplan);

        scrollView = findViewById(R.id.scrollView2);
        textViewNoPlan = findViewById(R.id.textViewNoPlan);

        //initializes text views with previous plan if meal plan currently exists
        spMealPlanOut = getApplicationContext().getSharedPreferences("mealPlanOutDay0", Context.MODE_PRIVATE);
        TextView changeText[] =
                {findViewById(R.id.textViewMonday),
                        findViewById(R.id.textViewTuesday),
                        findViewById(R.id.textViewWednesday),
                        findViewById(R.id.textViewThursday),
                        findViewById(R.id.textViewFriday),
                        findViewById(R.id.textViewSaturday),
                        findViewById(R.id.textViewSunday)};
        if(spMealPlanOut.getInt("pressed",0) == 1) {
            textViewNoPlan.setVisibility(View.INVISIBLE);
            scrollView.setVisibility(View.VISIBLE);
            for(int i=0; i<changeText.length; i++) {
                spMealPlanOut = getApplicationContext().getSharedPreferences("mealPlanOutDay" + i, Context.MODE_PRIVATE);
                parsedText = "Breakfast\n" + spMealPlanOut.getString("breakfast","") +
                        "\n\nLunch\n" + spMealPlanOut.getString("lunch","") +
                        "\n\nDinner\n" + spMealPlanOut.getString("dinner","");
                changeText[i].setText(parsedText);
            }
        }
        else {
            textViewNoPlan.setVisibility(View.VISIBLE);
            scrollView.setVisibility(View.INVISIBLE);
        }

        buttonSendToAI = findViewById(R.id.buttonGeneratePlan);
        spMealPlanIn = getApplicationContext().getSharedPreferences("userInputs", Context.MODE_PRIVATE);
        age = spMealPlanIn.getString("age","25");
        height = spMealPlanIn.getString("height","5'10");
        weight = spMealPlanIn.getString("weight","180");
        dietaryRestriction = spMealPlanIn.getString("dietaryRestriction","");

        buttonSendToAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String prompt = "I am " + age +
                        " years old, and have a height of " + height +
                        " , and currently weigh " + weight + " lbs. ";
                        if(dietaryRestriction != "") {
                            prompt += "I cannot eat " + dietaryRestriction + ". ";
                        }
                        prompt += "Generate a meal plan for the week containing 3 meals per day, Monday through Sunday. Output in JSON format as meal_plan";
                Log.i("WebService", "WebService URL: " + prompt);
                // Use AsyncTask execute Method To Prevent ANR Problem
                new MealPlanActivity.GetServerData().execute(prompt);
            }
        });

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MealPlanActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }

    class GetServerData extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(MealPlanActivity.this);
            if(spMealPlanIn.getInt("pressed",0) == 0) {
                progressDialog.setMessage("Generating meal plan...\n\n(Note: no data was entered in profile screen, default data will be used!)");
            }
            else {
                progressDialog.setMessage("Generating meal plan...");

            }
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            return getWebServiceResponseData((String) objects[0]);
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            //takes meal plan saved in shared prefs and changes textViews
            TextView changeText[] =
                    {findViewById(R.id.textViewMonday),
                    findViewById(R.id.textViewTuesday),
                    findViewById(R.id.textViewWednesday),
                    findViewById(R.id.textViewThursday),
                    findViewById(R.id.textViewFriday),
                    findViewById(R.id.textViewSaturday),
                    findViewById(R.id.textViewSunday)};

            for(int i=0; i<changeText.length; i++) {
                spMealPlanOut = getApplicationContext().getSharedPreferences("mealPlanOutDay" + i, Context.MODE_PRIVATE);
                parsedText = "Breakfast\n" + spMealPlanOut.getString("breakfast","") +
                        "\n\nLunch\n" + spMealPlanOut.getString("lunch","") +
                        "\n\nDinner\n" + spMealPlanOut.getString("dinner","");
                changeText[i].setText(parsedText);
            }

            if(progressDialog.isShowing()) {
                textViewNoPlan.setVisibility(View.INVISIBLE);
                scrollView.setVisibility(View.VISIBLE);
                progressDialog.dismiss();
            }
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

                //stores meal plan into shared prefs
                JSONObject jsonResponse = new JSONObject(responseText);
                JSONArray jsonArrayList = jsonResponse.getJSONArray("choices");
                JSONObject firstItem = jsonArrayList.getJSONObject(0);
                JSONObject message = firstItem.getJSONObject("message");
                String content = message.getString("content");

                JSONObject mealPlan = new JSONObject(content);
                JSONObject planStart = mealPlan.getJSONObject("meal_plan");

                JSONObject[] days =
                        {planStart.getJSONObject("Monday"),
                        planStart.getJSONObject("Tuesday"),
                        planStart.getJSONObject("Wednesday"),
                        planStart.getJSONObject("Thursday"),
                        planStart.getJSONObject("Friday"),
                        planStart.getJSONObject("Saturday"),
                        planStart.getJSONObject("Sunday")};

                spMealPlanOut = getSharedPreferences("mealPlanOut", Context.MODE_PRIVATE);
                for(int i=0; i<days.length; i++) {
                    spMealPlanOut = getSharedPreferences("mealPlanOutDay" + i, Context.MODE_PRIVATE);

                    String breakfast = days[i].getString("breakfast");
                    String lunch = days[i].getString("lunch");
                    String dinner = days[i].getString("dinner");

                    Log.d("responseDebug", "Breakfast: " + breakfast +
                            "\nLunch: " + lunch +
                            "\nDinner: " + dinner);

                    SharedPreferences.Editor editor = spMealPlanOut.edit();
                    editor.putString("breakfast",breakfast);
                    editor.putString("lunch",lunch);
                    editor.putString("dinner",dinner);
                    editor.putInt("pressed",1);
                    editor.apply();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}