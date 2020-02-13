package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techme.direction.Note;
import com.techme.direction.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MyNoteRecycleAdapter extends ListAdapter<Note, MyNoteRecycleAdapter.NoteViewHolder> {
    private onItemClickListener listener;

    public MyNoteRecycleAdapter() {
        super(Diff_Callback);
    }

    private static final DiffUtil.ItemCallback<Note> Diff_Callback = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getNote_id() == newItem.getNote_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.getName().equals(newItem.getName()) && oldItem.getSelected() == newItem.getSelected();
        }
    };

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgEdit;
        public RelativeLayout viewForeground, viewBackground;
        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_note_name_recycle_view);
            imgEdit = itemView.findViewById(R.id.img_edit_notes_recycle_view);
            viewForeground = itemView.findViewById(R.id.layout_foreground_note_recycle_view);
            viewBackground = itemView.findViewById(R.id.layout_background_note_recycle_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onItemClick(getNote(position));
                    }
                }
            });
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onEditClick(getNote(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_recycle_view,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = getNote(position);
        holder.txtName.setText(note.getName() + " note");
    }

    public Note getNote(int position){
        return getItem(position);
    }

    public interface onItemClickListener{
        void onEditClick(Note note);
        void onItemClick(Note note);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
