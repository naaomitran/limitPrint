package com.example.limitprint.model;

import android.content.Context;
import android.widget.ProgressBar;

public class LoadingBar extends ProgressBar {

    private boolean emitting;
    private boolean newDay; //resets after 24h
    private int time;
    private int speed;
    private boolean moving;
    private LoadingBar bar;
    private int progress;


    // Bar component of the screen and how it functions
    // - includes the footprint icon that moves along the bar and fills it up when
    // carbon emissions increase

    // Starts with empty bar with a footprint icon
    public LoadingBar(Context context) {
        super(context);
//        Image footprint =
        time = 0;
        speed = 0;
        moving = false;
        progress = 0;
    }

    // MODIFIES: this
    // EFFECTS: sets moving status to true if mobile senses movement of 30km/hr or greater
    public void isMoving() {
        if (speed >= 30) {
            moving = true;
        } else {
            moving = false;
        }
    }


    // EFFECTS: returns true if emitting co2
    public boolean isEmitting() {
        if (moving) {
            progress++; //research actual value
            return true;
        }
        return false;
    }

    // EFFECTS: return true if it's a new day, otherwise false
    public boolean isNewDay() {
        if (time >= 24) {
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: resets progress bar to 0% if new day
    public LoadingBar reset(Context context) {
        LoadingBar emptyBar = new LoadingBar(context);
        if (newDay) {
            System.out.println("It's a great day to make an impact!");
            progress = 0; //research actual value
            this.bar = emptyBar;
        }
        return this.bar;
    }

    public void exceed(int max) {
        if (progress >= max - 100) {
            System.out.println("Remember every step counts, you can do it!");
        }
        if (progress >= max) {
            System.out.println("Try and limit your carbon footprint by walking more for the " +
                    "                    + rest of the day!");
        }
    }
}
