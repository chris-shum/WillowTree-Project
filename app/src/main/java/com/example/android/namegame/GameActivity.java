package com.example.android.namegame;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: 6/20/16 sharedpreferences for high scores

    TextView normalGameName;
    ImageView normalGameImageView1, normalGameImageView2, normalGameImageView3, normalGameImageView4, normalGameImageView5, normalGameImageView6;

    ImageView reverseGameImageView;
    TextView reverseGameName1, reverseGameName2, reverseGameName3, reverseGameName4, reverseGameName5, reverseGameName6;

    Singleton mSingleton;

    int mGameMode;

    Boolean newQuestion = true;
    Boolean incorrect = false;

    AlertDialog alert;

    Timer mTimer;

    Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSingleton = Singleton.getInstance();

        if (mSingleton.randomNumbers.get(0) < 0) {
            randomNumberGenerator();
        }

        Intent intent = getIntent();
        mGameMode = intent.getIntExtra("Game Mode", 0);
        switch (mGameMode) {
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
        reverseGameName1 = (Button) findViewById(R.id.reverseGameRandomNameOne);
        reverseGameName2 = (Button) findViewById(R.id.reverseGameRandomNameTwo);
        reverseGameName3 = (Button) findViewById(R.id.reverseGameRandomNameThree);
        reverseGameName4 = (Button) findViewById(R.id.reverseGameRandomNameFour);
        reverseGameName5 = (Button) findViewById(R.id.reverseGameRandomNameFive);
        reverseGameName6 = (Button) findViewById(R.id.reverseGameRandomNameSix);


        switch (mGameMode) {
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


        mSnackbar = Snackbar.make(findViewById(android.R.id.content), "Would you like a hint?", Snackbar.LENGTH_LONG).setAction("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                choiceToRemove();
            }
        });

        mTimer = new Timer();
        startTimer();


        if (mSingleton.dialogueBoxShowing) {
            showDialog(this);
        }


    }

    public void randomNumberGenerator() {
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
            if (!incorrect) {
                mSingleton.mCorrect++;
                mSingleton.mStreak++;
            }
            incorrect = false;
            newQuestion = true;
            mSingleton.mTotalQuestionsAsked++;
            // TODO: 6/20/16 end button brings you back to main menu, saves scores in sharedpreferences
            mSingleton.dialogueBoxShowing = true;
            showDialog(this);


        } else {
            mSingleton.mStreak = 0;
            incorrect = true;
            if (newQuestion) {
                mSingleton.mIncorrect++;
                if (mSingleton.mCorrect < 0) {
                    mSingleton.mCorrect = 0;
                }
            }
            newQuestion = false;
            Toast.makeText(GameActivity.this, "No", Toast.LENGTH_SHORT).show();
        }
    }

    public void castNormalGame() {
        normalGameImageView1.setOnClickListener(this);
        normalGameImageView2.setOnClickListener(this);
        normalGameImageView3.setOnClickListener(this);
        normalGameImageView4.setOnClickListener(this);
        normalGameImageView5.setOnClickListener(this);
        normalGameImageView6.setOnClickListener(this);
    }

    public void castReverseGame() {
        reverseGameName1.setOnClickListener(this);
        reverseGameName2.setOnClickListener(this);
        reverseGameName3.setOnClickListener(this);
        reverseGameName4.setOnClickListener(this);
        reverseGameName5.setOnClickListener(this);
        reverseGameName6.setOnClickListener(this);
    }

    protected void showDialog(final Context context) {
        if (mGameMode == 0 || mGameMode == 1) {
            allImagesVisible();
        }
        if (mGameMode == 2) {
            allButtonsVisible();
        }
        mSingleton.setHintCount(0);
        String records1 = "You got " + mSingleton.mCorrect + "/" + mSingleton.mTotalQuestionsAsked + " correct.";
        String records2 = "Longest streak: " + mSingleton.mStreak;
        String records = records1 + "\n" + records2;

        ImageView image = new ImageView(this);
        Picasso.with(this).

                load(mSingleton.employeePhoto.get(mSingleton.randomNumbers.get(mSingleton.getRandomSelection()

                ))).

                into(image);

        AlertDialog.Builder builder = new AlertDialog.Builder(context).setTitle("Yes!  This is " + mSingleton.employeeName.get(mSingleton.randomNumbers.get(mSingleton.getRandomSelection())) + "!").
                setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSingleton.dialogueBoxShowing = false;
                        randomNumberGenerator();
                        mTimer = new Timer();
                        startTimer();

                        switch (mGameMode) {
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
                }).
                setNegativeButton("End", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        mSingleton.dialogueBoxShowing = false;
                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
                        startActivity(intent);

                    }
                }).
                setView(image).setMessage(records);
        alert = builder.create();
        alert.show();
        mTimer.cancel();
        mTimer.purge();

    }

    public void choiceToRemove() {
        int choiceToRemove;
        choiceToRemove = randomSelection();
        if (mSingleton.hintCount > 4) {
            mTimer.cancel();
            mTimer.purge();

        } else {
            while (choiceToRemove == mSingleton.getRandomSelection()) {
                choiceToRemove = randomSelection();
            }
            if (mGameMode == 0 || mGameMode == 1) {
                switch (choiceToRemove) {
                    case 0:
                        if (normalGameImageView1.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            normalGameImageView1.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 1:
                        if (normalGameImageView2.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            normalGameImageView2.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }

                        break;
                    case 2:
                        if (normalGameImageView3.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            normalGameImageView3.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }

                        break;
                    case 3:
                        if (normalGameImageView4.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();

                        } else {
                            normalGameImageView4.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }

                        break;
                    case 4:
                        if (normalGameImageView5.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();

                        } else {
                            normalGameImageView5.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }

                        break;
                    case 5:
                        if (normalGameImageView6.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();

                        } else {
                            normalGameImageView6.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }

                        break;
                    default:
                        break;
                }
            }
            if (mGameMode == 2) {
                switch (choiceToRemove) {
                    case 0:
                        if (reverseGameName1.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName1.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 1:
                        if (reverseGameName2.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName2.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 2:
                        if (reverseGameName3.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName3.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 3:
                        if (reverseGameName4.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName4.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 4:
                        if (reverseGameName5.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName5.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    case 5:
                        if (reverseGameName6.getVisibility() == View.INVISIBLE) {
                            choiceToRemove();
                        } else {
                            reverseGameName6.setVisibility(View.INVISIBLE);
                            mSingleton.hintCount++;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void allImagesVisible() {
        normalGameImageView1.setVisibility(View.VISIBLE);
        normalGameImageView2.setVisibility(View.VISIBLE);
        normalGameImageView3.setVisibility(View.VISIBLE);
        normalGameImageView4.setVisibility(View.VISIBLE);
        normalGameImageView5.setVisibility(View.VISIBLE);
        normalGameImageView6.setVisibility(View.VISIBLE);
    }

    public void allButtonsVisible() {
        reverseGameName1.setVisibility(View.VISIBLE);
        reverseGameName2.setVisibility(View.VISIBLE);
        reverseGameName3.setVisibility(View.VISIBLE);
        reverseGameName4.setVisibility(View.VISIBLE);
        reverseGameName5.setVisibility(View.VISIBLE);
        reverseGameName6.setVisibility(View.VISIBLE);
    }

    public void startTimer() {
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        mSnackbar.show();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                mSnackbar.dismiss();
                            }
                        }, 5000);

                    }
                });
            }
        }, 0, 20000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSingleton.hints.clear();
        if (mGameMode == 0 || mGameMode == 1) {

            mSingleton.hints.add(normalGameImageView1.getVisibility());
            mSingleton.hints.add(normalGameImageView2.getVisibility());
            mSingleton.hints.add(normalGameImageView3.getVisibility());
            mSingleton.hints.add(normalGameImageView4.getVisibility());
            mSingleton.hints.add(normalGameImageView5.getVisibility());
            mSingleton.hints.add(normalGameImageView6.getVisibility());
        }
        if (mGameMode == 2) {
            mSingleton.hints.add(reverseGameName1.getVisibility());
            mSingleton.hints.add(reverseGameName2.getVisibility());
            mSingleton.hints.add(reverseGameName3.getVisibility());
            mSingleton.hints.add(reverseGameName4.getVisibility());
            mSingleton.hints.add(reverseGameName5.getVisibility());
            mSingleton.hints.add(reverseGameName6.getVisibility());
        }
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {

            if (mGameMode == 0 || mGameMode == 1) {
                normalGameImageView1.setVisibility(mSingleton.hints.get(0));
                normalGameImageView2.setVisibility(mSingleton.hints.get(1));
                normalGameImageView3.setVisibility(mSingleton.hints.get(2));
                normalGameImageView4.setVisibility(mSingleton.hints.get(3));
                normalGameImageView5.setVisibility(mSingleton.hints.get(4));
                normalGameImageView6.setVisibility(mSingleton.hints.get(5));
            }
            if (mGameMode == 2) {

                reverseGameName1.setVisibility(mSingleton.hints.get(0));
                reverseGameName2.setVisibility(mSingleton.hints.get(1));
                reverseGameName3.setVisibility(mSingleton.hints.get(2));
                reverseGameName4.setVisibility(mSingleton.hints.get(3));
                reverseGameName5.setVisibility(mSingleton.hints.get(4));
                reverseGameName6.setVisibility(mSingleton.hints.get(5));
            }
        }
    }

}
