package com.example.vocappulary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class VocabularyAdapter extends RecyclerView.Adapter<VocabularyAdapter.CardHolder> {
    private Context mContext;
    private List<Vocabulary> vocabularyList;
    private List<Vocabulary> userVList;
    private VocabularyDao vocabularyDao;
    private Database vt;
    private RecyclerView rv;
    private boolean already = false;
    private Animation downtoup;

    public VocabularyAdapter(Context mContext, List<Vocabulary> vocabularyList, Database vt, RecyclerView rv) {
        this.mContext = mContext;
        this.vocabularyList = vocabularyList;
        this.vt = vt;
        this.rv = rv;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_tasarim, parent, false);

        return new CardHolder(view);

    }


    @Override
    public void onBindViewHolder(CardHolder holder, int position) {

        downtoup = AnimationUtils.loadAnimation(mContext,R.anim.downtoup);

        Vocabulary vocabulary = vocabularyList.get(position);

        vt = new Database(mContext);

        if (MainActivity.btn_text.equalsIgnoreCase("user own list")) {

            holder.tv_ingilizce.setText(vocabulary.getEnglish());
            holder.tv_turkce.setText(vocabulary.getTurkish());

            holder.imgView_add.setImageResource(R.drawable.ic_baseline_delete_24);

            holder.imgView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new VocabularyDao().kelimeSil(vt, vocabulary.getVoc_id());
                    Snackbar.make(v, vocabulary.getEnglish() + " Silindi!", Snackbar.LENGTH_SHORT).show();

                    vocabularyList = new VocabularyDao().user_own_list(vt);

                    rv.setAdapter(new VocabularyAdapter(mContext, vocabularyList, vt, rv));

                }
            });

        } else {
            holder.tv_ingilizce.setText(vocabulary.getEnglish());
            holder.tv_turkce.setText(vocabulary.getTurkish());

            holder.imgView_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userVList = new VocabularyDao().user_own_list(vt);

                    for (int i = 0; i < userVList.size(); i++) {
                        if (userVList.get(i).getEnglish().equalsIgnoreCase(vocabulary.getEnglish())) {
                            Snackbar.make(v, "Kelime listenizde bulunuyor !", Snackbar.LENGTH_SHORT).show();
                            already = true;

                            rv.setAdapter(new VocabularyAdapter(mContext, vocabularyList, vt, rv));
                        }
                    }
                    if (!already) {
                        new VocabularyDao().kelimeEkle(vt, vocabulary.getEnglish(), vocabulary.getTurkish(),
                                vocabulary.getSentence(), vocabulary.getSynonym(), vocabulary.getAntonym(), String.valueOf(5));

                        Snackbar.make(v, vocabulary.getEnglish() + " Eklendi!", Snackbar.LENGTH_SHORT).show();
                    }

                }
            });
        }

        holder.kelime_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DetailActivity.class);

                intent.putExtra("object", vocabulary);


                mContext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {

        return vocabularyList.size();
    }


    public class CardHolder extends RecyclerView.ViewHolder {

        private TextView tv_ingilizce;
        private TextView tv_turkce;
        private CardView kelime_card;
        private ImageView imgView_add;

        public CardHolder(@NonNull View itemView) {
            super(itemView);
            tv_ingilizce = itemView.findViewById(R.id.tv_ingilizce);
            tv_turkce = itemView.findViewById(R.id.tv_turkce);
            kelime_card = itemView.findViewById(R.id.kelime_card);
            imgView_add = itemView.findViewById(R.id.imgView_add);

        }
    }


}
