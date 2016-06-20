package com.example.android.namegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    TextView normalGameName;
    ImageView normalGameImageView1, normalGameImageView2, normalGameImageView3, normalGameImageView4, normalGameImageView5, normalGameImageView6;

    ImageView reverseGameImageView;
    TextView reverseGameName1, reverseGameName2, reverseGameName3, reverseGameName4, reverseGameName5, reverseGameName6;

    Singleton mSingleton;

    int mCorrect;
    int mIncorrect;
    int mStreak;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSingleton = Singleton.getInstance();

        if (mSingleton.randomNumbers.get(0) < 0) {
            randomNumberGenerator();
        }

        Intent intent = getIntent();
        int gameMode = intent.getIntExtra("Game Mode", 0);
        switch (gameMode) {
            case 0:
                setContentView(R.layout.activity_game_normal);
                break;
            case 1:
                setContentView(R.layout.activity_game_normal);
                mSingleton.randomNumbers.remove(5);
                int matt = (int) (Math.random() * 8) + 70;
                mSingleton.randomNumbers.add(matt);
                break;
            case 2:
                setContentView(R.layout.activity_game_reverse);
                break;
        }

        normalGameName = (TextView) findViewById(R.id.normalGameTextViewName);
        normalGameImageView1 = (ImageView) findViewById(R.id.normalGameRandomImageViewOne);
        normalGameImageView2 = (ImageView) findViewById(R.id.normalGameRandomImageViewTwo);
        normalGameImageView3 = (ImageView) findViewById(R.id.normalGameRandomImageViewThree);
        normalGameImageView4 = (ImageView) findViewById(R.id.normalGameRandomImageViewFour);
        normalGameImageView5 = (ImageView) findViewById(R.id.normalGameRandomImageViewFive);
        normalGameImageView6 = (ImageView) findViewById(R.id.normalGameRandomImageViewSix);


        reverseGameImageView = (ImageView) findViewById(R.id.reverseGameImageView);
        reverseGameName1 = (TextView) findViewById(R.id.reverseGameRandomNameOne);
        reverseGameName2 = (TextView) findViewById(R.id.reverseGameRandomNameTwo);
        reverseGameName3 = (TextView) findViewById(R.id.reverseGameRandomNameThree);
        reverseGameName4 = (TextView) findViewById(R.id.reverseGameRandomNameFour);
        reverseGameName5 = (TextView) findViewById(R.id.reverseGameRandomNameFive);
        reverseGameName6 = (TextView) findViewById(R.id.reverseGameRandomNameSix);


        switch (gameMode) {
            case 0:
                castNormalGame();
                normalGamePlay();
                break;
            case 1:
                castNormalGame();
                normalGamePlay();
                break;
            case 2:
                castReverseGame();
                reverseGamePlay();
                break;
        }
    }

    public void randomNumberGenerator() {
        mSingleton.randomNumbers = new ArrayList<>();
        mSingleton.randomNumbers.clear();

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < mSingleton.getSize(); i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        for (int i = 0; i < 6; i++) {
            mSingleton.randomNumbers.add(list.get(i));
        }
        mSingleton.setRandomSelection(randomSelection());
    }

    public int randomSelection() {
        Random random = new Random();
        return random.nextInt(6);
    }

    public void normalGamePlay() {
        normalGameName.setText("Who is " + mSingleton.employeeName.get(mSingleton.randomNumbers.get(mSingleton.getRandomSelection())) + "?");
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(0))).into(normalGameImageView1);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(1))).into(normalGameImageView2);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(2))).into(normalGameImageView3);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(3))).into(normalGameImageView4);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(4))).into(normalGameImageView5);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(5))).into(normalGameImageView6);
    }

    public void reverseGamePlay() {
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(mSingleton.getRandomSelection()))).into(reverseGameImageView);
        reverseGameName1.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(0)));
        reverseGameName2.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(1)));
        reverseGameName3.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(2)));
        reverseGameName4.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(3)));
        reverseGameName5.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(4)));
        reverseGameName6.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(5)));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.normalGameRandomImageViewOne:
                checkIfCorrect(0);
                break;

            case R.id.normalGameRandomImageViewTwo:
                checkIfCorrect(1);
                break;

            case R.id.normalGameRandomImageViewThree:
                checkIfCorrect(2);
                break;

            case R.id.normalGameRandomImageViewFour:
                checkIfCorrect(3);
                break;

            case R.id.normalGameRandomImageViewFive:
                checkIfCorrect(4);
                break;

            case R.id.normalGameRandomImageViewSix:
                checkIfCorrect(5);
                break;

            case R.id.reverseGameRandomNameOne:
                checkIfCorrect(0);
                break;

            case R.id.reverseGameRandomNameTwo:
                checkIfCorrect(1);
                break;

            case R.id.reverseGameRandomNameThree:
                checkIfCorrect(2);
                break;

            case R.id.reverseGameRandomNameFour:
                checkIfCorrect(3);
                break;

            case R.id.reverseGameRandomNameFive:
                checkIfCorrect(4);
                break;

            case R.id.reverseGameRandomNameSix:
                checkIfCorrect(5);
                break;

            default:
                break;
        }
    }

    public void checkIfCorrect(int clickedNumber) {

        if (mSingleton.randomNumbers.get(clickedNumber) == mSingleton.randomNumbers.get(mSingleton.getRandomSelection())) {
            mCorrect++;
            mStreak++;
            Toast.makeText(GameActivity.this, "Yes", Toast.LENGTH_SHORT).show();
        } else {
            mIncorrect++;
            Toast.makeText(GameActivity.this, "No", Toast.LENGTH_SHORT).show();
        }


    }

    public void castNormalGame(){
        normalGameImageView1.setOnClickListener(this);
        normalGameImageView2.setOnClickListener(this);
        normalGameImageView3.setOnClickListener(this);
        normalGameImageView4.setOnClickListener(this);
        normalGameImageView5.setOnClickListener(this);
        normalGameImageView6.setOnClickListener(this);
    }

    public void castReverseGame(){
        reverseGameName1.setOnClickListener(this);
        reverseGameName2.setOnClickListener(this);
        reverseGameName3.setOnClickListener(this);
        reverseGameName4.setOnClickListener(this);
        reverseGameName5.setOnClickListener(this);
        reverseGameName6.setOnClickListener(this);
    }
}
