package com.techme.direction.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techme.direction.ContextHelper;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.ui.fragments.MyDiningFragment;
import com.techme.direction.ui.fragments.MyGroceryFragment;
import com.techme.direction.ui.fragments.MyNoteFragment;
import com.techme.direction.ui.fragments.MyStoresFragment;
import com.techme.direction.R;

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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_store,new MyStoresFragment()).commit();
        buttons();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.bar_search:
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
                return  true;
            case R.id.bar_settings:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bar_duration_time:
                Toast.makeText(this, "time clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bar_alphabetic:
                Toast.makeText(this, "alpha clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bar_location:
                Toast.makeText(this, "location clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.bar_nearest:
                Toast.makeText(this, "nearest clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

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

    /**
     * hold all the onClick button listeners
     */
    private void buttons()
    {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyStoreActivity.this, AddStoreActivity.class);
                startActivity(intent);
            }
        });
    }
}
