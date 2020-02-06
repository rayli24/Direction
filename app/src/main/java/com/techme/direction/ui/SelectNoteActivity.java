package com.techme.direction.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.techme.direction.R;

public class SelectNoteActivity extends AppCompatActivity {

    private ImageView imgSearch;
    private ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_note);
        imgBack = findViewById(R.id.img_select_note_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectNoteActivity.this, CreateNoteActivity.class);
                startActivity(intent);
            }
        });
    }
}
