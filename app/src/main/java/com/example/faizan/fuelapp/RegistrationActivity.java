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
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizan.fuelapp.RegisterPOJO.RegisterBean;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RegistrationActivity extends AppCompatActivity {

    TextView loginBtn;
    Button register;
    EditText name, phone, email, pass, cpass;

    ProgressBar bar;

    SharedPreferences pref;
    SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        loginBtn = (TextView) findViewById(R.id.loginBtn);
        bar = (ProgressBar) findViewById(R.id.progress);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.regEmail);
        pass = (EditText) findViewById(R.id.regPass);
        cpass = (EditText) findViewById(R.id.cnfrmPass);
        register = (Button) findViewById(R.id.regButton);

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String n = name.getText().toString();
                final String p = phone.getText().toString();
                final String e = email.getText().toString();
                final String ps = pass.getText().toString();
                final String cps = cpass.getText().toString();

                if (!TextUtils.isEmpty(n)){

                    if (!TextUtils.isEmpty(p)){

                        if (!TextUtils.isEmpty(e)){

                            if (!TextUtils.isEmpty(ps)){

                                if (ps.length() > 6){

                                    if (ps.equals(cps)){


                                        bar.setVisibility(View.VISIBLE);
                                        final Bean b = (Bean) getApplicationContext();
                                        Retrofit retrofit = new Retrofit.Builder()
                                                .baseUrl(b.baseURL)
                                                .addConverterFactory(ScalarsConverterFactory.create())
                                                .addConverterFactory(GsonConverterFactory.create())
                                                .build();
                                        Allapi cr = retrofit.create(Allapi.class);
                                        Call<RegisterBean> call = cr.register(n,e,ps,p);
                                        call.enqueue(new Callback<RegisterBean>() {
                                            @Override
                                            public void onResponse(Call<RegisterBean> call, Response<RegisterBean> response) {

                                                if (Objects.equals(response.body().getStatus(), "1")){
                                                    Toast.makeText(RegistrationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    bar.setVisibility(View.GONE);
                                                    b.userId = response.body().getData().getUserId();
                                                    b.otp = response.body().getData().getOtp();
                                                    edit.putString("userId", response.body().getData().getPhone());
                                                    edit.apply();

                                                    Intent in = new Intent(RegistrationActivity.this, OtpActivity.class );
                                                    in.putExtra("OTP", response.body().getData().getOtp());
                                                    startActivity(in);
                                                    finish();



                                                }else {
                                                    Toast.makeText(RegistrationActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                    bar.setVisibility(View.GONE);
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<RegisterBean> call, Throwable t) {
                                                bar.setVisibility(View.GONE);

                                            }
                                        });

                                    }else {
                                        cpass.setError("Password does not match");
                                        cpass.requestFocus();
                                    }

                                }else {
                                    pass.setError("Password too short, enter minimum 6 character");
                                    pass.requestFocus();
                                }

                            }else {
                                pass.setError("Field is Empty");
                                pass.requestFocus();
                            }

                        }else {
                            email.setError("Field is Empty");
                            email.requestFocus();
                        }

                    }else {
                        phone.setError("Field is Empty");
                        phone.requestFocus();
                    }

                }else {
                    name.setError("Field is Empty");
                    name.requestFocus();
                }
            }
        });
    }
}
