package com.example.android.namegame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    TextView normalGameName;
    ImageView normalGameImageView1, normalGameImageView2, normalGameImageView3, normalGameImageView4, normalGameImageView5, normalGameImageView6;

    ImageView reverseGameImageView;
    TextView reverseGameName1, reverseGameName2, reverseGameName3, reverseGameName4, reverseGameName5, reverseGameName6;

    Singleton mSingleton;


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
                int matt = (int) (Math.random()*8)+70;
                mSingleton.randomNumbers.add(matt);
                Toast.makeText(GameActivity.this, matt+" ", Toast.LENGTH_SHORT).show();
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
                normalGamePlay();
                break;
            case 1:
                normalGamePlay();
                break;
            case 2:
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
    }

    public int randomSelection(){
        Random random = new Random();
        return random.nextInt(6);
    }

    public void normalGamePlay(){
        normalGameName.setText("Who is "+mSingleton.employeeName.get(mSingleton.randomNumbers.get(randomSelection()))+"?");

        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(0))).into(normalGameImageView1);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(1))).into(normalGameImageView2);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(2))).into(normalGameImageView3);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(3))).into(normalGameImageView4);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(4))).into(normalGameImageView5);
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(5))).into(normalGameImageView6);



    }

    public void reverseGamePlay(){
        Picasso.with(this).load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(randomSelection()))).into(reverseGameImageView);

        reverseGameName1.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(0)));
        reverseGameName2.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(1)));
        reverseGameName3.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(2)));
        reverseGameName4.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(3)));
        reverseGameName5.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(4)));
        reverseGameName6.setText(mSingleton.employeeName.get(mSingleton.randomNumbers.get(5)));

    }

}
