package com.example.vocappulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    private TextView tv_dogru, tv_yanlis, tv_soru_sayısı, tv_category, tv_soru;
    private Button btn_a, btn_b, btn_c, btn_d, btn_e;
    private int category;
    private ArrayList<Vocabulary> questionsList;
    private ArrayList<Vocabulary> wrong_choicesList;
    private Vocabulary correctChoice;
    private Database db;

    private int question_counter = 0;
    private int wrong_counter = 0;
    private int correct_counter = 0;

    private Animation uptodown;

    private HashSet<Vocabulary> mix_questionList = new HashSet<>();
    private ArrayList<Vocabulary> choicesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tv_dogru = findViewById(R.id.tv_dogru);
        tv_yanlis = findViewById(R.id.tv_yanlis);
        tv_soru_sayısı = findViewById(R.id.tv_soru_sayisi);
        tv_category = findViewById(R.id.tv_category);
        tv_soru = findViewById(R.id.tv_soru);
        btn_a = findViewById(R.id.btn_a);
        btn_b = findViewById(R.id.btn_b);
        btn_c = findViewById(R.id.btn_c);
        btn_d = findViewById(R.id.btn_d);
        btn_e = findViewById(R.id.btn_e);

        uptodown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.uptodown);

        db = new Database(this);

        category = getIntent().getIntExtra("quiz_category", 0);

        if (category == 1) {
            questionsList = new VocabularyDao().verbs(db);
        } else if (category == 2) {
            questionsList = new VocabularyDao().adverbs(db);
        } else if (category == 3) {
            questionsList = new VocabularyDao().adjectives(db);
        } else if (category == 4) {
            questionsList = new VocabularyDao().phrases_idioms(db);
        } else if (category == 5) {
            questionsList = new VocabularyDao().user_own_list(db);
        }

        setQuestions();


        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCheck(btn_a);
                counter_check();
            }
        });
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCheck(btn_b);
                counter_check();
            }
        });
        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCheck(btn_c);
                counter_check();
            }
        });
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCheck(btn_d);
                counter_check();
            }
        });
        btn_e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                correctCheck(btn_e);
                counter_check();
            }
        });


    }

    public void setQuestions() {

        btn_a.setAnimation(uptodown);
        btn_b.setAnimation(uptodown);
        btn_c.setAnimation(uptodown);
        btn_d.setAnimation(uptodown);
        btn_e.setAnimation(uptodown);

        Random random = new Random();
        int r = random.nextInt(10);

        question_counter++;
        tv_soru_sayısı.setText(question_counter + " .Soru");

        correctChoice = questionsList.get(r);

        wrong_choicesList = new VocabularyDao().get_wrong_choices(db, correctChoice.getVoc_id(), category);
        tv_soru.setText(correctChoice.getEnglish());

        mix_questionList.clear();
        mix_questionList.add(correctChoice);

        mix_questionList.add(wrong_choicesList.get(0));
        mix_questionList.add(wrong_choicesList.get(1));
        mix_questionList.add(wrong_choicesList.get(2));
        mix_questionList.add(wrong_choicesList.get(3));

        choicesList.clear();

        for (Vocabulary v : mix_questionList) {
            choicesList.add(v);
        }

        btn_a.setText(choicesList.get(0).getTurkish());
        btn_b.setText(choicesList.get(1).getTurkish());
        btn_c.setText(choicesList.get(2).getTurkish());
        btn_d.setText(choicesList.get(3).getTurkish());
        btn_e.setText(choicesList.get(4).getTurkish());

    }

    public void correctCheck(Button button) {
        String btn_text = button.getText().toString();
        String correct_choice = correctChoice.getTurkish();

        if (btn_text.equalsIgnoreCase(correct_choice)) {
            correct_counter++;
        } else {
            wrong_counter++;
        }

        tv_dogru.setText("Doğru: " + correct_counter);
        tv_yanlis.setText("Yanlış: " + wrong_counter);
    }

    public void counter_check() {
        if (question_counter != 5) {
            setQuestions();
        } else {
            Intent intent = new Intent(QuizActivity.this, ResultActivity.class);
            intent.putExtra("correct_counter", correct_counter);
            intent.putExtra("wrong_counter", wrong_counter);
            startActivity(intent);
            finish();
        }
    }

}