package com.techme.direction.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Window;
import android.widget.Toast;

import com.techme.direction.Country;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.adapter.CountryRecycleAdapter;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techme.direction.helper.VariablesHelper.ALL_PERMISSION_CODE;
import static com.techme.direction.helper.VariablesHelper.APP_PERMISSION;
import static com.techme.direction.helper.VariablesHelper.LOAD_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.SHARED_PREF_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.TRUE;
import static com.techme.direction.helper.VariablesHelper.countryName;
import static com.techme.direction.helper.VariablesHelper.countrySelected;

public class PermissionActivity extends AppCompatActivity {
    private DirectionViewModel viewModel;
    private CountryRecycleAdapter adapter;
    private RecyclerView recyclerView;
    private static boolean permission_granted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        init();
        observer();
        onItemClick();
        checkPermission();

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
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    ALL_PERMISSION_CODE);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == ALL_PERMISSION_CODE) {
            HashMap<String, Integer> permissionResults = new HashMap<>();
            int deniedCount = 0;

            for (int i = 0; i < grantResults.length; i++) {
                // add only permissions that are denied
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    permissionResults.put(permissions[i], grantResults[i]);
                    deniedCount++;
                }
            }

            // check if all permissions are granted
            if (deniedCount == 0) {
                // if the user accepted the permission again and they selected a country already
                if (countrySelected) {
                    Intent intent = new Intent(PermissionActivity.this, MyStoreActivity.class);
                    startActivity(intent);
                    finish();
                }
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                // close the app if the user denied and already has chosen a location before
            } else if (countrySelected) {
                finish();
                moveTaskToBack(true);
            } else {
                for (Map.Entry<String, Integer> entry : permissionResults.entrySet()) {
                    String permissionName = entry.getKey();
                    int permissionResult = entry.getValue();

                    // permission is denied but user did not check the checkbox
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionName)) {
                        showDialog("Permission needed", "this app needs Location to work properly.",
                                "Yes, Grant permission", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        checkPermission();
                                    }
                                },
                                "No, Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Toast.makeText(PermissionActivity.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                                    }
                                }, false);
                    }

                    // permission is denied and never asked again is checked
                    else {
                        // direct user to settings to manually allow permissions
                        showDialog("Permission needed", "You have denied some permissions. Allow all permissions" +
                                        "at [Setting] > [App] > [Permissions]", "Go to Settings",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        // Go to app settings
                                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                                Uri.fromParts("package", getPackageName(), null));
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                    }
                                }, "No, Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        Toast.makeText(PermissionActivity.this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                                    }
                                }, false);
                        break;
                    }
                }
            }


        }

    }

    /**
     * customized AlertDialog
     *
     * @param title
     * @param msg
     * @param positiveLabel
     * @param positiveOnClick
     * @param negativeLabel
     * @param negativeOnClick
     * @param isCancelAble
     * @return
     */
    public AlertDialog showDialog(String title, String msg, String positiveLabel,
                                  DialogInterface.OnClickListener positiveOnClick,
                                  String negativeLabel, DialogInterface.OnClickListener negativeOnClick,
                                  boolean isCancelAble) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(isCancelAble)
                .setMessage(msg)
                .setPositiveButton(positiveLabel, positiveOnClick)
                .setNegativeButton(negativeLabel, negativeOnClick)
                .create();

        Window view = ((alertDialog)).getWindow();
        view.setBackgroundDrawableResource(R.drawable.white_border);
        alertDialog.show();
        return alertDialog;

    }


    private void init() {
        recyclerView = findViewById(R.id.recycle_view_country_permission);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE);
        adapter = new CountryRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                adapter.submitList(countries);
            }
        });
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new CountryRecycleAdapter.onItemClickListener() {
            @Override
            public void onClick(Country country) {
                if (checkPermission()) {
                    countryName = country.getName();
                    saveCountry(country.getName());
                    country.setSelected(TRUE);
                    viewModel.updateCountry(country);
                    Intent intent = new Intent(PermissionActivity.this, MyStoreActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    checkPermission();
                }
            }
        });
    }

    /**
     * store the selected country in a sharedPreference
     *
     * @param name
     */
    private void saveCountry(String name) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_COUNTRY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOAD_COUNTRY, name);
        editor.apply();
    }
}
