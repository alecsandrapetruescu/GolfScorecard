package com.example.alecsandra.golfscorecard.ui;

import android.app.Activity;
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

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends Activity {

    @BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyTextView;

    private Score[] mScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Score[] scores = new Score[18];

                for(int i = 0; i < 18; i++ ){

                    Score score = new Score();
                    score.setScoreName("Hole " + i + ": ");
                    score.setScoreValue(0);

                    scores[i] = score;
                }
                mScores = Arrays.copyOf(scores, scores.length, Score[].class);
            }
        });

        Log.d("onCreate build ", "length of scores : " + mScores.length);
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


}
