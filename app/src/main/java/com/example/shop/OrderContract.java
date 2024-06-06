package com.example.shop;

import android.provider.BaseColumns;

public final class OrderContract {

    private OrderContract() {
    }

    public static class OrderEntry implements BaseColumns {
        public static final String TABLE_NAME = "orders";
        public static final String COLUMN_RECIPIENT = "recipient";
        public static final String COLUMN_CAR = "car";
        public static final String COLUMN_WHEELS = "wheels";
        public static final String COLUMN_EQUIPMENT = "equipment";
        public static final String COLUMN_PAYMENT = "payment";
        public static final String COLUMN_TOTAL_PRICE = "total_price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_ORDER_DATE = "order_date";  // nowa kolumna
    }
}
