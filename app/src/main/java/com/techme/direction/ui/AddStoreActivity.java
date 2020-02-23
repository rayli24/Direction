package com.techme.direction.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.ui.fragments.AddDiningFragment;
import com.techme.direction.ui.fragments.AddGroceryFragment;
import com.techme.direction.ui.fragments.AddStoresFragment;
import com.techme.direction.R;

import static com.techme.direction.helper.VariablesHelper.DINING_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.EXTRA_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.GROCERY_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.STORE_FRAGMENT;

public class AddStoreActivity extends AppCompatActivity {
    private BottomNavigationView navigationView;
    private Drawable backIcon;
    private String intentFragment;
    private int selectedId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);
        backIcon = getResources().getDrawable(R.drawable.ic_chevron_left_black_24dp);
        getSupportActionBar().setHomeAsUpIndicator(backIcon);
        navigationView = findViewById(R.id.bottom_nav_add_store);
        navigationView.setOnNavigationItemSelectedListener(navListener);

        // to get the correct fragment when user want to add new store my list
        intentFragment = getIntent().getStringExtra(EXTRA_FRAGMENT);
        if(!TextUtils.isEmpty(intentFragment)){
            switch (intentFragment){
                case DINING_FRAGMENT:
                    selectedId = navigationView.getMenu().findItem(R.id.nav_add_store_dining).getItemId();
                    navigationView.setSelectedItemId(selectedId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store, new AddDiningFragment()).commit();
                    break;
                case GROCERY_FRAGMENT:
                    selectedId = navigationView.getMenu().findItem(R.id.nav_add_store_grocery).getItemId();
                    navigationView.setSelectedItemId(selectedId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store, new AddGroceryFragment()).commit();
                    break;
                case STORE_FRAGMENT:
                    selectedId = navigationView.getMenu().findItem(R.id.nav_add_store_list).getItemId();
                    navigationView.setSelectedItemId(selectedId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store, new AddStoresFragment()).commit();
                    break;
            }

        }else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_add_store, new AddStoresFragment()).commit();

        }
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
