package com.example.faizan.fuelapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class PaymentMode extends AppCompatActivity {

    Toolbar pmodetoolbar;
    Button countinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_mode);

        pmodetoolbar = (Toolbar)findViewById(R.id.pmodeToolbar);

        setSupportActionBar(pmodetoolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        pmodetoolbar.setTitleTextColor(Color.BLACK);
        pmodetoolbar.setNavigationIcon(R.drawable.arrow);

        pmodetoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        pmodetoolbar.setTitle("Select Payment Mode");

        countinue = (Button)findViewById(R.id.countinueBtn);

        countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PaymentMode.this,rateDriverActivity.class);
                startActivity(i);
            }
        });

    }
}
