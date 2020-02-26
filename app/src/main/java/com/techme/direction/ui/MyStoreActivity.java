package com.techme.direction.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupWindow;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techme.direction.ui.fragments.MyDiningFragment;
import com.techme.direction.ui.fragments.MyGroceryFragment;
import com.techme.direction.ui.fragments.MyNoteFragment;
import com.techme.direction.ui.fragments.MyStoresFragment;
import com.techme.direction.R;

import static com.techme.direction.helper.VariablesHelper.EXTRA_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.NOTE_FRAGMENT;

public class MyStoreActivity extends AppCompatActivity {

    private BottomNavigationView navigationView;
    private FloatingActionButton floatingActionButton;
    private String intentFragment;
    private int selectedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_store);
        floatingActionButton = findViewById(R.id.float_button_store);

        navigationView = findViewById(R.id.bottom_nav_my_store);
        navigationView.setOnNavigationItemSelectedListener(navListener);

        intentFragment = getIntent().getStringExtra(EXTRA_FRAGMENT);
        if (!TextUtils.isEmpty(intentFragment)) {
            switch (intentFragment) {
                case NOTE_FRAGMENT:
                    selectedId = navigationView.getMenu().findItem(R.id.nav_my_store_notes).getItemId();
                    navigationView.setSelectedItemId(selectedId);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_store, new MyNoteFragment()).commit();
                    break;
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_store, new MyStoresFragment()).commit();
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;
                    switch (menuItem.getItemId()) {
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
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_store, selectedFragment).commit();
                    return true;
                }
            };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        menu.findItem(R.id.bar_nearest).setEnabled(false);
        menu.findItem(R.id.bar_duration_time).setEnabled(false);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_settings:
                return true;
            case R.id.bar_duration_time:
                return true;
            case R.id.bar_alphabetic:
                return true;
            case R.id.bar_nearest:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * toggle the visibility of the floating button
     *
     * @param show
     */
    private void showFloat(boolean show) {
        if (show)
            floatingActionButton.show();
        else
            floatingActionButton.hide();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


}
