package com.techme.direction.ui.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.techme.direction.R;
import com.techme.direction.ui.SelectNoteActivity;


public class MyNoteFragment extends Fragment {

    private ImageView imgAdd;

    public MyNoteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.my_note_fragment, container, false);
        imgAdd =  view.findViewById(R.id.img_add_my_note);
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SelectNoteActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }


}
