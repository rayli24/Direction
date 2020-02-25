package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.techme.direction.Country;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.helper.ContextHelper;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;

import static com.techme.direction.helper.VariablesHelper.APP_PERMISSION;
import static com.techme.direction.helper.VariablesHelper.LOAD_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.SHARED_PREF_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.countryName;
import static com.techme.direction.helper.VariablesHelper.countrySelected;

public class SplashScreenActivity extends AppCompatActivity {

    private static int splashTime = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextHelper.Helper.setResource(getResources()); // store resource in a class
        setContentView(R.layout.activity_splash_screen);
        splashTime();
    }

    private void splashTime() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCountry();
                if (!checkPermission() || !countrySelected) {
                    Intent intent = new Intent(SplashScreenActivity.this, PermissionActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, MyStoreActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, splashTime);
    }

    private void loadCountry() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_COUNTRY, MODE_PRIVATE);
        countryName = sharedPreferences.getString(LOAD_COUNTRY, "none");
        if (!countryName.equals("none")) {
            countrySelected = true;
        }
    }

    /**
     * check if all the permissions were granted or not
     *
     * @return
     */
    private boolean checkPermission() {
        List<String> listPermissionsNeeded = new ArrayList<>();
        // check which permission are granted
        for (String permission : APP_PERMISSION) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(permission);
            }
        }

        // check if there was permissions that was not granted
        if (!listPermissionsNeeded.isEmpty()) {
            return false;
        }
        return true;
    }
}
