package com.example.faizan.fuelapp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    Button loginButton;
    TextView registertBtn, forgotcancel, forgotconfirm, resetcancel, resetconfirm, forgotpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button)findViewById(R.id.logInButton);
        registertBtn = (TextView)findViewById(R.id.regBtn);


        resetconfirm = (TextView)findViewById(R.id.resetConfirm);
        forgotpassword = (TextView)findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.forgot_dialog);
                dialog.setCancelable(true);
                dialog.show();

                forgotcancel = (TextView)dialog.findViewById(R.id.forgotCancel);
                forgotcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                forgotconfirm = (TextView)dialog.findViewById(R.id.forgotConfirm);
                forgotconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Dialog dialog2 = new Dialog(LoginActivity.this);
                        dialog2.setContentView(R.layout.reset_dialog);
                        dialog2.setCancelable(true);
                        dialog2.show();

                        resetcancel = (TextView)dialog2.findViewById(R.id.resetCancel);
                        resetcancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog2.dismiss();
                            }
                        });

                    }
                });
            }
        });







        registertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(login);
            }
        });





    }
}
