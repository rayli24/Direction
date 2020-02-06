package com.techme.direction.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.techme.direction.ContextHelper;
import com.techme.direction.ConvertImage;
import com.techme.direction.R;
import com.techme.direction.Store;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyStoreRecycleAdapter extends RecyclerView.Adapter<MyStoreRecycleAdapter.MyStoreViewHolder> {
    private onItemClickListener listener;
    private List<Store> selectedList = new ArrayList<>();

    class MyStoreViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgLogo;
        private TextView name;
        private TextView time;

        public MyStoreViewHolder(@NonNull View itemView) {
            super(itemView);
            imgLogo = itemView.findViewById(R.id.img_my_logo_recycle_view);
            name = itemView.findViewById(R.id.txt_my_name_recycle_view);
            time = itemView.findViewById(R.id.txt_my_time_recycle_view);
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
        holder.time.setText(String.valueOf(store.getTime())+ " min");
    }

    @Override
    public int getItemCount() {
        return selectedList.size();
    }

    public Store getStore(int position) {
        return selectedList.get(position);
    }

    public void setList(List<Store> store) {
        selectedList = store;
        notifyDataSetChanged();
    }

    public interface onItemClickListener {
        void onclick(Store store);
    }

    public void onSetItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }
}
