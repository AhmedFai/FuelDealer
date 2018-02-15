package com.example.faizan.fuelapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.faizan.fuelapp.ForgetPasswordPOJO.ForgetPasswordBean;
import com.example.faizan.fuelapp.LoginPOJO.LoginBean;
import com.example.faizan.fuelapp.ResetPasswordPOJO.ResetPasswordBean;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    Button loginButton;
    EditText phone, pass, forgetNumber, resetCode, setPass, cnPass;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    ProgressBar bar;
    ImageView facebook, google;
    CallbackManager mCallbackManager;
    TextView registertBtn, forgotcancel, forgotconfirm, resetcancel, resetconfirm, forgotpassword;
    GoogleApiClient googleApiClient ;
    int RC_SIGN_IN = 12;
    Boolean goog_flag = false;
    Boolean fb_flag = false;
    Boolean sign_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        mCallbackManager = CallbackManager.Factory.create();


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {

                        String email, pid, name;

                        try {
                            email = object.getString("email");
                            pid = object.getString("id");
                            name = object.getString("name");
                            Log.d("fbname", name);
                            Log.d("fbemail", email);
                            Log.d("fbid", pid);


                            final Bean b = (Bean) getApplicationContext();
                            bar.setVisibility(View.VISIBLE);
                            fb_flag = true;


                            final Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseURL)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            final Allapi cr = retrofit.create(Allapi.class);
                            Call<LoginBean> call = cr.social(name,email,pid);
                            call.enqueue(new Callback<LoginBean>() {
                                @Override
                                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                                    if (Objects.equals(response.body().getStatus(), "1")){
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                        Bean b = (Bean) getApplicationContext();
                                        b.userId = response.body().getData().getUserId();
                                        b.name = response.body().getData().getName();
                                        b.phone = response.body().getData().getPhone();
                                        b.email = response.body().getData().getEmail();

                                        edit.putString("userId", response.body().getData().getUserId());
                                        edit.putString("Type", "facebook");
                                        edit.apply();

                                        Intent s = new Intent(LoginActivity.this, MainActivity.class);
                                        s.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(s);
                                        finish();
                                        bar.setVisibility(View.GONE);
                                    }else {
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<LoginBean> call, Throwable t) {

                                    LoginManager.getInstance().logOut();
                                    bar.setVisibility(View.GONE);
                                    fb_flag = false;
                                }
                            });


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "email,id,name,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);
        edit = pref.edit();

        phone = (EditText) findViewById(R.id.phone);
        pass = (EditText) findViewById(R.id.pass);
        bar = (ProgressBar) findViewById(R.id.progress);

        loginButton = (Button) findViewById(R.id.logInButton);
        registertBtn = (TextView) findViewById(R.id.regBtn);

        facebook = (ImageView) findViewById(R.id.facebook);
        google = (ImageView) findViewById(R.id.google);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email", "public_profile"));
            }
        });


        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        forgotpassword = (TextView) findViewById(R.id.forgotPassword);
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(LoginActivity.this);
                dialog.setContentView(R.layout.forgot_dialog);
                dialog.setCancelable(true);
                dialog.show();

                forgotcancel = (TextView) dialog.findViewById(R.id.forgotCancel);
                forgotcancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });


                forgotconfirm = (TextView) dialog.findViewById(R.id.forgotConfirm);
                forgetNumber = (EditText) dialog.findViewById(R.id.fNumber);
                forgotconfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                        final String phone = forgetNumber.getText().toString();
                        if (!TextUtils.isEmpty(phone)) {

                            bar.setVisibility(View.VISIBLE);
                            final Bean b = (Bean) getApplicationContext();
                            Retrofit retrofit = new Retrofit.Builder()
                                    .baseUrl(b.baseURL)
                                    .addConverterFactory(ScalarsConverterFactory.create())
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build();
                            Allapi cr = retrofit.create(Allapi.class);
                            Call<ForgetPasswordBean> call = cr.forget(phone);
                            call.enqueue(new Callback<ForgetPasswordBean>() {
                                @Override
                                public void onResponse(Call<ForgetPasswordBean> call, Response<ForgetPasswordBean> response) {
                                    if (Objects.equals(response.body().getStatus(), "1")) {
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);

                                        b.phone = response.body().getData().getPhone();
                                        Log.d("ResetCode", response.body().getData().getOtp());

                                        final Dialog dialog2 = new Dialog(LoginActivity.this);
                                        dialog2.setContentView(R.layout.reset_dialog);
                                        dialog2.setCancelable(true);
                                        dialog2.show();
                                        resetcancel = (TextView) dialog2.findViewById(R.id.resetCancel);
                                        resetcancel.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog2.dismiss();
                                            }
                                        });
                                        resetconfirm = (TextView) dialog2.findViewById(R.id.resetConfirm);
                                        resetCode = (EditText) dialog2.findViewById(R.id.resetCode);
                                        setPass = (EditText) dialog2.findViewById(R.id.setPassword);
                                        cnPass = (EditText) dialog2.findViewById(R.id.confirmPassword);
                                        resetconfirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                final String code = resetCode.getText().toString();
                                                final String setP = setPass.getText().toString();
                                                final String cnP = cnPass.getText().toString();

                                                if (!TextUtils.isEmpty(code)) {

                                                    if (!TextUtils.isEmpty(setP)) {

                                                        if (setP.equals(cnP)) {

                                                            bar.setVisibility(View.VISIBLE);
                                                            final Bean b = (Bean) getApplicationContext();
                                                            Retrofit retrofit = new Retrofit.Builder()
                                                                    .baseUrl(b.baseURL)
                                                                    .addConverterFactory(ScalarsConverterFactory.create())
                                                                    .addConverterFactory(GsonConverterFactory.create())
                                                                    .build();
                                                            Allapi cr = retrofit.create(Allapi.class);
                                                            Call<ResetPasswordBean> call = cr.reset(code, b.phone, setP);
                                                            call.enqueue(new Callback<ResetPasswordBean>() {
                                                                @Override
                                                                public void onResponse(Call<ResetPasswordBean> call, Response<ResetPasswordBean> response) {
                                                                    if (Objects.equals(response.body().getStatus(), "1")) {
                                                                        dialog2.dismiss();
                                                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        bar.setVisibility(View.GONE);


                                                                    } else {
                                                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        bar.setVisibility(View.GONE);
                                                                    }
                                                                }

                                                                @Override
                                                                public void onFailure(Call<ResetPasswordBean> call, Throwable t) {

                                                                }
                                                            });


                                                        } else {
                                                            cnPass.setError("Field is Empty");
                                                            cnPass.requestFocus();
                                                        }

                                                    } else {
                                                        setPass.setError("Field is Empty");
                                                        setPass.requestFocus();
                                                    }

                                                } else {
                                                    resetCode.setError("Field is Empty");
                                                    resetCode.requestFocus();
                                                }


                                            }
                                        });
                                    } else {
                                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                        bar.setVisibility(View.GONE);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ForgetPasswordBean> call, Throwable t) {

                                    bar.setVisibility(View.GONE);
                                }
                            });

                        } else {
                            forgetNumber.setError("Field is Empty");
                            forgetNumber.requestFocus();
                        }


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
                final String p = phone.getText().toString();
                final String ps = pass.getText().toString();

                if (!TextUtils.isEmpty(p)) {

                    if (!TextUtils.isEmpty(ps)) {


                        bar.setVisibility(View.VISIBLE);
                        sign_flag = true;
                        final Bean b = (Bean) getApplicationContext();
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(b.baseURL)
                                .addConverterFactory(ScalarsConverterFactory.create())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        Allapi cr = retrofit.create(Allapi.class);
                        Call<LoginBean> call = cr.login(p, ps);
                        call.enqueue(new Callback<LoginBean>() {
                            @Override
                            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                                if (Objects.equals(response.body().getStatus(), "1")) {
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                    b.phone = response.body().getData().getPhone();
                                    b.userId = response.body().getData().getUserId();

                                    edit.putString("userId", response.body().getData().getPhone());
                                    edit.putString("Type", "signIn");
                                    edit.apply();

                                    Intent l = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(l);
                                    finish();

                                } else {
                                    Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    bar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onFailure(Call<LoginBean> call, Throwable t) {

                                bar.setVisibility(View.GONE);
                                sign_flag = false;
                            }
                        });

                    } else {
                        pass.setError("Field is Empty");
                        pass.requestFocus();
                    }

                } else {
                    phone.setError("Field is Empty");
                    phone.requestFocus();
                }
            }
        });


    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

    }

    private  void  handleSignInResult(GoogleSignInResult result){


        Log.d("handle karo bhai", result.toString());


        if (result.isSuccess()){
            GoogleSignInAccount acct = result.getSignInAccount();


            final String email = acct.getEmail();
            final String pid = acct.getId();
            final String name = acct.getDisplayName();

            Log.d("gmailId", acct.getEmail());
            Log.d("gpid", acct.getId());


            bar.setVisibility(View.VISIBLE);
            goog_flag = true;
            final Bean b = (Bean)getApplicationContext();

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(b.baseURL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final Allapi cr = retrofit.create(Allapi.class);
            Call<LoginBean> call = cr.social(name,email,pid);
            call.enqueue(new Callback<LoginBean>() {
                @Override
                public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                    if (Objects.equals(response.body().getStatus(), "1")){
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        final Bean b = (Bean)getApplicationContext();


                        b.userId = response.body().getData().getUserId();
                        Intent gi = new Intent(LoginActivity.this, MainActivity.class);

                        edit.putString("userId", response.body().getData().getUserId());
                        Log.d("gooogllleee","succeeesss");
                        edit.putString("Type", "google");
                        edit.apply();
                        startActivity(gi);
                        finish();


                        bar.setVisibility(View.GONE);
                    }else {
                        Toast.makeText(LoginActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        bar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<LoginBean> call, Throwable t) {

                    Log.d("Google mai dikkt hai", t.toString());
                    bar.setVisibility(View.GONE);
                    signOut();
                    goog_flag = false;
                }
            });
        }
    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {


                    }
                });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
