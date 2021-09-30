package com.example.findtheanimals;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Level5Fragment extends Fragment {

    public static TextView tv_quesiton;
    public static TextView tv_points;

    public static String soru;
    private AdapterLevel5 adapter;

    public static int r_sayi;
    Random r = new Random();
    public static String soru_img;
    public static int soru_sound;

    private Context mContext;

    private RecyclerView rv;
    private ArrayList<Animals> animals;

    public Level5Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_level5,container,false); // tasarıma erişiyoruz rootView oluşturup

        r_sayi = r.nextInt(21);
        soru = GameActivity.animal_names[r_sayi];
        soru_img = GameActivity.animal_imgs[r_sayi];
        soru_sound = GameActivity.animal_sounds[r_sayi];

        tv_points = rootView.findViewById(R.id.tv_points4);

        tv_quesiton = rootView.findViewById(R.id.tv_question);
        tv_quesiton.setText("FIND THE "+soru+"!");

        String text = tv_quesiton.getText().toString();
        MainActivity.myTTS.speak(text, TextToSpeech.QUEUE_FLUSH,null);

        GameActivity.mP = MediaPlayer.create(getActivity(),soru_sound);
        GameActivity.hndlr.postDelayed(new Runnable() {
            @Override
            public void run() {
                GameActivity.mP.start();
            }
        },1000);

        tv_points.setText("Points: "+GameActivity.point);

        rv = rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        //rv.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.GAP_HANDLING_NONE));
        rv.setLayoutManager(new GridLayoutManager(mContext,3));

        animals = new ArrayList<>();

        animals.add(new Animals(soru,soru_img, soru_sound)); //dogru cevap

        int yeni_rand ;

        while (GameActivity.R_numbers.size() < 7){
            int yepyeni = r.nextInt(21);
            if (r_sayi != yepyeni)
                GameActivity.R_numbers.add(yepyeni);
            //System.out.printf("sa");
        }
        GameActivity.R_numbers_list = new ArrayList<>(GameActivity.R_numbers);

        for (int i=0;i<7;i++){
            yeni_rand = GameActivity.R_numbers_list.get(i);
            //System.out.println("yeni rand "+yeni_rand+" r_sayi: "+r_sayi);
            animals.add(new Animals(GameActivity.animal_names[yeni_rand],GameActivity.animal_imgs[yeni_rand], GameActivity.animal_sounds[yeni_rand]));
        }
        GameActivity.R_numbers.clear();

        Collections.shuffle(animals);
        adapter = new AdapterLevel5(getActivity(),animals);

        rv.setAdapter(adapter);

        return rootView;
    }
}