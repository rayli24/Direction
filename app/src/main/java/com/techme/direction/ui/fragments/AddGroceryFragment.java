package com.techme.direction.ui.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import androidx.appcompat.widget.SearchView;

import com.techme.direction.Note;
import com.techme.direction.adapter.AddStoreRecycleAdapter;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class AddGroceryFragment extends Fragment {

    private AddStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Store> origList = new ArrayList<>();

    public AddGroceryFragment() {
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
        View view = inflater.inflate(R.layout.add_grocery_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_add_grocery);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        observer();
//        searchView.setQuery(VariablesHelper.REPLACE,true); // to close the search view if open
        itemClicked();
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(VariablesHelper.RECYCLE_CACHE);
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
                    if (store.getCountryName().equals(VariablesHelper.countryName) &&
                            store.getType().equals(VariablesHelper.GROCERY)) {
                        list.add(store);
                    }
                }
                origList = new ArrayList<>(list);

                adapter.submitList(list);

                if(searchView != null & searchView.getQuery().length() > 0){
                    String temp = String.valueOf(searchView.getQuery());
                    searchView.setQuery("",false);
                    searchView.setQuery(temp,false);
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
                myStore.setSelected(VariablesHelper.TRUE);
                Note note = new Note(myStore.getName(), VariablesHelper.FALSE);
                viewModel.insertNote(note);
                viewModel.updateStore(myStore);
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.bar_search);
        searchView = (SearchView) menuItem.getActionView();
        search(menuItem);
    }

    /**
     * handles the search view
     * @param menuItem
     */
    private void search(final MenuItem menuItem){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(!newText.isEmpty()){
                    String name = "%" + newText + "%";
                    List<Store> list = new ArrayList<>();
                    try {
                        for(Store store: viewModel.searchAddStore(name)){
                            if(store.getType().equals(VariablesHelper.GROCERY) &&
                                    store.getCountryName().equals(VariablesHelper.countryName)){
                                list.add(store);
                            }
                        }
                        adapter.submitList(list);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else{
                    adapter.submitList(origList);
                }
                return true;
            }
        });
    }
}
