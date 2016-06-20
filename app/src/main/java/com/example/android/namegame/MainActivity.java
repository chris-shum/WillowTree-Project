package com.example.android.namegame;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

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
    String willowTreeLogoURL;
    Singleton mSingleton;

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
        willowTreeLogoURL = "http://10477-presscdn-0-4.pagely.netdna-cdn.com/wp-content/uploads/2013/11/willowtreeapps_logo_vert-1024x819.png";

        mSingleton = Singleton.getInstance();
        mSingleton.employeeName.clear();
        mSingleton.employeePhoto.clear();
        mSingleton.randomNumbers.clear();
        mSingleton.randomNumbers.add(-9999);
        mSingleton.setmCorrect(0);
        mSingleton.setmIncorrect(0);
        mSingleton.setmStreak(0);
        mSingleton.setmTotalQuestionsAsked(0);

        getWTData = new GetWTDataAsyncTask();
        getWTData.execute(apiUrl);

        Picasso.with(this).load(willowTreeLogoURL).into(willowTreeLogoImageView);

        gameStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                String gameMode = gameModeSelectSpinner.getSelectedItem().toString();
                switch (gameMode) {
                    case "Normal Mode":
                        intent.putExtra("Game Mode", 0);
                        break;
                    case "Matt Mode":
                        intent.putExtra("Game Mode", 1);
                        break;
                    case "Reverse Mode":
                        intent.putExtra("Game Mode", 2);
                        break;
                }
                startActivity(intent);
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
                    mSingleton.employeeName.add(employeeName);
                    mSingleton.employeePhoto.add(employeePhoto);
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

    @Override
    protected void onResume() {
        super.onResume();
        mSingleton.randomNumbers.clear();
        mSingleton.randomNumbers.add(-9999);
    }
}
