package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.techme.direction.ToDoList;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.adapter.ToDoListRecycleAdapter;
import com.techme.direction.helper.ToDoListRecycleItemTouchHelper;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity implements ToDoListRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private boolean boolEdit = false;
    private int editPosition = -1;
    private long noteId;
    private String titleName;
    private TextView txtName, txtDone;
    private EditText editAmount, editItem;
    private Button btnAdd;
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private ToDoListRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list);
        init();
        observer();
        onItemClick();
        onAddButtonClick();
        onDoneButtonClick();
        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new ToDoListRecycleItemTouchHelper(0,
                ItemTouchHelper.LEFT,this);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(recyclerView);
    }

    private void init() {
        txtName = findViewById(R.id.txt_name_to_do_list);
        editAmount = findViewById(R.id.edit_amount_to_do_list);
        editItem = findViewById(R.id.edit_item_to_do_list);
        txtDone = findViewById(R.id.txt_done_to_do_list);
        btnAdd = findViewById(R.id.btn_add_to_do_list);
        recyclerView = findViewById(R.id.recycle_view_to_do_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(VariablesHelper.RECYCLE_CACHE);
        adapter = new ToDoListRecycleAdapter();
        recyclerView.setAdapter(adapter);
        titleName = getIntent().getStringExtra(VariablesHelper.EXTRA_NOTE_NAME);
        txtName.setText(titleName + " Note");
        noteId = getIntent().getLongExtra(VariablesHelper.EXTRA_NOTE_ID,-1);
    }

    private void observer() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllToDoList().observe(this, new Observer<List<ToDoList>>() {
            @Override
            public void onChanged(List<ToDoList> toDoLists) {
                List<ToDoList> list = new ArrayList<>();
                for(ToDoList toDoList : toDoLists){
                    if(toDoList.getNote_id() == noteId){ // check if the note id belongs to the created note
                        list.add(toDoList);
                    }
                }
                adapter.submitList(list);
            }
        });
    }

    /**
     * handles the the edit and check box items in the recycle view
     */
    private void onItemClick() {
        adapter.setOnItemClickListener(new ToDoListRecycleAdapter.onItemClickListener() {
            @Override
            public void onEditClick(ToDoList toDoList, int position) {
                editAmount.setText(String.valueOf(toDoList.getAmount()));
                editItem.setText(toDoList.getItem());
                btnAdd.setText("edit");
                editPosition = position;
                boolEdit = true;
            }
        });
    }

    private void onDoneButtonClick(){
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * this method checks if the item is new or an already existing item that need to be edit
     */
    private void onAddButtonClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(editItem.getText().toString().trim().isEmpty() || editAmount.getText().toString().trim().isEmpty())) {
                    String item = editItem.getText().toString();
                    int amount = Integer.parseInt(editAmount.getText().toString());

                    if (boolEdit && editPosition != -1) {
                        ToDoList oldNote = adapter.getToDoList(editPosition);
                        long id = oldNote.getTo_do_id();
                        int timestamp = oldNote.timestamp();
                        boolean checked = oldNote.isChecked();
                        ToDoList toDoList = new ToDoList(item, amount, checked, timestamp, noteId);
                        toDoList.setTo_do_id(id);
                        viewModel.updateToDoList(toDoList);
                        btnAdd.setText("add");
                        boolEdit = false;
                        editPosition = -1;
                    } else {
                        int timestamp = adapter.getItemCount() + 1;
                        ToDoList toDoList = new ToDoList(item, amount, false, timestamp, noteId);
                        viewModel.insertToDoList(toDoList);
                    }
                    editItem.requestFocus();
                    editAmount.setText("");
                    editItem.setText("");
                }
                else{
                    Toast.makeText(ToDoListActivity.this, "Make sure to fill item and amount",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_action_bar, menu);
//        menu.findItem(R.id.bar_settings).setVisible(false);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.bar_search:
//                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//
//    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        ToDoList toDoList = adapter.getToDoList(viewHolder.getAdapterPosition());
        viewModel.deleteToDoList(toDoList);
    }
}
