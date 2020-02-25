package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techme.direction.Note;
import com.techme.direction.R;
import com.techme.direction.Store;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SelectNoteRecycleAdapter extends ListAdapter<Note, SelectNoteRecycleAdapter.SelectNoteViewHolder> {
    private onClickItemListener listener;

    public SelectNoteRecycleAdapter() {
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

    public class SelectNoteViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;

        public SelectNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_select_note_name_recycle_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(getNote(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public SelectNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_note_recycle_view, parent, false);
        return new SelectNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectNoteViewHolder holder, int position) {
        Note note = getNote(position);
        holder.txtName.setText(note.getName() + " Note");
    }


    public Note getNote(int position) {
        return getItem(position);
    }

    public interface onClickItemListener {
        void onClick(Note note);
    }

    public void setOnclickItemListener(onClickItemListener listener) {
        this.listener = listener;
    }

}
