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

import com.techme.direction.CreateNote;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.helper.ReceiveDataHelper;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.helper.MyStoreRecycleItemTouchHelper;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyStoreRecycleAdapter;

import java.util.ArrayList;
import java.util.List;


public class MyGroceryFragment extends Fragment implements MyStoreRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private DirectionViewModel viewModel;
    private MyStoreRecycleAdapter adapter;

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
        viewModelMethod();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyStoreRecycleItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new MyStoreRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void viewModelMethod(){
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
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Store store = adapter.getStore(viewHolder.getAdapterPosition());
        String name = store.getName();
        // this if statement checks if the item that is about to be delete is a grocery
        // and then checks if that grocery note was used to create its own create notes
        if(store.getType().equals(VariablesHelper.GROCERY)){
            Note note = ReceiveDataHelper.getNoteData(getViewLifecycleOwner(), name);
            if(note.getSelected() == VariablesHelper.TRUE){
                long noteId = note.getNote_id();
                for(CreateNote createNote: ReceiveDataHelper.getCreateNoteData(getViewLifecycleOwner(),noteId)){
                    viewModel.deleteCreateNote(createNote);
                }
            }
            viewModel.deleteNote(note);
        }
        store.setSelected(VariablesHelper.FALSE);
        viewModel.updateStore(adapter.getStore(viewHolder.getAdapterPosition()));
    }
}
