package com.example.android.namegame;

import java.util.ArrayList;

/**
 * Created by ShowMe on 6/19/16.
 */
public class Singleton {

    public static Singleton singleton;
    ArrayList<String> employeeName = new ArrayList<>();
    ArrayList<String> employeePhoto = new ArrayList<>();

    public Singleton() {
    }

    public static Singleton getInstance() {
        if (singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public ArrayList<String> getEmployeeName() {
        return employeeName;
    }

    public ArrayList<String> getEmployeePhoto() {
        return employeePhoto;
    }
}
