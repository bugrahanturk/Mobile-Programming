package com.example.findtheanimals;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Level1Fragment extends Fragment  {

    public static TextView tv_quesiton;
    public static TextView tv_points;
    private Button btn_sound;

    public static String soru;
    private AdapterLevel1 adapter;

    public static int r_sayi;
    Random r = new Random();
    public static String soru_img;
    public static int soru_sound;

    private Context mContext;

    private RecyclerView rv;
    private ArrayList<Animals> animals;

    private Runnable runnable;
    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_level1,container,false); // tasarıma erişiyoruz rootView oluşturup

        //btn_sound = rootView.findViewById(R.id.button_sound);

        r_sayi = r.nextInt(21);
        soru = GameActivity.animal_names[r_sayi];
        soru_img = GameActivity.animal_imgs[r_sayi];
        soru_sound = GameActivity.animal_sounds[r_sayi];

        tv_points = rootView.findViewById(R.id.tv_points);

        tv_quesiton = rootView.findViewById(R.id.tv_question);
        tv_quesiton.setText("FIND THE "+soru+" ?");

        String text = tv_quesiton.getText().toString();
        MainActivity.myTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);

        tv_points.setText("Points: "+GameActivity.point);

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        rv.setLayoutManager(new GridLayoutManager(mContext,2));
        animals = new ArrayList<>();

        animals.add(new Animals(soru,soru_img, soru_sound)); //dogru cevap

        System.out.println(r_sayi);
        int yeni_rand = r.nextInt(21);
        while (r_sayi == yeni_rand){
            yeni_rand = r.nextInt(21);
        }
        animals.add(new Animals(GameActivity.animal_names[yeni_rand],GameActivity.animal_imgs[yeni_rand], soru_sound));
        System.out.println(yeni_rand);

        Collections.shuffle(animals);
        adapter = new AdapterLevel1(getActivity(),animals);

        rv.setAdapter(adapter);


        GameActivity.mP = MediaPlayer.create(getActivity(),soru_sound);

        GameActivity.hndlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameActivity.mP.start();
            }},1000);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


}