package com.example.faizan.fuelapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import github.nisrulz.easydeviceinfo.base.EasyLocationMod;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    Toolbar toolbar;
    DrawerLayout drawer;
    TextView profile, history, concept, notification, ratetheapp, logout;
    SharedPreferences pref;
    SharedPreferences.Editor edit;
    GoogleApiClient googleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profile = (TextView) findViewById(R.id.Profile);
        history = (TextView) findViewById(R.id.history);
        concept = (TextView) findViewById(R.id.concept);
        notification = (TextView) findViewById(R.id.notification);
        ratetheapp = (TextView) findViewById(R.id.ratetheapp);
        logout = (TextView) findViewById(R.id.logout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setTitleTextColor(Color.WHITE);
        pref = getSharedPreferences("pref", MODE_PRIVATE);
        edit = pref.edit();

        buildGoogleApiClient();


        drawer = (DrawerLayout) findViewById(R.id.activity_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        MapFragment fragment = new MapFragment();
        ft.replace(R.id.replace, fragment);
        //ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
                ProfileFragment fragment = new ProfileFragment();
                ft.replace(R.id.replace, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
               // toolbar.setTitle("Profile");
               // toolbar.setTitleTextColor(Color.BLACK);

                drawer.closeDrawer(GravityCompat.START);

            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
                HistoryFragment fragment = new HistoryFragment();
                ft.replace(R.id.replace, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
               // toolbar.setTitle("Transaction History");
               // toolbar.setTitleTextColor(Color.BLACK);

                drawer.closeDrawer(GravityCompat.START);

            }
        });
        concept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
                ConceptFragment fragment = new ConceptFragment();
                ft.replace(R.id.replace, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
               // toolbar.setTitle("The Concept");
                //toolbar.setTitleTextColor(Color.BLACK);

                drawer.closeDrawer(GravityCompat.START);

            }
        });

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
                NotificationFragment fragment = new NotificationFragment();
                ft.replace(R.id.replace, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
                //toolbar.setTitle("Notification");
                //toolbar.setTitleTextColor(Color.BLACK);

                drawer.closeDrawer(GravityCompat.START);

            }
        });

        ratetheapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStackImmediate();
                }
                RateFragment fragment = new RateFragment();
                ft.replace(R.id.replace, fragment);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                ft.commit();
               // toolbar.setTitle("Rate The App");
                //toolbar.setTitleTextColor(Color.BLACK);

                drawer.closeDrawer(GravityCompat.START);

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ty = pref.getString("Type" , "");

                Log.d("asdas" , ty);

                if (Objects.equals(pref.getString("Type", ""), "google")) {
                    Log.d("htyhyh" , "jkbkb");

                    if (googleApiClient.isConnected()) {
                        signOut();
                    }
                } else if (Objects.equals(pref.getString("Type", ""), "facebook")) {

                    LoginManager.getInstance().logOut();
                    edit.remove("userId");
                    edit.remove("facebook");
                    edit.apply();
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    // overridePendingTransition(0,0);
                    finish();

                } else {
                    if (Objects.equals(pref.getString("Type", ""), "signIn")) {
                        edit.remove("phone");
                        edit.remove("userId");
                       /* edit.remove("name");
                        edit.remove("email");*/
                        edit.apply();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        finish();
                    }
                }
            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();

        googleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @SuppressLint("CommitPrefEdits")
                    @Override
                    public void onResult(@NonNull Status status) {


                        if (status.isSuccess()) {

                            edit.remove("userId");
                            edit.remove("google");
                            edit.apply();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);
                            // overridePendingTransition(0,0);
                            finish();

                        }


                    }
                });
    }

    private synchronized void buildGoogleApiClient() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu , menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_notification)
        {

            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportFragmentManager().popBackStackImmediate();
            }
            NotificationFragment fragment = new NotificationFragment();
            ft.replace(R.id.replace, fragment);
            ft.addToBackStack(null);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            ft.commit();

        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
