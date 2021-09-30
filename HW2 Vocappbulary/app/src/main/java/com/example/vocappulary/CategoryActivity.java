package com.example.vocappulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private Spinner spinner;
    private ArrayList<String> category = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;
    private Button button_quiz;
    private int size;
    private Database db;
    public static TextToSpeech myTTS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        myTTS = new TextToSpeech(this, this);
        myTTS.setSpeechRate((float) 0.8);

        db = new Database(this);

        button_quiz = findViewById(R.id.btn_quiz);

        spinner = findViewById(R.id.spinner);
        category.add("Verbs");
        category.add("Adverbs");
        category.add("Adjectives");
        category.add("Phrases and Idioms");
        category.add("User own List");

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, category);

        spinner.setAdapter(arrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(),"Seçilen category: "+category.get(position),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                size = new VocabularyDao().user_list_size(db);
                if (category.get(spinner.getSelectedItemPosition()).equalsIgnoreCase("user own list") && size < 10) {
                    Toast.makeText(getApplicationContext(), "You should add 10 items into user list in order to quiz: ", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
                    intent.putExtra("quiz_category", (int) (spinner.getSelectedItemId() + 1));
                    startActivity(intent);
                }

            }
        });
    }

    public void btn_clicked(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
        intent.putExtra("category", buttonText);
        startActivity(intent);
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            myTTS.setLanguage(Locale.ENGLISH);
            //Toast.makeText(this,"TTS is ready",Toast.LENGTH_SHORT).show();
        }


    }
}