package com.techme.direction.ui.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techme.direction.adapter.AddStoreRecycleAdapter;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;



public class AddDiningFragment extends Fragment {

    private RecyclerView recyclerView;
    private AddStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;

    public AddDiningFragment() {
        // Required empty public constructor
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

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20); // to ensure how many items should be hold in the cache after scrolling
        adapter = new AddStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllUnSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for(Store store: stores)
                {
                    if(store.getCountryName().equals("Canada") && store.getType().equals(VariablesHelper.DINING))
                        list.add(store);
                }
                adapter.submitList(list);
            }
        });
    }

    /**
     * this method is to update the selected items in add store and send them to my store list
     */
    private void itemClicked()
    {
        adapter.setOnItemClickListener(new AddStoreRecycleAdapter.onItemClickListener() {
            @Override
            public void onClick(Store store, int position) {
                Store myStore = adapter.getStore(position);
                myStore.setSelected(VariablesHelper.TRUE);
                viewModel.updateStore(myStore);
            }
        });
    }
}
