package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techme.direction.R;
import com.techme.direction.ToDoList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoNoteRecycleAdapter extends ListAdapter<ToDoList,ToDoNoteRecycleAdapter.ToDoNoteViewHolder> {
    private onItemClickListener listener;

    public ToDoNoteRecycleAdapter() {
        super(Diff_Callback);
    }

    public static DiffUtil.ItemCallback<ToDoList> Diff_Callback = new DiffUtil.ItemCallback<ToDoList>() {
        @Override
        public boolean areItemsTheSame(@NonNull ToDoList oldItem, @NonNull ToDoList newItem) {
            return oldItem.getTo_do_id() == newItem.getTo_do_id();
        }

        @Override
        public boolean areContentsTheSame(@NonNull ToDoList oldItem, @NonNull ToDoList newItem) {
            return oldItem.getAmount() == newItem.getAmount() && oldItem.getItem().equals(newItem.getItem())
                    && oldItem.timestamp() == newItem.timestamp();
        }
    };

    public class ToDoNoteViewHolder  extends RecyclerView.ViewHolder {
        private TextView txtItem, txtAmount;
        private CheckBox chkDone;
        public RelativeLayout viewForeground, viewBackground;
        public ToDoNoteViewHolder(@NonNull View itemView) {
            super(itemView);
            txtItem = itemView.findViewById(R.id.txt_item_to_do_note_recycle_view);
            txtAmount = itemView.findViewById(R.id.txt_amount_to_do_note_recycle_view);
            chkDone = itemView.findViewById(R.id.chk_done_to_do_note_recycle_view);
            viewForeground = itemView.findViewById(R.id.layout_foreground_to_do_note_recycle_view);
            viewBackground = itemView.findViewById(R.id.layout_background_to_do_note_recycle_view);
            chkDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onChecked(getToDo(position),chkDone.isChecked());
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ToDoNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_note_recycle_view, parent, false);
        return new ToDoNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoNoteViewHolder holder, int position) {
        ToDoList todo = getToDo(position);
        holder.txtAmount.setText(String.valueOf(todo.getAmount()));
        holder.txtItem.setText(todo.getItem());
        holder.chkDone.setChecked(todo.isChecked());
    }

    public ToDoList getToDo(int position){
        return getItem(position);
    }

    public interface onItemClickListener{
        void onChecked(ToDoList toDo, boolean checked);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }

}
