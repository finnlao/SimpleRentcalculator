package com.example.lolyf.simplerentcalculator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.util.ArrayList;

public class SummaryScreen extends AppCompatActivity {
    private Intent intentExtras;
    private Bundle extraBundles;
    private float[] prices, sizes;
    private ArrayList<String> names, types;
    private float totalSize, monthlyRent;
    private String currency, measurement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_screen);

        intentExtras = getIntent();
        extraBundles = intentExtras.getExtras();

        names = extraBundles.getStringArrayList("names");
        prices = extraBundles.getFloatArray("prices");
        types = extraBundles.getStringArrayList("types");
        sizes = extraBundles.getFloatArray("sizes");
        totalSize = extraBundles.getFloat("totalSize");
        monthlyRent = extraBundles.getFloat("monthlyRent");
        currency = extraBundles.getString("currency");
        measurement = extraBundles.getString("measurement");

        String message = "";
        for(int i = 0; i < prices.length; i++){
            message += names.get(i) + " should pay " + currency + prices[i] + "\n";
        }

        TextView messageView = findViewById(R.id.messageBox);
        messageView.setText(message);

        Button moreDetailsButton = findViewById(R.id.moreDetailsButton);
        moreDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String moreDetails = "";

                for (int i = 0; i < prices.length; i++) {
                    BigDecimal bd = new BigDecimal(Float.toString(sizes[i] / totalSize));
                    bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
                    float percentage = bd.floatValue() * 100;
                    moreDetails += "Since the " + types.get(i) + " room is " + sizes[i] + " " + measurement +  " which is " + percentage + "% of the total size, " + names.get(i) + " has to pay " + currency + prices[i]
                            + "\n";
                }

                TextView moreDetailsText = findViewById(R.id.moreDetailsText);
                moreDetailsText.setText(moreDetails);
            }
        });
    }
}
