package com.techme.direction;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MyStoreActivity extends AppCompatActivity {
    DirectionViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContextHelper.Helper.setResource(getResources()); // store Resource in a class

        // create a viewModel for every activity that need to get a specific table
        viewModel = ViewModelProviders.of(this).get(DirectionViewModel.class);
        viewModel.getAllStore().observe(this, new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                Toast.makeText(MyStoreActivity.this, "Testing", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
