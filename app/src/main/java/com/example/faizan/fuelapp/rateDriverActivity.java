package com.example.faizan.fuelapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class rateDriverActivity extends AppCompatActivity {

    Toolbar rateToolbar;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_driver);
        rateToolbar = (Toolbar)findViewById(R.id.rateToolbar);
        setSupportActionBar(rateToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        rateToolbar.setNavigationIcon(R.drawable.arrow);

        submit = (Button)findViewById(R.id.submitRating);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(rateDriverActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        rateToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
