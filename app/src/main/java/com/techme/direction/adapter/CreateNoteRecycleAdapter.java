package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techme.direction.CreateNote;
import com.techme.direction.Note;
import com.techme.direction.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CreateNoteRecycleAdapter extends ListAdapter<CreateNote,CreateNoteRecycleAdapter.CreateViewHolder> {
    private onItemClickListener listener;

    public CreateNoteRecycleAdapter() {
        super(Diff_Callback);
    }

    private static DiffUtil.ItemCallback<CreateNote> Diff_Callback = new DiffUtil.ItemCallback<CreateNote>() {
        @Override
        public boolean areItemsTheSame(@NonNull CreateNote oldItem, @NonNull CreateNote newItem) {
            return oldItem.getCreate_id() == newItem.getCreate_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull CreateNote oldItem, @NonNull CreateNote newItem) {
            return oldItem.getAmount() == newItem.getAmount() && oldItem.getItem().equals(newItem.getItem())
                    && oldItem.timestamp() == newItem.timestamp();
        }
    };



    public class CreateViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgEdit;
        private CheckBox chkSelect;
        private TextView txtAmount, txtItem;
        public RelativeLayout viewForeground, viewBackground;
        public CreateViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEdit = itemView.findViewById(R.id.img_edit_create_note_recycle_view);
            chkSelect = itemView.findViewById(R.id.chk_done_create_recycle_view);
            txtAmount = itemView.findViewById(R.id.txt_amount_create_recycle_view);
            txtItem = itemView.findViewById(R.id.txt_item_create_recycle_view);
            viewForeground = itemView.findViewById(R.id.layout_foreground_create_note_recycle_view);
            viewBackground = itemView.findViewById(R.id.layout_background_create_note_recycle_view);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onEditClick(getCreateNote(position),position);
                    }
                }
            });

            chkSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null & position != RecyclerView.NO_POSITION){
                        listener.onCheckClick(getCreateNote(position), chkSelect.isChecked());
                    }
                }
            });
        }
    }


    @NonNull
    @Override
    public CreateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.create_note_recycle_view,parent,false);
        return new CreateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreateViewHolder holder, int position) {
        CreateNote createNote = getCreateNote(position);
        holder.txtItem.setText(createNote.getItem());
        holder.txtAmount.setText(String.valueOf(createNote.getAmount()));
        holder.chkSelect.setChecked(createNote.isChecked());
    }


    public CreateNote getCreateNote(int position){
        return getItem(position);
    }

    public interface onItemClickListener{
        void onEditClick(CreateNote createNote, int position);
        void onCheckClick(CreateNote createNote, boolean checked);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
