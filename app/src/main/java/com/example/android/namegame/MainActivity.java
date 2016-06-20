package com.example.android.namegame;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    ImageView willowTreeLogoImageView;
    Spinner gameModeSelectSpinner;
    Button gameStartButton;
    ArrayAdapter<CharSequence> spinnerAdapter;
    String apiUrl;
    GetWTDataAsyncTask getWTData;
//    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        willowTreeLogoImageView = (ImageView) findViewById(R.id.willowTreeLogoImageView);
        gameModeSelectSpinner = (Spinner) findViewById(R.id.gameModeSelectSpinner);
        gameStartButton = (Button) findViewById(R.id.gameStartButton);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.game_modes, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gameModeSelectSpinner.setAdapter(spinnerAdapter);
        apiUrl = "http://api.namegame.willowtreemobile.com/";
//        i = 0;

        getWTData = new GetWTDataAsyncTask();
        getWTData.execute(apiUrl);


        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Singleton singleton = Singleton.getInstance();
//                if (singleton != null) {
//                    String test = singleton.getEmployeeName().get(i);
//                    String toast = "Employee #" + i + " is " + test;
//                    String url = singleton.getEmployeePhoto().get(i);
//                    String toast2 = "Their photo is at " + url;
//                    Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(MainActivity.this, toast2, Toast.LENGTH_SHORT).show();
//                    i++;
//                }
            }
        });

    }

    private String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
        String data;
        while ((data = reader.readLine()) != null) {
            builder.append(data);
        }
        reader.close();
        return builder.toString();
    }

    public class GetWTDataAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... wtURL) {

            String data = "";
            try {
                URL url = new URL(wtURL[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inStream = connection.getInputStream();
                data = getInputData(inStream);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }

            try {
                JSONArray dataArray = new JSONArray(data);
                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject object = dataArray.optJSONObject(i);
                    String employeeName = object.optString("name");
                    String employeePhoto = object.optString("url");
                    Singleton singleton = Singleton.getInstance();
                    singleton.employeeName.add(employeeName);
                    singleton.employeePhoto.add(employeePhoto);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            gameStartButton.setEnabled(true);
        }
    }
}
