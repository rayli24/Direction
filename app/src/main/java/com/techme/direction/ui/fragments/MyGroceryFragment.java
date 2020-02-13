package com.techme.direction.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyStoreRecycleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MyGroceryFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private DirectionViewModel viewModel;
    private MyStoreRecycleAdapter adapter;
    private Note searchedNote = null;
    public MyGroceryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_grocery_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycle_view_my_grocery);
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
        recyclerView.setItemViewCacheSize(20);
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
                    if (store.getCountryName().equals("Canada") && store.getType().equals(VariablesHelper.GROCERY)) {
                        list.add(store);
                    }
                }
                adapter.submitList(list);
            }
        });

//        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
//            @Override
//            public void onChanged(List<Note> notes) {
//
//            }
//        });
//
//        viewModel.getAllSearchNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
//            @Override
//            public void onChanged(List<Note> notes) {
//                if(notes.size() >0){
//                    searchedNote = notes.get(0);
//                }
//            }
//        });

    }

    //todo check when search for a name if you are able to delete it
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Store store = adapter.getStore(viewHolder.getAdapterPosition());
        String name = store.getName();


        try {
            if(!viewModel.noteTest(name).isEmpty()) {
                searchedNote = viewModel.noteTest(name).get(0);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // this if statement checks if the grocery note was used to create its own To-Do list
        if (searchedNote != null && searchedNote.getSelected() == VariablesHelper.TRUE) {
            // deletes all the To-Do list that belong to the note
            viewModel.deleteAllNotesId(searchedNote.getNote_id());
        }
        viewModel.deleteNote(searchedNote);
        store.setSelected(VariablesHelper.FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
    }


}
