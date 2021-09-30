package com.example.findtheanimals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener{
    public static HashSet<Integer> r_set;

    public static TextToSpeech myTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTTS = new TextToSpeech(this,this);
        myTTS.setSpeechRate((float) 0.8);

    }

    public void level_clicked(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(MainActivity.this,GameActivity.class);
        intent.putExtra("level",buttonText);
        startActivity(intent);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            myTTS.setLanguage(Locale.ENGLISH);
            //Toast.makeText(this,"TTS is ready",Toast.LENGTH_SHORT).show();
        }
    }


}