package com.techme.direction.helper;

import com.techme.direction.CreateNote;
import com.techme.direction.DirectionViewModel;
import com.techme.direction.Note;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class ReceiveDataHelper {

    private static DirectionViewModel viewModel;

    private ReceiveDataHelper() {
    }

    /**
     * this method returns a note if it was selected to write notes in
     * method is used to delete create note that has note_id
     *
     * @param lifecycleOwner
     * @param name
     * @return a note
     */
    public static Note getNoteData(final LifecycleOwner lifecycleOwner, final String name) {
        final Note[] myNote = {null};
        viewModel.getAllNotes().observe(lifecycleOwner, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                for (Note note : notes) {
                    if (note.getName().equals(name)) {
                        myNote[0] = note;
                        return;
                    }
                }
            }

        });
        return myNote[0];
    }

    /**
     * // this method return a list of create notes that belong to a specific note
     * // many to one relationship
     *
     * @param lifecycleOwner
     * @param noteId
     * @return list of create note
     */
    public static List<CreateNote> getCreateNoteData(LifecycleOwner lifecycleOwner, final long noteId) {
        final List<CreateNote> list = new ArrayList<>();
        viewModel.getAllCreateNote().observe(lifecycleOwner, new Observer<List<CreateNote>>() {
            @Override
            public void onChanged(List<CreateNote> createNotes) {
                for (CreateNote createNote : createNotes) {
                    if (createNote.getNote_id() == noteId) {
                        list.add(createNote);
                    }
                }
            }
        });
        return list;
    }
}
