package com.cornhub;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowInsets;
import android.view.WindowInsetsController;

/**
 * A class representing an activity of the game.
 */
public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(new GameView(this));
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
