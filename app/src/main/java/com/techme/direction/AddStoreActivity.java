package com.techme.direction;

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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.bar_search:
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
                return  true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
