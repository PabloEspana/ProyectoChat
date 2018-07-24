package com.pabloespana.proyectochat;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class PruebaDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "prueba.db";

    public static final String TABLA_MENSAJES = "mensajes";
    public static final String COLUMNA_ID = "_id";
    public static final String COLUMNA_REM = "remitente";
    public static final String COLUMNA_DES = "destinatario";
    public static final String COLUMNA_MSG = "mensaje";
    public static final String COLUMNA_ESTADO = "llega";


    private static final String SQL_CREAR  = "create table "
            + TABLA_MENSAJES + "(" + COLUMNA_ID  + " integer primary key autoincrement, "
            + COLUMNA_REM + " text not null, " + COLUMNA_DES + " text not null, "
            + COLUMNA_MSG + " text not null, " + COLUMNA_ESTADO + " text not null );";


    public PruebaDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREAR);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void agregar(String remitente, String destino, String msg){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMNA_REM, remitente);
        values.put(COLUMNA_DES, destino);
        values.put(COLUMNA_MSG, msg);
        values.put(COLUMNA_ESTADO, "false");
        db.insert(TABLA_MENSAJES, null, values);
        db.close();
    }
}
