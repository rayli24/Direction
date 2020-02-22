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
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.R;
import com.techme.direction.adapter.MyNoteRecycleAdapter;
import com.techme.direction.helper.MyNoteRecycleItemTouchHelper;
import com.techme.direction.helper.VariablesHelper;
import com.techme.direction.ui.ToDoListActivity;
import com.techme.direction.ui.SelectNoteActivity;
import com.techme.direction.ui.ToDoNoteActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.techme.direction.helper.VariablesHelper.EXTRA_NOTE_ID;
import static com.techme.direction.helper.VariablesHelper.EXTRA_NOTE_NAME;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.REPLACE;
import static com.techme.direction.helper.VariablesHelper.TRUE;


public class MyNoteFragment extends Fragment implements MyNoteRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private RecyclerView recyclerView;
    private MyNoteRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private ImageView imgAdd;
    private RelativeLayout layoutEmpty;
    private List<Note> origList = new ArrayList<>();
    private SearchView searchView;

    public MyNoteFragment() {
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
        View view = inflater.inflate(R.layout.my_note_fragment, container, false);
        imgAdd =  view.findViewById(R.id.img_add_my_note);
        recyclerView = view.findViewById(R.id.recycle_view_my_notes);
        layoutEmpty = view.findViewById(R.id.note_layout_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        buttonsEvent();
        init();
        observer();
        onItemCLick();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new MyNoteRecycleItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }


    private void onItemCLick(){
        adapter.setOnItemClickListener(new MyNoteRecycleAdapter.onItemClickListener() {
            @Override
            public void onEditClick(Note note) {
                Intent intent = new Intent(getContext(), ToDoListActivity.class);
                intent.putExtra(EXTRA_NOTE_NAME,note.getName());
                intent.putExtra(EXTRA_NOTE_ID,note.getNote_id());
                startActivity(intent);
            }

            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(getContext(), ToDoNoteActivity.class);
                intent.putExtra(EXTRA_NOTE_NAME,note.getName());
                intent.putExtra(EXTRA_NOTE_ID,note.getNote_id());
                startActivity(intent);
            }
        });
    }

    private void init(){
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE);
        adapter = new MyNoteRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllNotes().observe(getViewLifecycleOwner(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                List<Note> list = new ArrayList<>();
                for(Note note: notes){
                    if(note.getSelected() == TRUE){
                        list.add(note);
                    }
                }
                if(!list.isEmpty()){
                    recyclerView.setVisibility(View.VISIBLE);
                    layoutEmpty.setVisibility(View.GONE);
                }else {
                    layoutEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                // to save the actual list before the searching starts
                origList = new ArrayList<>(list);

                adapter.submitList(list);

                // to keep the correct list to be showed after removing an item
                if(searchView != null && searchView.getQuery().length() > 0){
                    String temp = String.valueOf(searchView.getQuery());
                    searchView.setQuery("",false);
                    searchView.setQuery(temp,false);
                }

            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        searchView.setQuery(REPLACE,true);
    }

    private void buttonsEvent(){
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery(REPLACE, true);
                Intent intent = new Intent(getContext(), SelectNoteActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        Note note = adapter.getNote(viewHolder.getAdapterPosition());
        // delete all the To-Do list that belong to the note by getting the note id
        viewModel.deleteAllNotesId(note.getNote_id());
        note.setSelected(FALSE);
        viewModel.updateNote(note);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.bar_search);
        MenuItem setting = menu.findItem(R.id.bar_settings);
        setting.setVisible(false);
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
            public boolean onQueryTextSubmit(String s) {
                searchView.setQuery("",false);
                menuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(!s.isEmpty()){
                    try {
                        String name = "%" + s + "%";
                        adapter.submitList(viewModel.searchNote(name));
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
