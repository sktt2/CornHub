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
        int id = view.getId();

        if (id == R.id.playButton) {
            Intent playIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(playIntent);
        } else if (id == R.id.leaderboardButton) {
            Intent leaderboardIntent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(leaderboardIntent);
        } else if (id == R.id.quitButton) {
            finishAffinity();
        }
    }
}
