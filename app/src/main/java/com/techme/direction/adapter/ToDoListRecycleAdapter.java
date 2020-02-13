package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.techme.direction.ToDoList;
import com.techme.direction.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class ToDoListRecycleAdapter extends ListAdapter<ToDoList, ToDoListRecycleAdapter.ToDoListViewHolder> {
    private onItemClickListener listener;

    public ToDoListRecycleAdapter() {
        super(Diff_Callback);
    }

    private static DiffUtil.ItemCallback<ToDoList> Diff_Callback = new DiffUtil.ItemCallback<ToDoList>() {
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



    public class ToDoListViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgEdit;
        private TextView txtAmount, txtItem;
        public RelativeLayout viewForeground, viewBackground;
        public ToDoListViewHolder(@NonNull View itemView) {
            super(itemView);
            imgEdit = itemView.findViewById(R.id.img_edit_to_do_list_recycle_view);
            txtAmount = itemView.findViewById(R.id.txt_amount_to_do_list_recycle_view);
            txtItem = itemView.findViewById(R.id.txt_item_to_do_list_recycle_view);
            viewForeground = itemView.findViewById(R.id.layout_foreground_to_do_list_recycle_view);
            viewBackground = itemView.findViewById(R.id.layout_background_to_do_list_recycle_view);
            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onEditClick(getToDoList(position),position);
                    }
                }
            });

        }
    }


    @NonNull
    @Override
    public ToDoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_list_recycle_view,parent,false);
        return new ToDoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoListViewHolder holder, int position) {
        ToDoList toDoList = getToDoList(position);
        holder.txtItem.setText(toDoList.getItem());
        holder.txtAmount.setText(String.valueOf(toDoList.getAmount()));
    }


    public ToDoList getToDoList(int position){
        return getItem(position);
    }

    public interface onItemClickListener{
        void onEditClick(ToDoList toDoList, int position);
    }

    public void setOnItemClickListener(onItemClickListener listener){
        this.listener = listener;
    }
}
