package com.example.alecsandra.golfscorecard.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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


public class MainActivity extends AppCompatActivity {

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

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        ButterKnife.bind(this);

        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        Gson gson = new Gson();
        String json = mSharedPreferences.getString(KEY_SCORES, "");
        if (json.isEmpty()) {
            Score[] scores = buildScoreCard();
            mScores = Arrays.copyOf(scores, scores.length, Score[].class);
            Log.d("onCreate build ", "empty json : ");
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

    @NonNull
    private Score[] buildScoreCard() {
        Score[] scores = new Score[18];
        for (int i = 0; i < scores.length; i++) {
            int scoreNameValue = i + 1;

            Score score = new Score();
            score.setScoreName("Hole " + scoreNameValue + ": ");
            score.setScoreValue(0);
            scores[i] = score;
        }
        return scores;
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mScores != null) {
            Gson gson = new Gson();
            String json = gson.toJson(mScores);
            Log.d("onPause build ", "json : " + json);
            mEditor.putString(KEY_SCORES, json);
            mEditor.apply();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                mScores = buildScoreCard();
                ScoreAdapter adapter = new ScoreAdapter(MainActivity.this, mScores);
                mListView.setAdapter(adapter);

                Gson gson = new Gson();
                String json = gson.toJson(mScores);

                mEditor.putString(KEY_SCORES, json);
                mEditor.apply();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
