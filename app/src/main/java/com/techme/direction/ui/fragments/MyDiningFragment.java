package com.techme.direction.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyStoreRecycleAdapter;
import com.techme.direction.ui.AddStoreActivity;
import com.techme.direction.ui.CountryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;
import static com.techme.direction.helper.VariablesHelper.DAILY_NOTES;
import static com.techme.direction.helper.VariablesHelper.DINING;
import static com.techme.direction.helper.VariablesHelper.DINING_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.EXTRA_COUNTRY_CODE;
import static com.techme.direction.helper.VariablesHelper.EXTRA_FRAGMENT;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.GROCERY;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.REPLACE;
import static com.techme.direction.helper.VariablesHelper.countryName;
import static com.techme.direction.helper.VariablesHelper.stringToUri;


public class MyDiningFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private RelativeLayout layoutEmpty;
    private List<Store> origList = new ArrayList<>();
    private SearchView searchView;
    private List<Store> groceryList = new ArrayList<>();
    private FloatingActionButton floatingActionButton;

    public MyDiningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_dining_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_my_dining);
        layoutEmpty = view.findViewById(R.id.my_layout_dining_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        observer();
        onItemClick();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyStoreRecycleItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    /**
     * this method open the google map and passing the location the user wants to travel to
     */
    private void onItemClick() {
        adapter.setOnItemClickListener(new MyStoreRecycleAdapter.onItemClickListener() {
            @Override
            public void onclick(Store store) {
                String name = stringToUri(store.getName());
                String country = store.getCountryName();
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + name + "," + country);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE);
        adapter = new MyStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
        floatingButton();
    }

    private void observer() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for (Store store : stores) {
                    if (store.getCountryName().equals(countryName) && store.getType().equals(DINING)) {
                        list.add(store);
                    }
                    if (store.getCountryName().equals(countryName) && store.getType().equals(GROCERY)) {
                        groceryList.add(store);
                    }

                }
                if (!list.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    layoutEmpty.setVisibility(View.GONE);
                } else {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                origList = new ArrayList<>(list);

                adapter.submitList(list);

                if (searchView != null && searchView.getQuery().length() > 0) {
                    String temp = String.valueOf(searchView.getQuery());
                    searchView.setQuery("", false);
                    searchView.setQuery(temp, false);
                }
            }
        });
    }


    private void floatingButton() {
        floatingActionButton = getActivity().findViewById(R.id.float_button_store);
        floatingActionButton.bringToFront();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddStoreActivity.class);
                intent.putExtra(EXTRA_FRAGMENT, DINING_FRAGMENT);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        adapter.getStore(viewHolder.getAdapterPosition()).setSelected(FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.bar_search);
        MenuItem locationItem = menu.findItem(R.id.bar_location);
        menuItem.collapseActionView();
        requestNewLocation(locationItem);
        searchView = (SearchView) menuItem.getActionView();
        search(menuItem);
    }

    /**
     * handles th search view
     *
     * @param menuItem
     */
    private void search(final MenuItem menuItem) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    String name = "%" + newText + "%";
                    List<Store> list = new ArrayList<>();
                    try {
                        for (Store store : viewModel.searchMyStore(name)) {
                            if (store.getType().equals(DINING) &&
                                    store.getCountryName().equals(countryName)) {
                                list.add(store);
                            }
                        }
                        adapter.submitList(list);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    adapter.submitList(origList);
                }
                return true;
            }
        });
    }

    /**
     * open th country activity to retrieve the new chosen location
     *
     * @param location
     */
    private void requestNewLocation(MenuItem location) {
        location.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getContext(), CountryActivity.class);
                startActivityForResult(intent, EXTRA_COUNTRY_CODE);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // refresh recycle view
        if (requestCode == EXTRA_COUNTRY_CODE && resultCode == RESULT_OK) {
            observer();
            // create a new daily note
            Note note = new Note(DAILY_NOTES, FALSE);
            viewModel.insertNote(note);
            if (!groceryList.isEmpty()) {
                // create old empty notes from new location country
                for (Store store : groceryList) {
                    if (store.getType().equals(GROCERY) &&
                            store.getCountryName().equals(countryName)) {
                        note = new Note(store.getName(), FALSE);
                        viewModel.insertNote(note);
                    }
                }
            }
            Toast.makeText(getContext(), "Country has been changed", Toast.LENGTH_SHORT).show();
        }
    }
}
