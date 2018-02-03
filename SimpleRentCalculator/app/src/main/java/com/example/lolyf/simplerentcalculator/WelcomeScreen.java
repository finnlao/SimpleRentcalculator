package com.example.lolyf.simplerentcalculator;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

public class WelcomeScreen extends AppCompatActivity {

    ArrayList<Room> rooms = new ArrayList<>();
    float totalSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);


        ListView roomList = findViewById(R.id.roomInfoView);
        Button addRoomButton =  findViewById(R.id.addRoomButton);
        Button calculateButton = findViewById(R.id.calculate);




        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        final MyAdapter adapter = new MyAdapter(this, rooms);
        roomList.setAdapter(adapter);
        addRoomButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText sizeEditText = findViewById(R.id.sizeText);
                        EditText typeEditText = findViewById(R.id.typeEditText);
                        EditText nameEditText = findViewById(R.id.roommateName);

                        float size = Float.parseFloat(sizeEditText.getText().toString());
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
        );

        calculateButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        EditText monthlyRentEditText = findViewById(R.id.monthlyRent);
                        float totalCost = 0;
                        float monthlyRent = Float.parseFloat(monthlyRentEditText.getText().toString());
                        for(int i = 0; i < rooms.size() - 1; i++){
                            Room room = rooms.get(i);
                            float percentage = totalSize / room.getSize();
                            BigDecimal bd = new BigDecimal(Float.toString(percentage));
                            bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                            percentage = bd.floatValue();
                            totalCost += percentage*monthlyRent;
                            room.setPrice(percentage*monthlyRent);
                            rooms.set(i, room);
                        }

                        Room room = rooms.get(rooms.size()-1);
                        room.setPrice(monthlyRent - totalCost);
                        rooms.set(rooms.size()-1, room);

                        monthlyRentEditText.setText(String.valueOf(room.getPrice()));
                    }
                }
        );


    }
}
