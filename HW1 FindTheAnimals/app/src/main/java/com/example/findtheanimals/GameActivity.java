package com.example.findtheanimals;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.Display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class GameActivity extends AppCompatActivity  {

    public static MediaPlayer mP;
    public static boolean active = false;
    public static int count=0;
    public static String [] animal_names = {"Cat","Dog","Lion","Bee","Pig","Cow","Cock","Fox","Wolf","Dolphin","Tiger","Frog","Leopard","Monkey","Elephant","Duck","Bird","Eagle","Parrot","Bear","Owl"};
    public static String [] animal_imgs = {"cat","dog","lion","bee","pig","cow","cock","fox","wolf","dolphin","tiger","frog","leopard","monkey","elephant","duck","bird","eagle","parrot","bear","owl"};
    public static int [] animal_sounds = {R.raw.cat,R.raw.dog,R.raw.lion,R.raw.bee,R.raw.pig,R.raw.cow,R.raw.cock,R.raw.fox,R.raw.wolf,R.raw.dolphin,R.raw.tiger,R.raw.frog,R.raw.leopard,R.raw.monkey,R.raw.elephant,R.raw.duck,R.raw.bird,R.raw.eagle,R.raw.parrot,R.raw.bear,R.raw.owl};

    public static HashSet<Integer> R_numbers = new HashSet<>();
    public static ArrayList<Integer> R_numbers_list;

    public static int point=0;

    public static Handler hndlr = new Handler();

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        String btn_text = getIntent().getStringExtra("level");

        if (btn_text.equalsIgnoreCase("level 1")){

            ft.add(R.id.fragment_tutucu1,new Level1Fragment());
            ft.commit();
        }
        else if (btn_text.equalsIgnoreCase("level 2")){

            ft.add(R.id.fragment_tutucu1,new Level2Fragment());
            ft.commit();
        }
        else if (btn_text.equalsIgnoreCase("level 3")){

            ft.add(R.id.fragment_tutucu1,new Level3Fragment());
            ft.commit();
        }
        else if (btn_text.equalsIgnoreCase("level 4")){

            ft.add(R.id.fragment_tutucu1,new Level4Fragment());
            ft.commit();
        }
        else if (btn_text.equalsIgnoreCase("level 5")){

            ft.add(R.id.fragment_tutucu1,new Level5Fragment());
            ft.commit();
        }

    }

    public static void speak(String text){
        MainActivity.myTTS.speak(text, TextToSpeech.QUEUE_FLUSH,null);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GameActivity.mP.release();
        MainActivity.myTTS.stop();
        GameActivity.count=0;
        GameActivity.point=0;
    }
    public void onStart(){
        super.onStart();
        active = true;
        System.out.println("true");
    }
    public void onStop(){
        super.onStop();
        active = false;
        System.out.println("false");
    }


}