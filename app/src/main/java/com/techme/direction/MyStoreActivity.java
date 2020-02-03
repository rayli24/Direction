package com.techme.direction;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MyStoreActivity extends AppCompatActivity {
    DirectionViewModel viewModel;
    BottomNavigationView navigationView;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        ContextHelper.Helper.setResource(getResources()); // store Resource in a class
        floatingActionButton = findViewById(R.id.float_button_store);
        navigationView = findViewById(R.id.bottom_nav_my_store);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        // create a viewModel for every activity that need to get a specific table
//        viewModel = ViewModelProviders.of(this).get(DirectionViewModel.class);
//        viewModel.getAllStore().observe(this, new Observer<List<Store>>() {
//            @Override
//            public void onChanged(List<Store> stores) {
//                Toast.makeText(MyStoreActivity.this, "Testing", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()){
                        case R.id.nav_my_store_list:
                            showFloat(true);
                            selectedFragment = new MyStoresFragment();
                            break;
                        case R.id.nav_my_store_dining:
                            showFloat(true);
                            selectedFragment = new MyDiningFragment();
                            break;
                        case R.id.nav_my_store_grocery:
                            showFloat(true);
                            selectedFragment = new MyGroceryFragment();
                            break;
                        case R.id.nav_my_store_notes:
                            showFloat(false);
                            selectedFragment = new MyNoteFragment();
                            break;

                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_store,selectedFragment).commit();
                    return true;
                }
            };

    /**
     * toggle the visibility of the floating button
     * @param show
     */
    private void showFloat(boolean show)
    {
        if(show)
            floatingActionButton.show();
        else
            floatingActionButton.hide();
    }
}
