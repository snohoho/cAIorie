package edu.floridapoly.mobiledeviceapps.fall23.caiorie;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    String responseText;
    String parsedText;
    StringBuffer response;
    URL url;
    String apiKey = "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";
    String model = "gpt-3.5-turbo-1106";
    String urlStr = "https://api.openai.com/v1/chat/completions";

    String userInfo = "weight:180lbs, age:25, height:5'10, weight goal:160lbs, dietary restrictions:nut allergy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mealplan_activity);

        final Button buttonSendToAI = (Button) findViewById(R.id.generatePlanBtn);

        buttonSendToAI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TextView textView = findViewById(R.id.textView9);
                String prompt = "generate a meal that a person with these features can eat: " + userInfo;
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

            TextView textResponse = findViewById(R.id.textView9);
            textResponse.setText(responseText);
            TextView textParsed = findViewById(R.id.textView9);
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
                parsedText = extractMessageFromJSONResponse(response.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String extractMessageFromJSONResponse(String response) {
        String contentStr = "";
        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONArray jsonArrayList = jsonResponse.getJSONArray("choices");
            JSONObject firstItem = jsonArrayList.getJSONObject(0);
            JSONObject message  = firstItem.getJSONObject("message");
            contentStr = message.getString("content");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return contentStr;
    }
}