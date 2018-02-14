package com.example.faizan.fuelapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.faizan.fuelapp.OtpPOJO.OtpBean;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtpActivity extends AppCompatActivity {

    EditText otp;
    String OTP;
    Button verify;
    ProgressBar bar;
    SharedPreferences pref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


        otp = (EditText) findViewById(R.id.otp);
        verify = (Button) findViewById(R.id.verify);
        bar = (ProgressBar) findViewById(R.id.progress);


        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();
        OTP = getIntent().getStringExtra("OTP");
        otp.setText(OTP);


        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String p = otp.getText().toString();
                if (!TextUtils.isEmpty(p)){

                    bar.setVisibility(View.VISIBLE);
                    final Bean b = (Bean) getApplicationContext();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(b.baseURL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Allapi cr = retrofit.create(Allapi.class);
                    Call<OtpBean> call = cr.verify(b.userId, p);
                    call.enqueue(new Callback<OtpBean>() {
                        @Override
                        public void onResponse(Call<OtpBean> call, Response<OtpBean> response) {
                            if (Objects.equals(response.body().getStatus(), "1")){

                                bar.setVisibility(View.GONE);
                                Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                edit.putString("userId", response.body().getData().getUserId());
                                edit.apply();
                                Intent intent = new Intent(OtpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(OtpActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                bar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<OtpBean> call, Throwable t) {

                            bar.setVisibility(View.GONE);
                        }
                    });

                }else {
                    otp.setError("Field is Empty");
                    otp.requestFocus();
                }
            }
        });
    }
}
