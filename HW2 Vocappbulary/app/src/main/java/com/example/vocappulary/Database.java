package com.example.vocappulary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "vocabulary.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(String.format("CREATE TABLE IF NOT EXISTS \"Vocabulary\" (\n" +
                "\t\"voc_id\"\tINTEGER,\n" +
                "\t\"english\"\tTEXT NOT NULL,\n" +
                "\t\"turkish\"\tTEXT NOT NULL,\n" +
                "\t\"sentence\"\tTEXT,\n" +
                "\t\"synonym\"\tTEXT,\n" +
                "\t\"antonym\"\tTEXT,\n" +
                "\t\"voc_type_id\"\tINTEGER NOT NULL,\n" +
                "\t\"image\"\tBLOB,\n" +
                "\tPRIMARY KEY(\"voc_id\" AUTOINCREMENT),\n" +
                "\tFOREIGN KEY(\"voc_type_id\") REFERENCES \"Vocabulary_type\"(\"voc_type_id\")\n" +
                ")"));

        db.execSQL("CREATE TABLE IF NOT EXISTS \"Vocabulary_type\" (\n" +
                "\t\"voc_type_id\"\tINTEGER,\n" +
                "\t\"description\"\tTEXT,\n" +
                "\tPRIMARY KEY(\"voc_type_id\")\n" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Vocabulary");
        db.execSQL("DROP TABLE IF EXISTS Vocabulary_type");
        onCreate(db);
    }
}
