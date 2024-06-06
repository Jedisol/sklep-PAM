package com.example.shop;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ZamowieniaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zamowienia);

        displayOrders();
    }

    private void displayOrders() {
        OrderDbHelper dbHelper = new OrderDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                OrderContract.OrderEntry._ID,
                OrderContract.OrderEntry.COLUMN_RECIPIENT,
                OrderContract.OrderEntry.COLUMN_CAR,
                OrderContract.OrderEntry.COLUMN_WHEELS,
                OrderContract.OrderEntry.COLUMN_EQUIPMENT,
                OrderContract.OrderEntry.COLUMN_PAYMENT,
                OrderContract.OrderEntry.COLUMN_TOTAL_PRICE,
                OrderContract.OrderEntry.COLUMN_QUANTITY,
                OrderContract.OrderEntry.COLUMN_ORDER_DATE  // nowa kolumna
        };

        Cursor cursor = db.query(
                OrderContract.OrderEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        TextView ordersTextView = findViewById(R.id.ordersTextView);
        ordersTextView.setText("");

        try {
            while (cursor.moveToNext()) {
                long orderId = cursor.getLong(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry._ID));
                String recipient = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_RECIPIENT));
                String car = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_CAR));
                String wheels = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_WHEELS));
                String equipment = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_EQUIPMENT));
                String payment = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_PAYMENT));
                int totalPrice = cursor.getInt(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_TOTAL_PRICE));
                int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_QUANTITY));
                String orderDate = cursor.getString(cursor.getColumnIndexOrThrow(OrderContract.OrderEntry.COLUMN_ORDER_DATE));

                String orderText = "Zamówienie #" + orderId + ":\n" +
                        "Adresat: " + recipient + "\n" +
                        "Marka: " + car + "\n" +
                        "Felgi: " + wheels + "\n" +
                        "Wyposażenie: " + equipment + "\n" +
                        "Typ płatności: " + payment + "\n" +
                        "Ilość: " + String.valueOf(quantity) + "\n" +
                        "Do zapłaty: " + totalPrice + " zł\n" +
                        "Data zamówienia: " + orderDate + "\n\n";
                ordersTextView.append(orderText);
            }
        } finally {
            cursor.close();
        }
    }
}

