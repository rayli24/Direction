package com.techme.direction.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.techme.direction.DirectionViewModel;
import com.techme.direction.R;
import com.techme.direction.adapter.SelectNoteRecycleAdapter;
import com.techme.direction.ui.SelectNoteActivity;


public class MyNoteFragment extends Fragment {

    private RecyclerView recyclerView;
    private SelectNoteRecycleAdapter adapter;
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


}
