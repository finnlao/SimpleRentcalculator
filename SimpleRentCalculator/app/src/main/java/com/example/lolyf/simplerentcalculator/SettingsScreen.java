package com.example.lolyf.simplerentcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SettingsScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_screen);

        final Spinner currenyDropdown = findViewById(R.id.currencyDropdown);
        String [] currencies = new String[] {"Dollars ($)", "Pounds (\u00a3)", "Euros (\u20ac)"};
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, currencies);
        currenyDropdown.setAdapter(currencyAdapter);

        final Spinner measurementDropDown = findViewById(R.id.measurementDropdown);
        String [] measurement = new String[] {"Sq. ft", "Sq. m"};
        ArrayAdapter<String> measurementAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, measurement);
        measurementDropDown.setAdapter(measurementAdapter);


        final Button applyButton = findViewById(R.id.applyButton);

        applyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBundle = new Intent(SettingsScreen.this, WelcomeScreen.class);
                Bundle bundle = new Bundle();

                bundle.putString("currency", currenyDropdown.getSelectedItem().toString());
                bundle.putString("measurement", measurementDropDown.getSelectedItem().toString());

                intentBundle.putExtras(bundle);
                startActivity(intentBundle);
            }
        });

    }
}
