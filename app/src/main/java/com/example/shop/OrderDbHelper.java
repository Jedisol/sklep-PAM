package com.example.shop;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class OrderDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 2;  // Zwiększ numer wersji bazy danych
    public static final String DATABASE_NAME = "Orders.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + OrderContract.OrderEntry.TABLE_NAME + " (" +
                    OrderContract.OrderEntry._ID + " INTEGER PRIMARY KEY," +
                    OrderContract.OrderEntry.COLUMN_RECIPIENT + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_CAR + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_WHEELS + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_EQUIPMENT + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_PAYMENT + " TEXT," +
                    OrderContract.OrderEntry.COLUMN_TOTAL_PRICE + " INTEGER," +
                    OrderContract.OrderEntry.COLUMN_QUANTITY + " INTEGER," +
                    OrderContract.OrderEntry.COLUMN_ORDER_DATE + " TEXT)";  // nowa kolumna

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + OrderContract.OrderEntry.TABLE_NAME;

    public OrderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}