package com.cornhub;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LeaderboardActivity extends Activity {

    RecyclerView leaderboardRecycler;
    RecyclerView.LayoutManager leaderboardRecyclerViewLayoutManager;
    ArrayList<JSONObject> scoreboardList;
    LeaderboardAdapter leaderboardAdapter;

    LinearLayoutManager verticalLayout;

    TextView currentUsername;
    TextView currentUserScore;
    TextView currentUserRank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideStatusBar();
        setContentView(R.layout.leaderboard);

        DBHandler db = new DBHandler(this);
        JSONObject leaderboardData = db.getLeaderboardData();
        String name = "";

        String url = "http://10.0.2.2:3000/leaderboard";

        Map<String, String> params = new HashMap();
        try {
            name = leaderboardData.getString("id");
            params.put("id", name);
            params.put("score", leaderboardData.getString("total"));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.print("Updated DB success");
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonRequest);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    addItemsToItemsRecyclerViewArrayList(response);
                    leaderboardAdapter = new LeaderboardAdapter(scoreboardList);
                    verticalLayout = new LinearLayoutManager(LeaderboardActivity.this, LinearLayoutManager.VERTICAL, false);
                    leaderboardRecycler.setLayoutManager(verticalLayout);
                    leaderboardRecycler.setAdapter(leaderboardAdapter);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                System.out.println("Error! API couldn't be reached");
            }

        });


        currentUsername = findViewById(R.id.currentUsername);
        currentUserScore = findViewById(R.id.currentUserScore);
        currentUserRank = findViewById(R.id.currentUserRank);
        String url2 = "http://10.0.2.2:3000/leaderboard/" + name;
        JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject element = null;
                    element = response.getJSONObject(0);
                    currentUsername.setText(element.getString("id"));
                    currentUserScore.setText(String.valueOf(element.getInt("score")));
                    currentUserRank.setText(String.valueOf(element.getInt("position")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                System.out.println("Error! API couldn't be reached");
            }

        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
        requestQueue.add(jsonArrayRequest2);

        leaderboardRecycler = (RecyclerView) findViewById(R.id.leaderboardRecycler);
        leaderboardRecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        leaderboardRecycler.setLayoutManager(leaderboardRecyclerViewLayoutManager);
    }

    public void addItemsToItemsRecyclerViewArrayList(JSONArray dataList) {
        // Adding items to ArrayList

        scoreboardList = new ArrayList<>();

        for (int i = 0; i<dataList.length(); i++) {
            JSONObject element = null;
            try {
                element = dataList.getJSONObject(i);
                scoreboardList.add(element);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
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
                wic.hide(WindowInsets.Type.systemBars());
            }
        }
    }
}
