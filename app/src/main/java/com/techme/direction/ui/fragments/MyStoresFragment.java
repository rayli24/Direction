package com.techme.direction.ui.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.adapter.MyStoreRecycleAdapter;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.ui.MapsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyStoresFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyStoreRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private Note searchedNote = null;

    public static MyStoresFragment newInstance() {
        return new MyStoresFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_stores_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_my_store);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        observer();
        onItemClick();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyStoreRecycleItemTouchHelper(0,  ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);

    }

    private void onItemClick(){
        adapter.setOnItemClickListener(new MyStoreRecycleAdapter.onItemClickListener() {
            @Override
            public void onclick(Store store) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
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
                    if (store.getCountryName().equals("Canada")) {
                        list.add(store);
                    }
                }
                adapter.submitList(list);
            }
        });

//        viewModel.getSearchNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
//            @Override
//            public void onChanged(List<Note> notes) {
//                searchNote = notes.get(0);
//            }
//        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Store store = adapter.getStore(viewHolder.getAdapterPosition());
        String name = store.getName();
        // this if statement checks if the item that is about to be delete is a grocery
        // and then checks if that grocery note was used to create its own To-Do list
        if(store.getType().equals(VariablesHelper.GROCERY)){
            try {
                if(!viewModel.noteTest(name).isEmpty()) {
                    searchedNote = viewModel.noteTest(name).get(0);
                }
            }catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(searchedNote.getSelected() == VariablesHelper.TRUE){
               viewModel.deleteAllNotesId(searchedNote.getNote_id());
            }
            viewModel.deleteNote(searchedNote);
        }

        store.setSelected(VariablesHelper.FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
    }
}
