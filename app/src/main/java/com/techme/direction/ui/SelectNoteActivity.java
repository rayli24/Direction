package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;
import com.techme.direction.R;
import com.techme.direction.Store;
import com.techme.direction.adapter.SelectNoteRecycleAdapter;
import com.techme.direction.ui.fragments.MyNoteFragment;

import java.util.ArrayList;
import java.util.List;

public class SelectNoteActivity extends AppCompatActivity {
    public static final String GROCERY = "grocery";
    private SelectNoteRecycleAdapter adapter;
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private SearchView search;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_note);
        init();
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllSelectedStores().observe(this, new Observer<List<Store>>() {
            @Override
            public void onChanged(List<Store> stores) {
                List<Store> list = new ArrayList<>();
                for(Store store: stores){
                    if(store.getType().equals(GROCERY)){
                        list.add(store);
                    }
                }
                adapter.submitList(list);
            }
        });

        buttonEvents();

    }

    private void init(){
        search = findViewById(R.id.search_select_note);
        imgBack = findViewById(R.id.img_select_note_back);
        recyclerView = findViewById(R.id.recycle_view_select_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new SelectNoteRecycleAdapter();
    }

    public void buttonEvents(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectNoteActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });

        adapter.setOnclickItemListener(new SelectNoteRecycleAdapter.onClickItemListener() {
            @Override
            public void onClick(Store store) {
                Note note = new Note(store.getName(),1);
                viewModel.insertNote(note);
                Intent intent = new Intent(SelectNoteActivity.this, MyNoteFragment.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
