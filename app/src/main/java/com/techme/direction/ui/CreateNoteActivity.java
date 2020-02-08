package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.techme.direction.CreateNote;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.adapter.CreateNoteRecycleAdapter;
import com.techme.direction.helper.CreateNoteRecycleItemTouchHelper;
import com.techme.direction.helper.VariablesHelper;

import java.util.ArrayList;
import java.util.List;

public class CreateNoteActivity extends AppCompatActivity implements CreateNoteRecycleItemTouchHelper.RecyclerItemTouchHelperListener {

    private boolean boolEdit = false;
    private int editPosition = -1;
    private long noteId;
    private EditText editAmount, editItem;
    private Button btnAdd;
    private ImageView imgSave;
    private DirectionViewModel viewModel;
    private RecyclerView recyclerView;
    private CreateNoteRecycleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        init();
        viewModelMethod();
        onItemClick();
        onButtonClick();

        ItemTouchHelper.SimpleCallback itemTouchHelperCallBack = new CreateNoteRecycleItemTouchHelper(0,
                ItemTouchHelper.LEFT,this);
    }

    public void init() {
        editAmount = findViewById(R.id.edit_amount_create_note);
        editItem = findViewById(R.id.edit_item_create_note);
        imgSave = findViewById(R.id.img_save_create_note);
        recyclerView = findViewById(R.id.recycle_view_create_note);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        adapter = new CreateNoteRecycleAdapter();
        recyclerView.setAdapter(adapter);
        noteId = getIntent().getLongExtra(VariablesHelper.EXTRA_NOTE_ID,-1);
    }

    public void viewModelMethod() {
        viewModel = new ViewModelProvider(this).get(DirectionViewModel.class);
        viewModel.getAllCreateNote().observe(this, new Observer<List<CreateNote>>() {
            @Override
            public void onChanged(List<CreateNote> createNotes) {
                List<CreateNote> list = new ArrayList<>();
                for(CreateNote createNote: createNotes){
                    if(createNote.getNote_id() == noteId){ // check if the note id belongs to the created note
                        list.add(createNote);
                    }
                }
                adapter.submitList(list);
            }
        });
    }

    /**
     * handles the the edit and check box items in the recycle view
     */
    public void onItemClick() {
        adapter.setOnItemClickListener(new CreateNoteRecycleAdapter.onItemClickListener() {
            @Override
            public void onEditClick(CreateNote createNote, int position) {
                editAmount.setText(String.valueOf(createNote.getAmount()));
                editItem.setText(createNote.getItem());
                editPosition = position;
                boolEdit = true;
            }

            @Override
            public void onCheckClick(CreateNote createNote, boolean checked) {
                CreateNote myCreateNote = createNote;
                myCreateNote.setChecked(checked);
                viewModel.updateCreateNote(myCreateNote);
            }
        });
    }

    /**
     * this method checks if the item is new or an already existing item that need to be edit
     */
    public void onButtonClick() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(editItem.getText().toString().trim().isEmpty() || editAmount.getText().toString().trim().isEmpty())) {
                    String item = editItem.getText().toString();
                    int amount = Integer.parseInt(editAmount.getText().toString());

                    if (boolEdit && editPosition != -1) {
                        CreateNote oldNote = adapter.getCreateNote(editPosition);
                        long id = oldNote.getCreate_id();
                        int timestamp = oldNote.timestamp();
                        boolean checked = oldNote.isChecked();
                        CreateNote createNote = new CreateNote(item, amount, checked, timestamp, noteId);
                        createNote.setCreate_id(id);
                        viewModel.updateCreateNote(createNote);
                        boolEdit = false;
                        editPosition = -1;
                    } else {
                        int timestamp = adapter.getItemCount() + 1;
                        CreateNote createNote = new CreateNote(item, amount, false, timestamp, noteId);
                        viewModel.insertCreateNote(createNote);
                    }
                }
                else{
                    Toast.makeText(CreateNoteActivity.this, "Make sure to fill item and amount",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_action_bar, menu);
        menu.findItem(R.id.bar_settings).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.bar_search:
                Toast.makeText(this, "search clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        CreateNote createNote = adapter.getCreateNote(position);
        viewModel.deleteCreateNote(createNote);
    }
}
