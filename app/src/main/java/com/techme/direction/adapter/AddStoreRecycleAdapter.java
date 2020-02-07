package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.techme.direction.helper.ConvertImage;
import com.techme.direction.R;
import com.techme.direction.Store;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class AddStoreRecycleAdapter extends ListAdapter<Store, AddStoreRecycleAdapter.AddStoreViewHolder> {
    //private List<Store> unselectedList = new ArrayList<>();
    private onItemClickListener listener;

    public AddStoreRecycleAdapter() {
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

    class AddStoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView txtName;

        // display each item in the recycle view
        public AddStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_add_logo_recycle_view);
            txtName = itemView.findViewById(R.id.txt_add_name_recycle_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(getStore(position), position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AddStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_store_recycle_view, parent, false);
        return new AddStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddStoreViewHolder holder, int position) {
        Store store = getItem(position);
        holder.imgLogo.setImageBitmap(ConvertImage.convertByteToImage(store.getLogo()));
        holder.txtName.setText(store.getName());
    }


    // to get the position
    public Store getStore(int position) {
        return getItem(position);
    }

    //create an interface to override the onclick listener
    public interface onItemClickListener {
        void onClick(Store store, int position);
    }

    // method be called in other classes to get on click events
    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

}
