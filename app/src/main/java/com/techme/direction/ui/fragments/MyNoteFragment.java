package com.techme.direction.ui.fragments;

import android.content.Intent;
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
import android.widget.ImageView;

import com.techme.direction.CreateNote;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.MyNoteRecycleAdapter;
import com.techme.direction.adapter.SelectNoteRecycleAdapter;
import com.techme.direction.helper.MyNoteRecycleItemTouchHelper;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.ui.CreateNoteActivity;
import com.techme.direction.ui.SelectNoteActivity;

import java.util.ArrayList;
import java.util.List;


public class MyNoteFragment extends Fragment implements MyNoteRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyNoteRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private ImageView imgAdd;

    public MyNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_note_fragment, container, false);
        imgAdd =  view.findViewById(R.id.img_add_my_note);
        recyclerView = view.findViewById(R.id.recycle_view_my_notes);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonsEvent();
        init();
        viewModelMethod();
        onEditCLick();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyNoteRecycleItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void onEditCLick(){
        adapter.setOnItemClickListener(new MyNoteRecycleAdapter.onItemClickListener() {
            @Override
            public void onEditClick(Note note) {
                Intent intent = new Intent(getContext(),CreateNoteActivity.class);
                intent.putExtra(VariablesHelper.EXTRA_NOTE,note.getName());
                intent.putExtra(VariablesHelper.EXTRA_NOTE_ID,note.getNote_id());
                startActivity(intent);
            }
        });
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new MyNoteRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void viewModelMethod(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                List<Note> list = new ArrayList<>();
                for(Note note: notes){
                    if(note.getSelected() == VariablesHelper.TRUE){
                        list.add(note);
                    }
                }
                adapter.submitList(notes);
            }
        });
    }

    private void buttonsEvent(){
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SelectNoteActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Note note = adapter.getNote(viewHolder.getAdapterPosition());
        note.setSelected(VariablesHelper.FALSE);
        viewModel.updateNote(note);
        // delete all the create notes that belong to the note by getting the note id
        // remember to check for when a grocery store has been deleted from my store list to delete it from notes as well
        //for(CreateNote createNote: viewModel.getAllCreateNote().)
        //viewModel.deleteCreateNote();
    }
}
