package com.example.findtheanimals;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLevel3 extends RecyclerView.Adapter<AdapterLevel3.CardViewTasarimNesneleriniTutucu>{
    private Context mContext;
    private List<Animals> my_animals;

    public AdapterLevel3(Context mContext, List<Animals> my_animals) {
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

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim4,parent,false);

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
                System.out.println(animal_name+" "+Level3Fragment.soru);
                if (animal_name.equalsIgnoreCase(Level3Fragment.soru)){
                    MainActivity.myTTS.stop();
                    ft.replace(R.id.fragment_tutucu1,new Level3Fragment());

                    GameActivity.mP.pause();

                    GameActivity.speak("You got it");

                    GameActivity.point+=5;
                    GameActivity.count++;
                    if (GameActivity.count == 3){
                        GameActivity.count = 0;
                        ft.replace(R.id.fragment_tutucu1,new Level4Fragment());
                    }

                    ft.commit();
                }else{
                    MainActivity.myTTS.stop();
                    GameActivity.count = 0;
                    GameActivity.mP.pause();
                    GameActivity.speak("Becareful");
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
}
