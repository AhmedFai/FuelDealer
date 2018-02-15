package com.example.faizan.fuelapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    Timer time;

    SharedPreferences pref;

    SharedPreferences.Editor edit;

    ProgressBar bar;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE
            , Manifest.permission.READ_SMS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(SplashActivity.this));

        pref = getSharedPreferences("pref", Context.MODE_PRIVATE);

        edit = pref.edit();
        bar = (ProgressBar) findViewById(R.id.progress);

        if (hasPermissions(this, PERMISSIONS)) {

            startApp();


        } else {
            ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST_CODE_ASK_PERMISSIONS);
        }


        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.faizan.fuelapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_ASK_PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                startApp();

            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {

                    Toast.makeText(getApplicationContext(), "Permissions are required for this app", Toast.LENGTH_SHORT).show();
                    finish();

                }
                //permission is denied (and never ask again is  checked)
                //shouldShowRequestPermissionRationale will return false
                else {
                    Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                            .show();
                    finish();
                    //                            //proceed with logic by disabling the related features or quit the app.
                }
            }

        }

    }


    public void startApp() {

        String id = pref.getString("userId" , "");

        if (id.length() > 0)
        {

            Bean b = (Bean) getApplicationContext();
            b.userId = id;

            //          edit.putString("userId", pref.getString("userId", ""));
//            edit.apply();

            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();


        }
        else
        {
            time = new Timer();
            time.schedule(new TimerTask() {
                @Override
                public void run() {

                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();

                }
            }, 1500);

        }
    }
}
