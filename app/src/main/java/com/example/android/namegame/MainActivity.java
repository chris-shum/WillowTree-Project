package com.example.android.namegame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    ImageView willowTreeLogoImageView;
    Spinner gameModeSelectSpinner;
    Button gameStartButton;
    ArrayAdapter<CharSequence> spinnerAdapter;
    String apiUrl;

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
        apiUrl = 




    }
}
