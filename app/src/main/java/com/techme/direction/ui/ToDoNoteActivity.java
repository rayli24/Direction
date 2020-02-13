package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.ToDoList;
import com.techme.direction.adapter.ToDoNoteRecycleAdapter;
import com.techme.direction.helper.ToDoNoteRecycleItemTouchHelper;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * this class displays a list of all the To-do for a specific note
 */
public class ToDoNoteActivity extends AppCompatActivity implements ToDoNoteRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private ImageView imgBack;
    private RecyclerView recyclerView;
    private DirectionViewModel viewModel;
    private ToDoNoteRecycleAdapter adapter;
    private String titleName;
    private long noteId;
    private TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_note);
        init();
        observer();
        buttonEvent();
        onItemClick();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack =
                new ToDoNoteRecycleItemTouchHelper(0,ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void init(){
        imgBack = findViewById(R.id.img_to_do_note_back);
        txtName = findViewById(R.id.txt_to_do_note_layout_title);
        recyclerView = findViewById(R.id.recycle_view_to_do_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new ToDoNoteRecycleAdapter();
        recyclerView.setAdapter(adapter);
        titleName = getIntent().getStringExtra(VariablesHelper.EXTRA_NOTE_NAME);
        noteId = getIntent().getLongExtra(VariablesHelper.EXTRA_NOTE_ID,-1);
        txtName.setText(titleName + " ToDo List");
    }

    private void observer(){
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllToDoList().observe(this, new Observer<List<ToDoList>>() {
            @Override
            public void onChanged(List<ToDoList> toDoLists) {
                List<ToDoList> list = new ArrayList<>();
                for(ToDoList toDo: toDoLists){
                    if(toDo.getNote_id() == noteId){
                        list.add(toDo);
                    }
                }
                adapter.submitList(list);
            }
        });
    }

    private void buttonEvent(){
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void onItemClick(){
        adapter.setOnItemClickListener(new ToDoNoteRecycleAdapter.onItemClickListener() {
            @Override
            public void onChecked(ToDoList toDo, boolean checked) {
                ToDoList toDoList = toDo;
                toDoList.setChecked(checked);
                viewModel.updateToDoList(toDoList);
            }
        });
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        ToDoList toDoList = adapter.getToDo(viewHolder.getAdapterPosition());
        viewModel.deleteToDoList(toDoList);
    }
}
