package com.example.ongaku.utilities;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context){
        super(context, "ongaku_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilities.CREATE_LIST_TABLE);
        db.execSQL(Utilities.CREATE_SONG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS list");
        db.execSQL("DROP TABLE IF EXISTS  song");
        onCreate(db);
    }

    public void deleteList(int id, Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM list WHERE id = '" + id + "''");
        Toast.makeText(context, "La lista se borro satisfactoriamente", Toast.LENGTH_SHORT).show();
    }
}
