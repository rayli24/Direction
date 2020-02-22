package com.techme.direction.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techme.direction.Country;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.adapter.CountryRecycleAdapter;
import com.techme.direction.helper.VariablesHelper;

import java.util.List;

import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.LOAD_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.SHARED_PREF_COUNTRY;
import static com.techme.direction.helper.VariablesHelper.TRUE;
import static com.techme.direction.helper.VariablesHelper.countryName;

public class CountryActivity extends AppCompatActivity {


    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private CountryRecycleAdapter adapter;
    private TextView txtName;
    private Country currentCountry;
    private ImageView imgBack;
    private boolean accepted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        init();
        observer();
        onItemClick();
        onBackButton();
    }

    private void init(){
        imgBack = findViewById(R.id.img_country_back);
        txtName = findViewById(R.id.txt_country_layout_title);
        recyclerView = findViewById(R.id.recycle_view_country);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE);
        adapter = new CountryRecycleAdapter();
        recyclerView.setAdapter(adapter);
        txtName.setText("Change: " + countryName);
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllCountries().observe(this, new Observer<List<Country>>() {
            @Override
            public void onChanged(List<Country> countries) {
                adapter.submitList(countries);
                for(Country country: countries){
                    if(country.getName().equals(countryName)){
                        currentCountry = country;
                        return;
                    }
                }
            }
        });
    }

    private void onItemClick(){
        adapter.setOnItemClickListener(new CountryRecycleAdapter.onItemClickListener() {
            @Override
            public void onClick(Country country) {
                if(country.getName().equals(currentCountry.getName())){
                    Toast toast = Toast.makeText(CountryActivity.this, currentCountry.getName()
                            + " is already your current location", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    displayDialog(country);
                }
            }
        });
    }

    private void updateCountry(Country newCountry){
            currentCountry.setSelected(FALSE);
            viewModel.updateCountry(currentCountry);
            newCountry.setSelected(TRUE);
            countryName = newCountry.getName();
            deleteCountryNotes();
            saveCountry();
            viewModel.updateCountry(newCountry);
            setResult(RESULT_OK);
            finish();
    }

    private void displayDialog(final Country newCountry){
        showDialog("Confirmation needed", "You will lose all '" + currentCountry.getName()+ "' Todo list",
                "Ok, Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        updateCountry(newCountry);
                    }
                }, "No, Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Toast toast = Toast.makeText(CountryActivity.this, "Country change failed", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }
                },false);
    }

    private AlertDialog showDialog(String title, String msg, String positiveLabel,
                                  DialogInterface.OnClickListener positiveOnClick,
                                  String negativeLabel, DialogInterface.OnClickListener negativeOnClick,
                                  boolean isCancelAble) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setCancelable(isCancelAble)
                .setMessage(msg)
                .setPositiveButton(positiveLabel, positiveOnClick)
                .setNegativeButton(negativeLabel, negativeOnClick)
//                .setIcon(R.drawable.ic_delete_red_24dp)
                .create();

        Window view = ((alertDialog)).getWindow();
        view.setBackgroundDrawableResource(R.drawable.white_border);
        alertDialog.show();
        return alertDialog;

    }

    /**
     * delete all notes and to-do list
     *
     */
    private void deleteCountryNotes(){
        viewModel.deleteAllToDoList();
        viewModel.deleteAllNotes();
    }

    /**
     * save the new country in a shared preference
     */
    private void saveCountry(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_COUNTRY,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(LOAD_COUNTRY, countryName);
        editor.apply();
    }

    private void onBackButton(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
