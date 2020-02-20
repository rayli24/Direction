package com.techme.direction.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.techme.direction.ui.fragments.AddDiningFragment;
import com.techme.direction.ui.fragments.AddGroceryFragment;
import com.techme.direction.ui.fragments.AddStoresFragment;
import com.techme.direction.R;

public class AddStoreActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private Drawable backIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        backIcon = getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(backIcon);
        navigationView = findViewById(R.id.bottom_nav_add_store);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store,new AddStoresFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;
            switch (menuItem.getItemId())
            {
                case R.id.nav_add_store_list:
                    selectedFragment = new AddStoresFragment();
                    break;
                case R.id.nav_add_store_dining:
                    selectedFragment = new AddDiningFragment();
                    break;
                case R.id.nav_add_store_grocery:
                    selectedFragment = new AddGroceryFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar,menu);
        menu.findItem(R.id.bar_settings).setVisible(false);
        return true;
    }

}
