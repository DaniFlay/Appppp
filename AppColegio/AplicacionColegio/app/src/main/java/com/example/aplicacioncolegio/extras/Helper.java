package com.example.aplicacioncolegio.extras;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Helper extends SQLiteOpenHelper {
    String creacion="create table usuario(id integer primary key autoincrement, nombre text, apellidos text, sexo text, puesto text, ausente number, password text)";
    String borrado= "drop table if exists usuario";
    public Helper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(creacion);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(borrado);
        db.execSQL(creacion);
    }
}
