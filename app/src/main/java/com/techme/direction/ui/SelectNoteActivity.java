package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.R;
import com.techme.direction.adapter.SelectNoteRecycleAdapter;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.techme.direction.helper.VariablesHelper.EXTRA_NOTE_ID;
import static com.techme.direction.helper.VariablesHelper.EXTRA_NOTE_NAME;
import static com.techme.direction.helper.VariablesHelper.FALSE;
import static com.techme.direction.helper.VariablesHelper.RECYCLE_CACHE;
import static com.techme.direction.helper.VariablesHelper.TRUE;

public class SelectNoteActivity extends AppCompatActivity {

    private SelectNoteRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_note);
        init();
        observer();
        buttonEvents();
        onItemClicked();
    }

    private void init(){
        imgBack = findViewById(R.id.img_select_note_back);
        recyclerView = findViewById(R.id.recycle_view_to_do_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(RECYCLE_CACHE);
        adapter = new SelectNoteRecycleAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                List<Note> list = new ArrayList<>();
                for(Note note: notes){
                    if(note.getSelected() == FALSE){
                        list.add(note);
                    }
                }
                adapter.submitList(list);
            }
        });
    }

    private void buttonEvents(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void onItemClicked(){

        adapter.setOnclickItemListener(new SelectNoteRecycleAdapter.onClickItemListener() {
            @Override
            public void onClick(Note note) {
                Note myNote = new Note(note.getName(),TRUE);
                myNote.setNote_id(note.getNote_id());
                String name = myNote.getName();
                viewModel.updateNote(myNote);
                Intent intent = new Intent(SelectNoteActivity.this, ToDoListActivity.class);
                intent.putExtra(EXTRA_NOTE_NAME,name);
                intent.putExtra(EXTRA_NOTE_ID,note.getNote_id());
                startActivity(intent);
                finish();
            }
        });
    }


}
