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
import android.widget.TabHost;
import android.widget.Toast;

import com.techme.direction.adapter.AddStoreRecycleAdapter;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.Store;

import java.util.ArrayList;
import java.util.List;


public class AddStoresFragment extends Fragment {
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private AddStoreRecycleAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_stores_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_add_store);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new AddStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllUnSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                // update recycle view
                List<Store> list = new ArrayList<>();
                for(Store store: stores)
                {
                    if(store.getCountryName().equals("Canada")) {
                        list.add(store);
                        System.out.println("name in view " + store.getName());
                        System.out.println("selected in view "+store.getSelected());
                    }
                }
                Toast.makeText(getContext(), "Done with the list", Toast.LENGTH_SHORT).show();;
                adapter.setList(list);
            }
        });
        itemClicked();
    }

    /**
     * this method is to update the selected items in add store and send them to my store list
     */
    private void itemClicked()
    {
        adapter.setOnItemClickListener(new AddStoreRecycleAdapter.onItemClickListener() {
            @Override
            public void onClick(Store store, int position) {
                adapter.getStore(position).setSelected(1);
                Store myStore = adapter.getStore(position);
                viewModel.updateStore(myStore);
            }
        });
    }

}