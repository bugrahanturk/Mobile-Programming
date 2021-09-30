package com.example.vocappulary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class DetailActivity extends AppCompatActivity {
    private TextView tv_word, tv_definition, tv_sentence, tv_synonym, tv_antonym;
    private Vocabulary vocabulary;
    private ImageView img;
    private Button btn;
    private Button listen;
    private int SELECT_PICTURE = 200;
    private Database db;
    private Bitmap img_thumb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        db = new Database(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        vocabulary = (Vocabulary) getIntent().getSerializableExtra("object");

        listen = findViewById(R.id.btn_speak);


        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(vocabulary.getEnglish());
            }
        });

        btn = findViewById(R.id.btn_change);
        img = findViewById(R.id.imgView_resim);
        tv_word = findViewById(R.id.tv_word);
        tv_definition = findViewById(R.id.tv_definition);
        tv_sentence = findViewById(R.id.tv_sentence);
        tv_synonym = findViewById(R.id.tv_synonym);
        tv_antonym = findViewById(R.id.tv_antonym);

        img.setVisibility(View.GONE);
        btn.setVisibility(View.GONE);

        if (MainActivity.btn_text.equalsIgnoreCase("user own list")) {
            img.setVisibility(View.VISIBLE);
            btn.setVisibility(View.VISIBLE);
        }


        if (vocabulary.getImage() == null) {
            System.out.println("NULL");
            img.setImageResource(R.drawable.noimg);
        } else {
            Bitmap bm = BitmapFactory.decodeByteArray(vocabulary.getImage(), 0, vocabulary.getImage().length);
            img.setImageBitmap(bm);
        }

        tv_word.setText(vocabulary.getEnglish());
        tv_definition.setText(vocabulary.getTurkish());
        tv_sentence.setText(vocabulary.getSentence());
        tv_synonym.setText(vocabulary.getSynonym());
        tv_antonym.setText(vocabulary.getAntonym());


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    public static void speak(String text) {
        CategoryActivity.myTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {

            if (requestCode == SELECT_PICTURE) {

                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    img.setImageURI(selectedImageUri);
                    Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    byte[] bytesImage = byteArrayOutputStream.toByteArray();
                    new VocabularyDao().resimUpdate(db, bytesImage, vocabulary.getVoc_id());

                }
            }
        }
    }


    public void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}