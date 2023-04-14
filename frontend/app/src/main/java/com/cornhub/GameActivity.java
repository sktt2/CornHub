package com.cornhub;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * A class representing an activity of the game.
 */
public class GameActivity extends Activity {

    private GameManager gameManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();

        DBHandler db = new DBHandler(this);
        int[] data = db.getData();

//        String stored = "";
//        for(int i : data){
//            stored += i + ",";
//        }
//        System.out.println(stored);

        setContentView(R.layout.activity_game);
        ArrayList<Farmer> farmers = new ArrayList<>();
        for(int i = 0; i < data[0]; i++) {
            farmers.add(new Farmer());
        }
        this.gameManager = new GameManager(GameActivity.this ,farmers, data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9]);




        ImageView farmer = findViewById(R.id.farmer);
        TranslateAnimation farmerAnimation = new TranslateAnimation(1700.0f, -500f,
                0f, 0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        farmerAnimation.setDuration(5000);  // animation duration
        farmerAnimation.setRepeatCount(-1);  // animation repeat count
//        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);

        farmer.startAnimation(farmerAnimation);  // start animation
        ImageView cloud1 = findViewById(R.id.cloud1);
        TranslateAnimation cloud1Animation = new TranslateAnimation(0, 2000,
                0f, 0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        cloud1Animation.setDuration(10000);  // animation duration
        cloud1Animation.setRepeatCount(-1);  // animation repeat count
        cloud1Animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);
        cloud1.startAnimation(cloud1Animation);  // start animation        farmer.startAnimation(farmerAnimation);  // start animation
        ImageView cloud2 = findViewById(R.id.cloud2);
        TranslateAnimation cloud2Animation = new TranslateAnimation(2000.0f, 0f,
                0f, 0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        cloud2Animation.setDuration(7000);  // animation duration
        cloud2Animation.setRepeatCount(-1);  // animation repeat count
        cloud2Animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        //animation.setFillAfter(true);
        cloud2.startAnimation(cloud2Animation);  // start animation

    }
    public void buttonClicked(View view) {
        int id = view.getId();

        if (id == R.id.buttonMenu) {
            this.finish();
        }
    }
    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController wic = getWindow().getDecorView().getWindowInsetsController();
            if (wic != null) {
                wic.hide(WindowInsets.Type.statusBars());
            }
        }
    }
}
