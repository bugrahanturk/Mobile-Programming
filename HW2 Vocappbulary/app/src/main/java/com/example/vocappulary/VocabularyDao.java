package com.example.vocappulary;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class VocabularyDao {


    public void kelimeEkle(Database vt, String english, String turkish, String sentence, String synonym, String antonym, String type_id) {

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues degerler = new ContentValues();

        degerler.put("english", english);
        degerler.put("turkish", turkish);
        degerler.put("sentence", sentence);
        degerler.put("synonym", synonym);
        degerler.put("antonym", antonym);
        degerler.put("voc_type_id", type_id);

        dbx.insertOrThrow("Vocabulary", null, degerler);
        dbx.close();

    }


    public ArrayList<Vocabulary> verbs(Database vt) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = 1 and Vocabulary.voc_type_id = Vocabulary_type.voc_type_id", null);

        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type,
                    c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);

        }
        return vocabularyArrayList;
    }


    public ArrayList<Vocabulary> adverbs(Database vt) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = 2 and Vocabulary.voc_type_id = Vocabulary_type.voc_type_id", null);


        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type,
                    c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }

    public ArrayList<Vocabulary> adjectives(Database vt) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = 3 and Vocabulary.voc_type_id = Vocabulary_type.voc_type_id", null);


        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type,
                    c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }

    public ArrayList<Vocabulary> phrases_idioms(Database vt) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = 4 and Vocabulary.voc_type_id = Vocabulary_type.voc_type_id", null);


        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type,
                    c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }

    public ArrayList<Vocabulary> user_own_list(Database vt) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = 5 and Vocabulary.voc_type_id = Vocabulary_type.voc_type_id", null);


        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type
                    , c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }


    public ArrayList<Vocabulary> kelime_ara(Database vt, String arananKelime) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();
        //kategoriye göre
        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = Vocabulary_type.voc_type_id and english like '%" + arananKelime + "%'", null);

        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type, c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }


    public int VeriSayisi(Database vt) {
        int sayi = 0;

        SQLiteDatabase dbx = vt.getWritableDatabase();

        Cursor c = dbx.rawQuery("SELECT COUNT(*) as VeriSayisi FROM Vocabulary", null);

        while (c.moveToNext()) {
            sayi = c.getInt(c.getColumnIndex("VeriSayisi"));
        }
        return sayi;

    }

    public int user_list_size(Database vt) {
        int sayi = 0;

        SQLiteDatabase dbx = vt.getWritableDatabase();

        Cursor c = dbx.rawQuery("SELECT COUNT(*) as user_list_size FROM Vocabulary WHERE Vocabulary.voc_type_id = 5", null);

        while (c.moveToNext()) {
            sayi = c.getInt(c.getColumnIndex("user_list_size"));
        }
        return sayi;

    }

    public void kelimeSil(Database vt, int voc_id) {
        SQLiteDatabase dbx = vt.getWritableDatabase();
        dbx.delete("Vocabulary", "voc_id = ?", new String[]{String.valueOf(voc_id)});
        dbx.close();
    }


    public ArrayList<Vocabulary> get_wrong_choices(Database vt, int voc_id, int category) {
        ArrayList<Vocabulary> vocabularyArrayList = new ArrayList<>();

        SQLiteDatabase db = vt.getWritableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Vocabulary,Vocabulary_type WHERE Vocabulary.voc_type_id = Vocabulary_type.voc_type_id and Vocabulary.voc_type_id = " + category + " and voc_id!=" + voc_id + " ORDER BY RANDOM() ", null);


        while (c.moveToNext()) {
            Vocabulary_type vocabulary_type = new Vocabulary_type(c.getInt(c.getColumnIndex("voc_type_id")),
                    c.getString(c.getColumnIndex("description")));
            Vocabulary v = new Vocabulary(c.getInt(c.getColumnIndex("voc_id")),
                    c.getString(c.getColumnIndex("english")),
                    c.getString(c.getColumnIndex("turkish")), c.getString(c.getColumnIndex("sentence")),
                    c.getString(c.getColumnIndex("synonym")),
                    c.getString(c.getColumnIndex("antonym")), vocabulary_type,
                    c.getBlob(c.getColumnIndex("image")));

            vocabularyArrayList.add(v);
        }
        return vocabularyArrayList;
    }

    public void resimUpdate(Database vt, byte[] image, int voc_id) {

        SQLiteDatabase dbx = vt.getWritableDatabase();
        ContentValues degerler = new ContentValues();

        degerler.put("image", image);

        dbx.update("Vocabulary", degerler, "voc_id = ?", new String[]{String.valueOf(voc_id)});
        dbx.close();

    }

}
