package com.example.vocappulary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MemoryActivity extends AppCompatActivity {

    private TextView eng;
    private TextView tr;
    private TextView sent;
    private TextView syn;
    private TextView anty;

    private String kelime;
    private String anlami;
    private String cumle;
    private String es;
    private String zit;
    private int id;

    private Button btn_done;
    private Button btn_later;

    private Database db;
    private List<Vocabulary> vocabularyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);

        eng = findViewById(R.id.tv_english_mem);
        tr = findViewById(R.id.tv_turkish_mem);
        sent = findViewById(R.id.tv_sentence_mem);
        syn = findViewById(R.id.tv_synonym_mem);
        anty = findViewById(R.id.tv_antonym_mem);

        btn_done = findViewById(R.id.btn_done);
        btn_later = findViewById(R.id.btn_later);

        kelime = getIntent().getStringExtra("kelime");
        anlami = getIntent().getStringExtra("anlami");
        cumle = getIntent().getStringExtra("cumle");
        es = getIntent().getStringExtra("es");
        zit = getIntent().getStringExtra("zit");
        id = getIntent().getIntExtra("id", 0);

        eng.setText(kelime);
        tr.setText(anlami);
        sent.setText(cumle);
        syn.setText(es);
        anty.setText(zit);

        System.out.println(kelime + anlami + cumle + es + zit + id);

        btn_later.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, " Peki biraz daha çalışabilirsin!", Snackbar.LENGTH_SHORT).show();
                //MemoryActivity.super.onBackPressed();
            }
        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, " Mükemmel bu kelimeyi artık öğrendin!!!", Snackbar.LENGTH_SHORT).show();
                new VocabularyDao().kelimeSil(db, id);
                //MemoryActivity.super.onBackPressed();
            }
        });


    }
}