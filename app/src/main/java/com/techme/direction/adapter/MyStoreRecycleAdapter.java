package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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

public class MyStoreRecycleAdapter extends ListAdapter<Store, MyStoreRecycleAdapter.MyStoreViewHolder> {
    private onItemClickListener listener;

    public MyStoreRecycleAdapter() {
        super(Diff_Callback);
    }

    // to handle the proper animation in the recycle view
    private static final DiffUtil.ItemCallback<Store> Diff_Callback = new DiffUtil.ItemCallback<Store>() {
        // check if the items are the same by checking their id
        @Override
        public boolean areItemsTheSame(@NonNull Store oldItem, @NonNull Store newItem) {
            return oldItem.getId() == newItem.getId();
        }

        // to make sure if the contests are the same
        @Override
        public boolean areContentsTheSame(@NonNull Store oldItem, @NonNull Store newItem) {
            return oldItem.getType().equals(newItem.getType()) && oldItem.getSelected() == newItem.getSelected()
                    && oldItem.getName().equals(newItem.getName()) && oldItem.getCountryName().equals(newItem.getCountryName())
                    && oldItem.getLogo() == newItem.getLogo() && oldItem.getTime() == newItem.getTime();
        }
    };

    public class MyStoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView name, time;
        public RelativeLayout viewForeground, viewBackground;


        public MyStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_my_logo_recycle_view);
            name = itemView.findViewById(R.id.txt_my_name_recycle_view);
//            time = itemView.findViewById(R.id.txt_my_time_recycle_view);
            viewForeground = itemView.findViewById(R.id.layout_foreground_my_store_recycle_view);
            viewBackground = itemView.findViewById(R.id.layout_background_my_store_recycle_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(getStore(position));
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public MyStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_store_recycle_view, parent, false);
        return new MyStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyStoreViewHolder holder, int position) {
        Store store = getStore(position);
        holder.imgLogo.setImageBitmap(ConvertImage.convertByteToImage(store.getLogo()));
        holder.name.setText(store.getName());
        holder.name.setSelected(true);
        holder.name.setFocusableInTouchMode(true);
//        holder.time.setText(store.getTime() + " min");
    }


    public Store getStore(int position) {
        return getItem(position);
    }


    public interface onItemClickListener {
        void onclick(Store store);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
