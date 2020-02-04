package com.techme.direction;

import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyStoreRecycleAdapter extends RecyclerView.Adapter<MyStoreRecycleAdapter.MyStoreViewHolder> {
    private List<Store> allStore;
    class MyStoreViewHolder extends RecyclerView.ViewHolder{

        public MyStoreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public MyStoreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyStoreViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
