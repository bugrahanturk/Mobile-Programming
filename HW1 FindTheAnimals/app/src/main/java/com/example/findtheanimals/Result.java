package com.example.findtheanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.concurrent.PriorityBlockingQueue;

public class Result extends AppCompatActivity {

    private TextView result_p ;
    private TextView tv_result;
    private TextView tv_best;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result_p = findViewById(R.id.tv_result_points);
        tv_result = findViewById(R.id.tv_result);
        tv_best = findViewById(R.id.tv_best);

        sp = getSharedPreferences("HighScore",MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        int points = getIntent().getIntExtra("puan",0);
        int count = getIntent().getIntExtra("count",0);

        int highestScore = sp.getInt("HighScore",0);

        result_p.setText("YOUR TOTAL POINTS: "+points);

        tv_best.setText("HIGH SCORE : "+highestScore);

        if (points > highestScore){
            highestScore = points;
            tv_best.setText("NEW HIGH SCORE: "+ highestScore);
            editor.putInt("HighScore",highestScore);
            editor.commit();
        }

        if(count == 3){
            tv_result.setText("WELL DONE !");
        }
        else{
            tv_result.setText("GAME OVER");
        }


    }
    public void clicked_home(View view) {
        GameActivity.point=0;
        GameActivity.count=0;
        Intent intent = new Intent(Result.this,MainActivity.class);
        finish();
        startActivity(intent);

    }

}