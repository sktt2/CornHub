package com.cornhub;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * A class representing an activity of the main menu.
 */
public class MainActivity extends Activity {

    private EditText editTextTextPersonName;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.activity_main);

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        DBHandler db = new DBHandler(this);
        JSONObject leaderboardData = db.getLeaderboardData();
        try{
            name = leaderboardData.getString("id");
            editTextTextPersonName.setText(name);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        editTextTextPersonName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (editTextTextPersonName.getText().length() > 0){
                        name = String.valueOf(editTextTextPersonName.getText());
                        db.updateName(name);
                    }
                }catch (Exception e ) {
                    System.out.println("name too short");
                }
            }

        });

    }




    private void hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WindowInsetsController wic = getWindow().getDecorView().getWindowInsetsController();
            if (wic != null) {
                wic.hide(WindowInsets.Type.systemBars());
            }
        }
    }

    public void buttonClicked(View view) {
        int id = view.getId();

        if (id == R.id.startButton) {
            Intent startIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startIntent);
        } else if (id == R.id.resetButton) {
            DBHandler db = new DBHandler(this);
            db.drop();
            Intent startIntent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(startIntent);
        } else if (id == R.id.leaderboardButton) {
            Intent leaderboardIntent = new Intent(MainActivity.this, LeaderboardActivity.class);
            startActivity(leaderboardIntent);
        }
    }
}
