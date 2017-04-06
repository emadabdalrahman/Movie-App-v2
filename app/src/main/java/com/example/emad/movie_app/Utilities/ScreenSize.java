package com.example.emad.movie_app.Utilities;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

/**
 * Created by emad on 11/12/2016.
 */

public class ScreenSize {
    private double width_dp;
    private double height_dp;
    private Activity activity;

    public ScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
//        Point point = new Point();
//        display.getSize(point);
//        height_dp = point.y;
//        width_dp=point.x;


        DisplayMetrics displayMetrics =new DisplayMetrics();
        display.getMetrics(displayMetrics);

        Float density = activity.getResources().getDisplayMetrics().density;
        height_dp = displayMetrics.heightPixels/density;
        width_dp = displayMetrics.widthPixels/density;

    }
    public double getWidth_dp(){return width_dp;}
    public double getHeight_dp(){return height_dp;}
}
