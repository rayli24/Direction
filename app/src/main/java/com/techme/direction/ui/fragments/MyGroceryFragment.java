package com.techme.direction.ui.fragments;

import android.content.Intent;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyStoreRecycleAdapter;
import com.techme.direction.ui.CountryActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_OK;


public class MyGroceryFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private DirectionViewModel viewModel;
    private MyStoreRecycleAdapter adapter;
    private Note searchedNote = null;
    private RelativeLayout layoutEmpty;
    private SearchView searchView;
    private List<Store> origList = new ArrayList<>();
    private List<Note> noteList = new ArrayList<>();

    public MyGroceryFragment() {
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
        View view = inflater.inflate(R.layout.my_grocery_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_my_grocery);
        layoutEmpty = view.findViewById(R.id.my_layout_grocery_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        observer();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyStoreRecycleItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void init() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(VariablesHelper.RECYCLE_CACHE);
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
                    if (store.getCountryName().equals(VariablesHelper.countryName)
                            && store.getType().equals(VariablesHelper.GROCERY)) {
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

                // to keep the correct list after removing a searched item
                if(searchView != null && searchView.getQuery().length() > 0){
                    String temp = String.valueOf(searchView.getQuery());
                    searchView.setQuery("", false);
                    searchView.setQuery(temp, false);
                }
            }
        });

        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteList = new ArrayList<>(notes);
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

        if(!noteList.isEmpty()) {
            for(Note note: noteList){
                if(note.getName().equals(name)){
                    searchedNote = note;
                    break;
                }
            }

            // this if statement checks if the grocery note was used to create its own To-Do list
            if (searchedNote.getSelected() == VariablesHelper.TRUE) {
                // deletes all the To-Do list that belong to the note
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
        MenuItem locationItem = menu.findItem(R.id.bar_location);
        requestNewLocation(locationItem);
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.isEmpty()) {
                    String name = "%" + newText + "%";
                    try {
                        List<Store> list = new ArrayList<>();
                        for(Store store: viewModel.searchMyStore(name)){
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
                }else {
                    adapter.submitList(origList);
                }
                return true;
            }
        });
    }

    /**
     * open th country activity to retrieve the new chosen location
     * @param location
     */
    private void requestNewLocation(MenuItem location){
        location.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(getContext(), CountryActivity.class);
                startActivityForResult(intent,VariablesHelper.EXTRA_COUNTRY_CODE);
                return true;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // refresh recycle view
        if(requestCode == VariablesHelper.EXTRA_COUNTRY_CODE && resultCode == RESULT_OK){
            observer();
            System.out.println("grocery list size is: " + origList.size());
            // create a new daily note
            Note note = new Note(VariablesHelper.DAILY_NOTES,VariablesHelper.FALSE);
            viewModel.insertNote(note);
            if(!origList.isEmpty()) {
                // create old empty notes from new location country
                for(Store store: origList){
                    if(store.getType().equals(VariablesHelper.GROCERY) &&
                            store.getCountryName().equals(VariablesHelper.countryName)){
                        note = new Note(store.getName(),VariablesHelper.FALSE);
                        viewModel.insertNote(note);
                    }
                }
            }
            Toast.makeText(getContext(), "Country has been changed", Toast.LENGTH_SHORT).show();
        }
    }
}
