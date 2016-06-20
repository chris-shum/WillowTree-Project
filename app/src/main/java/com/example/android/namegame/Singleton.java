package com.example.android.namegame;

import java.util.ArrayList;

/**
 * Created by ShowMe on 6/19/16.
 */
public class Singleton {

    public static Singleton singleton;
    ArrayList<String> employeeName = new ArrayList<>();
    ArrayList<String> employeePhoto = new ArrayList<>();
    ArrayList<Integer> randomNumbers = new ArrayList<>();
    int randomSelection = -9999;
    int mCorrect;
    int mIncorrect;
    int mStreak;
    int mTotalQuestionsAsked;
    Boolean dialogueBoxShowing = false;

    public Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public int getRandomSelection() {
        return randomSelection;
    }

    public void setRandomSelection(int randomSelection) {
        this.randomSelection = randomSelection;
    }


    public int getmCorrect() {
        return mCorrect;
    }

    public void setmCorrect(int mCorrect) {
        this.mCorrect = mCorrect;
    }

    public int getmIncorrect() {
        return mIncorrect;
    }

    public void setmIncorrect(int mIncorrect) {
        this.mIncorrect = mIncorrect;
    }

    public int getmStreak() {
        return mStreak;
    }

    public void setmStreak(int mStreak) {
        this.mStreak = mStreak;
    }

    public int getmTotalQuestionsAsked() {
        return mTotalQuestionsAsked;
    }

    public void setmTotalQuestionsAsked(int mTotalQuestionsAsked) {
        this.mTotalQuestionsAsked = mTotalQuestionsAsked;
    }

    public Boolean getDialogueBoxShowing() {
        return dialogueBoxShowing;
    }

    public void setDialogueBoxShowing(Boolean dialogueBoxShowing) {
        this.dialogueBoxShowing = dialogueBoxShowing;
    }

    public int getSize() {
        return employeeName.size();
    }

}
