package com.techme.direction.ui.fragments;

import androidx.appcompat.widget.SearchView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.adapter.MyStoreRecycleAdapter;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyStoresFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private Note searchedNote = null;
    private RelativeLayout layoutEmpty;
    private List<Store> origList = new ArrayList<>();
    private SearchView searchView;

    public static MyStoresFragment newInstance() {
        return new MyStoresFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_stores_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_my_store);
        layoutEmpty = view.findViewById(R.id.my_layout_store_empty);
        getActivity().invalidateOptionsMenu();
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
                String name = VariablesHelper.stringToUri(store.getName());
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
        recyclerView.setItemViewCacheSize(7);
        adapter = new MyStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllSelectedStores().observe(getViewLifecycleOwner(), new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for (Store store : stores) {
                    if (store.getCountryName().equals(VariablesHelper.countryName)) {
                        list.add(store);
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

                // to keep the correct list after removing a searched item
                if (searchView != null && searchView.getQuery().length() > 0) {
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
        Store store = adapter.getStore(viewHolder.getAdapterPosition());
        String name = store.getName();
        // this if statement checks if the item that is about to be delete is a grocery
        // and then checks if that grocery note was used to create its own To-Do list
        if (store.getType().equals(VariablesHelper.GROCERY)) {
            try {
                if (!viewModel.searchNote(name).isEmpty()) {
                    searchedNote = viewModel.searchNote(name).get(0);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if (searchedNote.getSelected() == VariablesHelper.TRUE) {
                viewModel.deleteAllNotesId(searchedNote.getNote_id());
            }
            viewModel.deleteNote(searchedNote);
        }
        store.setSelected(VariablesHelper.FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
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
    private void search(final MenuItem menuItem) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                menuItem.collapseActionView();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    try {
                        String name = "%" + newText + "%";
                        List<Store> list = new ArrayList<>();
                        for (Store store: viewModel.searchMyStore(name)){
                            if(store.getCountryName().equals(VariablesHelper.countryName)){
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
