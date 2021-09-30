package com.example.vocappulary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    private TextView tv_dogruYanlis, tv_sonuc;
    private Button btn_menu;
    private int result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_dogruYanlis = findViewById(R.id.tv_dogruYanlis);
        tv_sonuc = findViewById(R.id.tv_sonuc);

        btn_menu = findViewById(R.id.btn_menu);

        int correct = getIntent().getIntExtra("correct_counter", 0);
        int wrong = getIntent().getIntExtra("wrong_counter", 0);
        System.out.println(correct + "" + wrong);

        result = correct * 20;
        tv_dogruYanlis.setText(correct + " Doğru " + wrong + " Yanlış");
        tv_sonuc.setText("%" + result + " Başarı");

        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResultActivity.this, CategoryActivity.class));
                finish();
            }
        });

    }
}