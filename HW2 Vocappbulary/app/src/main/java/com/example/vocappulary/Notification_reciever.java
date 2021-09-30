package com.example.vocappulary;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import java.util.List;
import java.util.Random;

public class Notification_reciever extends BroadcastReceiver {

    private Database db;
    private List<Vocabulary> vocabularyList;
    private int random;
    private int size;

    public Notification_reciever() {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        db = new Database(context);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        size = new VocabularyDao().user_list_size(db);

        Random rand = new Random();
        random = rand.nextInt(size);

        vocabularyList = new VocabularyDao().user_own_list(db);


        Vocabulary vocabulary = vocabularyList.get(random);

        NotificationCompat.Builder builder;


        Intent memory_intent = new Intent(context, MemoryActivity.class);
        memory_intent.putExtra("kelime", vocabulary.getEnglish());
        memory_intent.putExtra("anlami", vocabulary.getTurkish());
        memory_intent.putExtra("cumle", vocabulary.getSentence());
        memory_intent.putExtra("es", vocabulary.getSynonym());
        memory_intent.putExtra("zit", vocabulary.getAntonym());
        memory_intent.putExtra("id", vocabulary.getVoc_id());
        context.startService(memory_intent);


        memory_intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 100, memory_intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String kanalId = "kanalId";
            String kanalAd = "kanalAd";
            String kanalTanım = "kanalTanım";
            int kanalOnceligi = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel kanal = notificationManager.getNotificationChannel(kanalId);

            if (kanal == null) {
                kanal = new NotificationChannel(kanalId, kanalAd, kanalOnceligi);
                kanal.setDescription(kanalTanım);
                notificationManager.createNotificationChannel(kanal);
            }

            builder = new NotificationCompat.Builder(context, kanalId)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_add_24)
                    .setContentTitle(vocabulary.getEnglish())
                    .setContentText(vocabulary.getTurkish())
                    .setAutoCancel(true);

        } else {
            builder = new NotificationCompat.Builder(context)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_baseline_add_24)
                    .setContentTitle(vocabulary.getEnglish())
                    .setContentText(vocabulary.getTurkish())
                    .setAutoCancel(true);

        }

        notificationManager.notify(100, builder.build());

    }
}
