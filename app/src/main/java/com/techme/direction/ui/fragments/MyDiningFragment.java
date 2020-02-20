package com.techme.direction.ui.fragments;

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

import com.techme.direction.DirectionViewModel;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyStoreRecycleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MyDiningFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private RelativeLayout layoutEmpty;
    private List<Store> origList = new ArrayList<>();
    private SearchView searchView;

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


        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyStoreRecycleItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(7);
        adapter = new MyStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for (Store store : stores) {
                    if (store.getCountryName().equals("Canada") && store.getType().equals(VariablesHelper.DINING)) {
                        list.add(store);
                    }
                }
                if(!list.isEmpty()){
                    recyclerView.setVisibility(View.VISIBLE);
                    layoutEmpty.setVisibility(View.GONE);
                }else {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                origList = new ArrayList<>(list);

                adapter.submitList(list);

                if(searchView != null && searchView.getQuery().length() > 0){
                    String temp = String.valueOf(searchView.getQuery());
                    searchView.setQuery("", false);
                    searchView.setQuery(temp, false);
                }
            }
        });
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        searchView.setQuery(VariablesHelper.REPLACE,true);
//    }



    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        adapter.getStore(viewHolder.getAdapterPosition()).setSelected(VariablesHelper.FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.bar_search);
        searchView = (SearchView) menuItem.getActionView();
        search(menuItem);
    }

    /**
     * handles th search view
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
                        for(Store store: viewModel.searchMyStore(name)){
                            if(store.getType().equals(VariablesHelper.DINING) &&
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
