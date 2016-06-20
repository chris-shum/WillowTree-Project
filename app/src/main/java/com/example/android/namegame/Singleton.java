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

    public int getSize() {
        return employeeName.size();
    }

}
