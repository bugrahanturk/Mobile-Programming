package com.example.findtheanimals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AdapterLevel1 extends RecyclerView.Adapter<AdapterLevel1.CardViewTasarimNesneleriniTutucu> implements TextToSpeech.OnInitListener{
    private Context mContext;
    private List<Animals> my_animals;

    public AdapterLevel1(Context mContext, List<Animals> my_animals) {
        this.mContext = mContext;
        this.my_animals = my_animals;
    }


    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder{
        public ImageView imgV_animals;
        public CardView resim_CardView;

        public CardViewTasarimNesneleriniTutucu(@NonNull View view) {
            super(view);
            imgV_animals = view.findViewById(R.id.imgV_animals);
            resim_CardView = view.findViewById(R.id.resim_cardView);
        }
    }

    @NonNull
    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim,parent,false);

        return new CardViewTasarimNesneleriniTutucu(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriniTutucu holder, int position) {
        Animals animals = my_animals.get(position);
        holder.imgV_animals.setImageResource(mContext.getResources().getIdentifier(animals.getAnimal_img(),"drawable",mContext.getPackageName()));

        String animal_name = my_animals.get(position).getAnimal_name();

        holder.resim_CardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((FragmentActivity) v.getContext()).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();

                if (animal_name.equalsIgnoreCase(Level1Fragment.soru)){

                    GameActivity.mP.pause();

                    MainActivity.myTTS.stop();
                    try {
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MainActivity.myTTS.speak("You got it",TextToSpeech.QUEUE_FLUSH,null);

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ft.replace(R.id.fragment_tutucu1,new Level1Fragment());
                    GameActivity.point+=5;
                    GameActivity.count++;
                    if (GameActivity.count == 3){
                        GameActivity.count = 0;
                        ft.replace(R.id.fragment_tutucu1,new Level2Fragment());
                    }

                    ft.commit();
                }else{
                    MainActivity.myTTS.stop();
                    GameActivity.mP.pause();

                    MainActivity.myTTS.speak("Becareful",TextToSpeech.QUEUE_FLUSH,null);

                    try {
                        TimeUnit.MILLISECONDS.sleep(800);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    MainActivity.myTTS.stop();
                    GameActivity.count = 0;
                    Intent i = new Intent(mContext.getApplicationContext(),Result.class);
                    int points = GameActivity.point;
                    i.putExtra("puan",points);
                    ((Activity)mContext).finish();
                    mContext.startActivity(i);

                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return my_animals.size();
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS){
            MainActivity.myTTS.setLanguage(Locale.ENGLISH);
            Toast.makeText(mContext,"TTS is ready",Toast.LENGTH_SHORT).show();
        }
    }
}
