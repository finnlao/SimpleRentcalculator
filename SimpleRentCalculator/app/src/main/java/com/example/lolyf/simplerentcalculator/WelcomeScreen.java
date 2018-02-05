package com.example.lolyf.simplerentcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.ArrayList;

public class WelcomeScreen extends AppCompatActivity {

    private Intent intentExtras;
    private Bundle extraBundles;
    private String currency, measurement;
    private ArrayList<Room> rooms = new ArrayList<>();
    private float totalSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        intentExtras = getIntent();
        extraBundles = intentExtras.getExtras();
        if (extraBundles == null || extraBundles.isEmpty()){
            currency = "$";
            measurement = "Sq. ft";
        } else{
            currency = extraBundles.getString("currency");
            measurement = extraBundles.getString("measurement");

            if(currency.contains("Dollars")){
                currency = "$";
            } else if (currency.contains("Euros")){
                currency = "\u00a3";
            } else {
                currency = "\u20ac";
            }
        }

        final TextView sizeTextView = findViewById(R.id.sizeLabel);
        final TextView monthlyRentTextView = findViewById(R.id.monthlyRentLabel);

        sizeTextView.setText(sizeTextView.getText().toString() + "(" + measurement + ")");
        monthlyRentTextView.setText(monthlyRentTextView.getText().toString() + "(" + currency + ")");

        ListView roomList = findViewById(R.id.roomInfoView);
        Button addRoomButton =  findViewById(R.id.addRoomButton);
        Button calculateButton = findViewById(R.id.calculate);
        Button settingsButton = findViewById(R.id.settingsButton);


        final EditText sizeEditText = findViewById(R.id.sizeText);
        final EditText typeEditText = findViewById(R.id.typeEditText);
        final EditText nameEditText = findViewById(R.id.roommateName);
        final EditText monthlyRentEditText = findViewById(R.id.messageBox);

        sizeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                sizeEditText.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        typeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                typeEditText.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                nameEditText.setTextColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        final MyAdapter adapter = new MyAdapter(this, rooms);
        roomList.setAdapter(adapter);
        addRoomButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean valid = true;

                        if(sizeEditText.getText().length() == 0){
                            sizeEditText.setText("REQUIRED");
                            sizeEditText.setTextColor(Color.RED);
                            valid = false;
                        }

                        if(typeEditText.getText().length() == 0){
                            typeEditText.setText("REQUIRED");
                            typeEditText.setTextColor(Color.RED);
                            valid = false;
                        }

                        if(nameEditText.getText().length() == 0){
                            nameEditText.setText("REQUIRED");
                            nameEditText.setTextColor(Color.RED);
                            valid = false;
                        }

                        if(valid) {
                            float size = 0;
                            try {
                                size = Float.parseFloat(sizeEditText.getText().toString());
                            } catch (NumberFormatException ex){
                                AlertDialog alertDialog = new AlertDialog.Builder(WelcomeScreen.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please enter a number for the room size!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();

                                return;
                            }
                            String type = typeEditText.getText().toString();
                            String name = nameEditText.getText().toString();

                            totalSize += size;
                            Room room = new Room(size, type, name);
                            rooms.add(room);

                            sizeEditText.setText("");
                            typeEditText.setText("");
                            nameEditText.setText("");

                            adapter.notifyDataSetChanged();
                        }
                    }
                }
        );

        calculateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean valid = true;

                        if(monthlyRentEditText.getText().length() == 0){
                            monthlyRentEditText.setTextColor(Color.RED);
                            monthlyRentEditText.setText("REQUIRED");
                            valid = false;
                        }

                        if(rooms.size() == 0){
                            AlertDialog alertDialog = new AlertDialog.Builder(WelcomeScreen.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("You have not entered any room information!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                            valid = false;
                        }


                        if (valid) {
                            float totalCost = 0, monthlyRent = 0;
                            try {
                                monthlyRent = Float.parseFloat(monthlyRentEditText.getText().toString());
                            } catch (NumberFormatException ex){
                                AlertDialog alertDialog = new AlertDialog.Builder(WelcomeScreen.this).create();
                                alertDialog.setTitle("Error");
                                alertDialog.setMessage("Please enter a number for the monthly rent!");
                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                alertDialog.show();
                                return;
                            }
                            for (int i = 0; i < rooms.size() - 1; i++) {
                                Room room = rooms.get(i);
                                float price = (room.getSize() / totalSize) * monthlyRent;
                                totalCost += price;
                                BigDecimal bd = new BigDecimal(Float.toString(price));
                                bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                                price = bd.floatValue();
                                room.setPrice(price);
                                rooms.set(i, room);
                            }

                            Room room = rooms.get(rooms.size() - 1);
                            room.setPrice(monthlyRent - totalCost);
                            rooms.set(rooms.size() - 1, room);

                            Intent intentBundle = new Intent(WelcomeScreen.this, SummaryScreen.class);
                            if (extraBundles == null){
                                extraBundles = new Bundle();
                            }

                            ArrayList<String> names = new ArrayList<>();
                            ArrayList<String> types = new ArrayList<>();
                            float[] sizes = new float[rooms.size()];
                            float[] prices = new float[rooms.size()];

                            for (int i = 0; i < rooms.size(); i++) {
                                names.add(rooms.get(i).getName());
                                types.add(rooms.get(i).getType());
                                sizes[i] = rooms.get(i).getSize();
                                prices[i] = rooms.get(i).getPrice();
                            }

                            extraBundles.putStringArrayList("names", names);
                            extraBundles.putStringArrayList("types", types);
                            extraBundles.putFloatArray("sizes", sizes);
                            extraBundles.putFloatArray("prices", prices);
                            extraBundles.putFloat("monthlyRent", monthlyRent);
                            extraBundles.putFloat("totalSize", totalSize);
                            extraBundles.putString("currency", currency);
                            extraBundles.putString("measurement", measurement);

                            intentBundle.putExtras(extraBundles);
                            startActivity(intentBundle);
                        }
                    }
                }
        );
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentBundle = new Intent(WelcomeScreen.this, SettingsScreen.class);
                startActivity(intentBundle);
            }
        });

    }
}
