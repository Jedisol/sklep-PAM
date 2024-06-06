package com.example.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Spinner spinner1, spinner2, spinner3, spinner0;
    private SeekBar seekBar;
    private TextView totalPriceTextView;
    private CheckBox checkbox1, checkbox2;
    private Button orderButton, zamowieniaButton;
    private EditText editTextRecipient;



    // Ceny opcji spinnerów
    private int[] pricesSpinner0 = {10000, 20000, 1000};
    private int[] pricesSpinner1 = {500, 4000, 1000};
    private int[] pricesSpinner2 = {0, 2000, 5000};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner0 = findViewById(R.id.spinner0);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

        String[] optionsSpinner0 = {"BMW 10000zl", "Audi 20000zl", "Peugeot 1000zl"};
        String[] optionsSpinner1 = {"Standardowe Stalufelgi 500zl", "Luksusowe Alusy 4000zl", "Chińskie BBSy 1000zl"};
        String[] optionsSpinner2 = {"Pakiet 'Bieda' 0zl", "Pakiet 'Premium' 2000zl", "Pakiet 'Szport' 5000zl"};
        String[] optionsSpinner3 = {"Gotówka","Karta","Leasing"};

        int[] imageResourcesSpinner0 = {R.drawable.spinner0_1, R.drawable.spinner0_2, R.drawable.spinner0_3,};
        int[] imageResourcesSpinner1 = {R.drawable.spinner1_1, R.drawable.spinner1_2, R.drawable.spinner1_3,};
        // Inicjalizacja adaptatorów spinnera
        // Utwórz adaptery
        CustomSpinnerAdapter adapterSpinner0 = new CustomSpinnerAdapter(this, optionsSpinner0, imageResourcesSpinner0);
        CustomSpinnerAdapter adapterSpinner1 = new CustomSpinnerAdapter(this, optionsSpinner1, imageResourcesSpinner1);
// Dodaj kolejne dla innych spinnerów
        ArrayAdapter<String> adapterSpinner2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionsSpinner2);
        ArrayAdapter<String> adapterSpinner3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, optionsSpinner3);

// Konfiguracja dla rozwijanych widoków spinnera
        adapterSpinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpinner3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Przypisz adaptery
        spinner0.setAdapter(adapterSpinner0);
        spinner1.setAdapter(adapterSpinner1);
// Dodaj kolejne dla innych spinnerów
        spinner2.setAdapter(adapterSpinner2);
        spinner3.setAdapter(adapterSpinner3);

        totalPriceTextView = findViewById(R.id.totalPrice);
        seekBar = findViewById(R.id.seekBar);
        checkbox1 = findViewById(R.id.checkbox1);
        checkbox2 = findViewById(R.id.checkbox2);
        orderButton = findViewById(R.id.zamow);
        zamowieniaButton = findViewById(R.id.zamowienia);
        editTextRecipient = findViewById(R.id.editTextRecipient);


        zamowieniaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ZamowieniaActivity.class);
            startActivity(intent);
        });


        orderButton.setOnClickListener(v -> {
            int totalPrice = Integer.parseInt(totalPriceTextView.getText().toString().replaceAll("[\\D]", ""));
            String car = spinner0.getSelectedItem().toString();
            String wheels = spinner1.getSelectedItem().toString();
            String equipment = spinner2.getSelectedItem().toString();
            String payment = spinner3.getSelectedItem().toString();
            String recipient = editTextRecipient.getText().toString();

            saveOrderToDatabase(recipient,totalPrice, car, wheels, equipment,  payment,seekBar.getProgress());
            Toast.makeText(MainActivity.this, "Zamówienie złożone!", Toast.LENGTH_SHORT).show();
        });


        // Obsługa zmiany wartości na SeekBarze
        TextView seekBarValueText = findViewById(R.id.seekBarValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarValueText.setText("Ilość: "+String.valueOf(progress+1));
                updateTotalPrice();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        checkbox1.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotalPrice());

        checkbox2.setOnCheckedChangeListener((buttonView, isChecked) -> updateTotalPrice());

        spinner0.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        // Obsługa zmiany wybranej opcji w spinnerze 1
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateTotalPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
        updateTotalPrice();
    }
    private void updateTotalPrice() {
        int totalPrice = 0;
        totalPrice += pricesSpinner0[spinner0.getSelectedItemPosition()];
        if (checkbox1.isChecked()) {
            totalPrice += pricesSpinner1[spinner1.getSelectedItemPosition()];
        }
        if (checkbox2.isChecked()) {
            totalPrice += pricesSpinner2[spinner2.getSelectedItemPosition()];
        }
        totalPrice *= seekBar.getProgress() + 1;
        totalPriceTextView.setText("Cena całkowita: " + totalPrice + ".00 zł");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item1) {
            return true;
        } else if (id == R.id.menu_item2) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveOrderToDatabase(String recipient,int totalPrice, String car, String wheels, String equipment, String payment, int quantity) {
        OrderDbHelper dbHelper = new OrderDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OrderContract.OrderEntry.COLUMN_TOTAL_PRICE, totalPrice);

        // Dodaj wartości zależne od stanu checkboxów
        values.put(OrderContract.OrderEntry.COLUMN_RECIPIENT,recipient);
        values.put(OrderContract.OrderEntry.COLUMN_CAR, car);
        values.put(OrderContract.OrderEntry.COLUMN_WHEELS, wheels);
        values.put(OrderContract.OrderEntry.COLUMN_EQUIPMENT, equipment);
        values.put(OrderContract.OrderEntry.COLUMN_QUANTITY, quantity);
        values.put(OrderContract.OrderEntry.COLUMN_PAYMENT, payment);

        // Dodaj aktualną datę
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        values.put(OrderContract.OrderEntry.COLUMN_ORDER_DATE, currentDate);

        long newRowId = db.insert(OrderContract.OrderEntry.TABLE_NAME, null, values);
    }
}


