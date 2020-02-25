package com.techme.direction.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.techme.direction.adapter.AddStoreRecycleAdapter;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.techme.direction.helper.VariablesHelper.DINING;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.TRUE;
import static com.techme.direction.helper.VariablesHelper.countryName;


public class AddDiningFragment extends Fragment {

    private RecyclerView recyclerView;
    private AddStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private SearchView searchView;
    private List<Store> origList = new ArrayList<>();

    public AddDiningFragment() {
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
        View view = inflater.inflate(R.layout.add_dining_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_add_dining);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        observer();
        itemClicked();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE); // to ensure how many items should be hold in the cache after scrolling
        adapter = new AddStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllUnSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for (Store store : stores) {
                    if (store.getCountryName().equals(countryName) &&
                            store.getType().equals(DINING))
                        list.add(store);
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

    /**
     * this method is to update the selected items in add store and send them to my store list
     */
    private void itemClicked() {
        adapter.setOnItemClickListener(new AddStoreRecycleAdapter.onItemClickListener() {
            @Override
            public void onClick(Store store, int position) {
                Store myStore = adapter.getStore(position);
                myStore.setSelected(TRUE);
                viewModel.updateStore(myStore);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.bar_search);
        menuItem.collapseActionView();
        searchView = (SearchView) menuItem.getActionView();
        search(menuItem);
    }

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
                        for (Store store : viewModel.searchAddStore(name)) {
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
}
