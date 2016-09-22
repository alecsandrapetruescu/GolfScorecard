package com.example.alecsandra.golfscorecard.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.alecsandra.golfscorecard.R;
import com.example.alecsandra.golfscorecard.adapters.ScoreAdapter;
import com.example.alecsandra.golfscorecard.model.Score;
import com.google.gson.Gson;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;

    private static final String PREFS_FILE = "com.teamtreehouse.golfscorecard.preferences";
    private static final String KEY_SCORES = "KEY_SCORES";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Score[] mScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        Gson gson = new Gson();
        String json = mSharedPreferences.getString(KEY_SCORES, "");
        if(json == ""){
            Score[] scores = new Score[18];

            for(int i = 0; i < scores.length; i++ ){
                int scoreNameValue = i+1;

                Score score = new Score();
                score.setScoreName("Hole " + scoreNameValue + ": ");
                score.setScoreValue(0);
                scores[i] = score;
            }
            mScores = Arrays.copyOf(scores, scores.length, Score[].class);
        } else {
            Log.d("onCreate build ", "json : " + json);
            mScores = gson.fromJson(json, Score[].class);
        }

        ScoreAdapter adapter = new ScoreAdapter(this, mScores);
        mListView.setAdapter(adapter);
        mListView.setEmptyView(mEmptyTextView);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String message = "You tapped on Hole : " + String.valueOf(position);
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mScores != null){
            Gson gson = new Gson();
            String json = gson.toJson(mScores);
            Log.d("onPause build ", "json : " + json);
            mEditor.putString(KEY_SCORES, json);
            mEditor.apply();
        }

    }
}
