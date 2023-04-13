package com.cornhub;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

/**
 * A class representing an activity of the main menu.
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);
    }

    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController wic = getWindow().getDecorView().getWindowInsetsController();
            if (wic != null) {
                wic.hide(WindowInsets.Type.statusBars());
            }
        }
    }

    public void buttonClicked(View view) {
        Intent indent = new Intent(this, GameActivity.class);
        startActivity(indent);
    }

    public void goToLeaderboard(View view) {
        Intent indent = new Intent(this, LeaderboardActivity.class);
        startActivity(indent);
    }
}
