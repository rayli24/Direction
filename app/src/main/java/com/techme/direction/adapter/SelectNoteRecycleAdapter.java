package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.techme.direction.R;
import com.techme.direction.Store;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class SelectNoteRecycleAdapter extends ListAdapter<Store, SelectNoteRecycleAdapter.SelectNoteViewHolder> {
    private onClickItemListener listener;

    public SelectNoteRecycleAdapter() {
        super(Diff_Callback);
    }

    private static final DiffUtil.ItemCallback<Store> Diff_Callback = new DiffUtil.ItemCallback<Store>() {
        @Override
        public boolean areItemsTheSame(@NonNull Store oldItem, @NonNull Store newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Store oldItem, @NonNull Store newItem) {
            return oldItem.getType().equals(newItem.getType()) && oldItem.getSelected() == newItem.getSelected()
                    && oldItem.getName().equals(newItem.getName()) && oldItem.getCountryName().equals(newItem.getCountryName())
                    && oldItem.getLogo() == newItem.getLogo() && oldItem.getTime() == newItem.getTime();
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
                    if(listener != null && position != RecyclerView.NO_POSITION){
                        listener.onClick(getStore(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public SelectNoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_note_recycle_view,parent,false);
        return new SelectNoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectNoteViewHolder holder, int position) {
        Store store = getStore(position);
        holder.txtName.setText(store.getName());
    }


    public Store getStore(int position)
    {
        return getItem(position);
    }

    public interface onClickItemListener{
        void onClick(Store store);
    }

    public void setOnclickItemListener(onClickItemListener listener){
        this.listener = listener;
    }

}
