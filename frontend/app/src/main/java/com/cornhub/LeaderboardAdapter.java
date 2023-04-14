package com.cornhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardAdapter.MyView>{
    private Context context;

    private List<JSONObject> list;

    public class MyView extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewScore;
        TextView textViewRank;

        public MyView(View view) {
            super(view);

            // initialise TextView with id
            textViewName = (TextView) view.findViewById(R.id.topPlayersName);
            textViewScore = (TextView) view.findViewById(R.id.topPlayersScore);
            textViewRank = (TextView) view.findViewById(R.id.topPlayersRank);

        }
    }

    public LeaderboardAdapter(List<JSONObject> horizontalList)
    {
        this.list = horizontalList;
    }

    @Override
    public LeaderboardAdapter.MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.userboard, parent, false);
        context = parent.getContext();
        return new LeaderboardAdapter.MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final LeaderboardAdapter.MyView holder, final int position)
    {
        JSONObject item = list.get(position);
        try {
            holder.textViewName.setText(item.getString("id"));
            holder.textViewScore.setText(String.valueOf(item.getInt("score")));
            int rank = position + 1;
            holder.textViewRank.setText(String.valueOf(rank));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

}

