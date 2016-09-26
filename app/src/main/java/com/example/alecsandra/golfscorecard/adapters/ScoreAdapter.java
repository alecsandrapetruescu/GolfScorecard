package com.example.alecsandra.golfscorecard.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alecsandra.golfscorecard.R;
import com.example.alecsandra.golfscorecard.model.Score;

public class ScoreAdapter extends BaseAdapter {

    private Context mContext;
    private Score[] mScores;

    public ScoreAdapter(Context context, Score[] scores) {
        mContext = context;
        mScores = scores;
    }

    @Override
    public int getCount() {
        return mScores.length;
    }

    @Override
    public Object getItem(int i) {
        return mScores[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.score_list_item, null);
            holder = new ViewHolder();
            holder.scoreName = (TextView) convertView.findViewById(R.id.scoreName);
            holder.scoreValue = (TextView) convertView.findViewById(R.id.scoreValue);
            holder.increaseScore = (ImageButton) convertView.findViewById(R.id.increaseScore);
            holder.decreaseScore = (ImageButton) convertView.findViewById(R.id.decreaseScore);
            holder.decreaseScore.setClickable(true);
            holder.increaseScore.setClickable(true);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.scoreName.setText(mScores[position].getScoreName() + "");
        holder.scoreValue.setText(mScores[position].getScoreValue() + "");

        holder.increaseScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("build ", "score name : " + mScores[position].getScoreName() + "score  : " + mScores[position].getScoreValue());
                mScores[position].setScoreValue(mScores[position].getScoreValue() + 1);
                mScores[position].setScoreName(mScores[position].getScoreName());
                holder.scoreName.setText(mScores[position].getScoreName() + "");
                holder.scoreValue.setText(mScores[position].getScoreValue() + "");
            }
        });

        holder.decreaseScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("build ", "score name : " + mScores[position].getScoreName() + "score  : " + mScores[position].getScoreValue());
                int decreaseScore = mScores[position].getScoreValue() - 1;
                if (decreaseScore < 0) decreaseScore = 0;
                mScores[position].setScoreValue(decreaseScore);
                mScores[position].setScoreName(mScores[position].getScoreName());
                holder.scoreName.setText(mScores[position].getScoreName() + "");
                holder.scoreValue.setText(mScores[position].getScoreValue() + "");
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView scoreName;
        TextView scoreValue;
        ImageButton increaseScore;
        ImageButton decreaseScore;
    }
}
