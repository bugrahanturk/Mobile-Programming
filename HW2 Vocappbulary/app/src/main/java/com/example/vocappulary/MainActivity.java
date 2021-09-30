package com.example.vocappulary;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    private Toolbar toolbar;
    private RecyclerView rv;
    public static ArrayList<Vocabulary> vocabularyArrayList;
    private VocabularyAdapter adapter;
    private Database vt;
    private FloatingActionButton fab;
    private boolean already = false;

    public static String btn_text;
    private int adet;

    public void database_copy() {
        DatabaseCopyHelper databaseCopyHelper = new DatabaseCopyHelper(this);
        try {
            databaseCopyHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        databaseCopyHelper.openDataBase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        database_copy();

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rv = findViewById(R.id.rv);
        fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        toolbar.setTitle("VocAppulary");
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        vt = new Database(this);

        btn_text = getIntent().getStringExtra("category");

        if (btn_text.equalsIgnoreCase("verbs")) {
            vocabularyArrayList = new VocabularyDao().verbs(vt);
        } else if (btn_text.equalsIgnoreCase("adverbs")) {
            vocabularyArrayList = new VocabularyDao().adverbs(vt);
        } else if (btn_text.equalsIgnoreCase("adjectives")) {
            vocabularyArrayList = new VocabularyDao().adjectives(vt);
        } else if (btn_text.equalsIgnoreCase("phrases and idioms")) {
            vocabularyArrayList = new VocabularyDao().phrases_idioms(vt);
        } else if (btn_text.equalsIgnoreCase("User own list")) {
            fab.setVisibility(View.VISIBLE);
            vocabularyArrayList = new VocabularyDao().user_own_list(vt);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlert(v);
                }
            });
        }

        adapter = new VocabularyAdapter(this, vocabularyArrayList, vt, rv);

        rv.setAdapter(adapter);

        adet = new VocabularyDao().user_list_size(vt);

        if (adet > 0) {
            Calendar calendar = Calendar.getInstance();

            //FIRST NOTIFICATION
            calendar.set(Calendar.HOUR_OF_DAY, 5);
            calendar.set(Calendar.MINUTE, 27);
            calendar.set(Calendar.SECOND, 3);

            Intent intent = new Intent(getApplicationContext(), Notification_reciever.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            //SECOND NOTIFICATION
            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, 5);
            calendar2.set(Calendar.MINUTE, 28);
            calendar2.set(Calendar.SECOND, 3);

            intent = new Intent(getApplicationContext(), Notification_reciever.class);

            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem item = menu.findItem(R.id.action_ara);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        //Log.e("Gönderilen Arama",query);
        arama(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        //Log.e("Harf Girdikçe",newText);
        arama(newText);
        return false;
    }

    public void arama(String arananKelime) {
        vocabularyArrayList = new VocabularyDao().kelime_ara(vt, arananKelime);

        adapter = new VocabularyAdapter(this, vocabularyArrayList, vt, rv);

        rv.setAdapter(adapter);
    }

    public void showAlert(View view) {
        LayoutInflater layout = LayoutInflater.from(this);
        View alert = layout.inflate(R.layout.alert_design, null);

        EditText editText_ing = alert.findViewById(R.id.edit_text_ing);
        EditText editText_trk = alert.findViewById(R.id.editText_turk);
        EditText editText_sentence = alert.findViewById(R.id.editText_sentence);
        EditText editText_synonym = alert.findViewById(R.id.editText_synonym);
        EditText editText_antonym = alert.findViewById(R.id.editText_antonym);

        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setTitle("Yeni kelime ekle");
        ad.setView(alert);
        ad.setPositiveButton("Ekle", new DialogInterface.OnClickListener() {


            @Override
            public void onClick(DialogInterface dialog, int which) {

                String english = editText_ing.getText().toString().trim();
                String turkish = editText_trk.getText().toString().trim();
                String sentence = editText_sentence.getText().toString().trim();
                String synonym = editText_synonym.getText().toString().trim();
                String antonym = editText_antonym.getText().toString().trim();

                for (int i = 0; i < vocabularyArrayList.size(); i++) {
                    if (vocabularyArrayList.get(i).getEnglish().equalsIgnoreCase(english)) {
                        Snackbar.make(view, "Kelime listenizde bulunuyor !", Snackbar.LENGTH_SHORT).show();
                        already = true;
                    }
                }
                if (!already) {
                    new VocabularyDao().kelimeEkle(vt, english, turkish, sentence, synonym, antonym, String.valueOf(5));
                    Snackbar.make(view, "Yeni Kelime Eklendi!", Snackbar.LENGTH_SHORT).show();
                }

                vocabularyArrayList = new VocabularyDao().user_own_list(vt);

                rv.setAdapter(new VocabularyAdapter(MainActivity.this, vocabularyArrayList, vt, rv));

            }
        });

        ad.setNegativeButton("İptal", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ad.create().show();

    }


}